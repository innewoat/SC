package supplychain;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import supplychain.SupplyChainModel;
import supplychain.SupplyChainView;
import view.ErrorAlert;

class SupplyChainPresenter {
    private SupplyChainModel model;
    private SupplyChainView view;

    private CommuFunction commuFunction;

    private int quitFlag = 0;

    public SupplyChainModel getModel() {
        return model;
    }

    public SupplyChainView getView() {
        return view;
    }

    public SupplyChainPresenter(SupplyChainModel model, SupplyChainView view) {
        this.model = model;
        this.view = view;
        attachLogInViewEvents();
        this.view.getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                quitFlag = 1;
                System.out.println("检测到关闭");
                try {
                    ChainNodeInfo curUser = model.getCurUserInfo();
                    if (curUser != null) {
                        Socket socket = new Socket(curUser.getUserAddress(),
                                curUser.getUserPort());
                        socket.close();
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
        this.commuFunction = new CommuFunction(this);
    }

    private void attachLogInViewEvents() {
        view.getLogInView().loginButton.setOnAction(e -> logIn());
        view.getLogInView().registerButton.setOnAction(e->showRegister());
        // TODO: 注册
        view.getRegisterView().backToLogin.setOnAction(e->backLogIn());
    }

    private void logIn() {
        try {
            String userName = view.getLogInView().userNameTextField.getText();
            String userPasswd = view.getLogInView().userPasswdTextField.getText();

            if (userName.isEmpty() || userPasswd.isEmpty()) {
                System.out.println("用户名，密码不能为空");
                showError("用户名，密码不能为空");
                return;
            }
            File userDir = new File(userName);
            if (!userDir.exists()) {
                userDir.mkdir();
                commuFunction.pullUserInfo(userName);
            }
            model.initializeUser(userName);
            if (!model.checkIdentidy(userName, userPasswd)) {
                showError("用户名或密码错误");
                return;
            } else {
                model.setUserName(userName);
                model.setUserPasswd(userPasswd);
                view.showMainForm();
                view.getMainForm().setUserList(model.getUserInfoList());
                addLog("用户登录");
                ChainNodeInfo user = model.getCurUserInfo();
                NodeSocket nodeSocket = new NodeSocket(user.getUserAddress(), user.getUserPort());
                Thread CommuThread = new Thread(nodeSocket);
                CommuThread.start();
                System.out.println("启动socket完成");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void showRegister(){
        try{
            view.showRegisterView();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    private void sendRegisterInfo(){
        String userName=view.getRegisterView().userNameTextField.getText();
        String userPasswd=view.getRegisterView().userPasswdTextField.getText();
        String userStatus=view.getRegisterView().userStatusChoiceBox.getValue();
        String userAddr=view.getRegisterView().userAddrTextField.getText();
        int userPort=Integer.valueOf(view.getRegisterView().userPortTextField.getText());

        ChainNodeInfo n=new ChainNodeInfo(userName, userPasswd, userStatus, userAddr, userPort);
        commuFunction.sendRegisterInfo(n);
        System.out.println("发送成功，返回登录");
        backLogIn();
    }

    private void backLogIn(){
        try{
            view.showLogInView();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    private void showError(String errorMessage) {
        ErrorAlert errorAlert = new ErrorAlert(errorMessage);

        Scene scene = new Scene(errorAlert);
        Stage stage = new Stage(StageStyle.UTILITY);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(scene);
        stage.initOwner(view.getCurView().getScene().getWindow());

        errorAlert.okBtn.setOnAction(e -> stage.close());
        stage.sizeToScene();
        stage.showAndWait();
    }

    public void addLog(String raw) {
        view.addLog(model.buildLog(raw));
    }

    private class NodeSocket implements Runnable {
        String addr;
        int port;

        NodeSocket(String addr, int port) {
            this.addr = addr;
            this.port = port;
        }

        @Override
        public void run() {
            try {
                ServerSocket server = new ServerSocket(port);
                addLog("通信启动");
                while (quitFlag == 0) {
                    Socket socket = server.accept();
                    Thread t = new Thread(new CommuHandle(socket));
                    t.start();
                }
                server.close();
                System.out.println("成功关闭serversocket");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    private class CommuHandle implements Runnable {
        Socket socket;

        public CommuHandle(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            commuFunction.serverHandle(socket);
        }
    }
}