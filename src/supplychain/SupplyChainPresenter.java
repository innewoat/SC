package supplychain;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import view.MessageAlert;
import view.InputTransactionInfo;

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
        attachEvents();
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

    private void attachEvents() {
        view.getLogInView().loginButton.setOnAction(e -> logIn());
        view.getLogInView().registerButton.setOnAction(e -> showRegister());
        view.getRegisterView().backToLogin.setOnAction(e -> backLogIn());
        view.getRegisterView().sendInfoButton.setOnAction(e -> sendRegisterInfo());
        view.getMainForm().addTransaction.setOnAction(e->showInputTransaction());
        view.getMainForm().addLoanModeA.setOnAction(e->addLoanModeA());
        view.getMainForm().addLoanModeB.setOnAction(e->addLoanModeB());
        view.getMainForm().finishLoanModeA.setOnAction(e->finishLoanModeA());
        view.getMainForm().transmitLoanModeB.setOnAction(e->transmitLoanModeB());
        view.getMainForm().finishLoanModeB.setOnAction(e->finishLoanModeB());
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
            if (!userDir.exists())
                userDir.mkdir();
            File transactionDir = new File(userName+File.separator+"transaction");
            if(!transactionDir.exists())
                transactionDir.mkdirs();
            File loanModeADir = new File(userName+File.separator+"loanModeA");
            if(!loanModeADir.exists())
                loanModeADir.mkdirs();
            File loanModeBDir = new File(userName+File.separator+"loanModeB");
            if(!loanModeBDir.exists())
                loanModeBDir.mkdirs();

            if (!userName.equals("root")) {
                commuFunction.askFile(userName, model.getKernelAddress(), model.getKernelPort(), "UserInfo.txt");
                commuFunction.initializeTransactionBlock(userName);
                commuFunction.initializeLoanModeABlock(userName);
                commuFunction.initializeLoanModeBBlock(userName);
            }
            model.initializeUserIntoList(userName);
            if (!model.checkIdentidy(userName, userPasswd)) {
                showError("用户名或密码错误");
                return;
            } else {
                model.setUserName(userName);
                model.setUserPasswd(userPasswd);
                model.initializeUserStatus();
                model.initializeBlocks();
                view.showMainForm();
                view.getMainForm().setUserList(model.getUserInfoList());
                view.getMainForm().setTransactionInfoList(model.getTransactionInfoList());
                view.getMainForm().setLoanModeAInfoList(model.getLoanModeAInfoList());
                view.getMainForm().setLoanModeBInfoList(model.getLoanModeBInfoList());
                addLog("用户登录");
                view.getStage().setTitle("供应链-"+userName);
                ChainNodeInfo user = model.getCurUserInfo();
                NodeSocket nodeSocket = new NodeSocket(user.getUserAddress(), user.getUserPort());
                Thread commuThread = new Thread(nodeSocket);
                commuThread.start();
                System.out.println("启动socket完成");
                Thread controllerThread=new Thread(new Controller());
                controllerThread.start();
                System.out.println("启动控制线程完成");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void showRegister() {
        try {
            view.showRegisterView();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void sendRegisterInfo() {
        String userName = view.getRegisterView().userNameTextField.getText();
        String userPasswd = view.getRegisterView().userPasswdTextField.getText();
        String userStatus = "Unknown";
        switch (view.getRegisterView().userStatusChoiceBox.getValue()) {
            case "中小企业":
                userStatus = "enterprise";
                break;
            case "银行":
                userStatus = "bank";
                break;
            case "物流":
                userStatus = "logistics";
                break;
            default:
                break;
        }
        String userAddr = view.getRegisterView().userAddrTextField.getText();
        int userPort = Integer.valueOf(view.getRegisterView().userPortTextField.getText());

        ChainNodeInfo n = new ChainNodeInfo(userName, userPasswd, userStatus, userAddr, userPort);
        commuFunction.sendRegisterInfo(n);
        System.out.println("发送成功，返回登录");
        backLogIn();
    }

    private void backLogIn() {
        try {
            view.showLogInView();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void showError(String errorMessage) {
        MessageAlert messageAlert = new MessageAlert(errorMessage);

        Scene scene = new Scene(messageAlert);
        Stage stage = new Stage(StageStyle.UTILITY);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(scene);
        stage.initOwner(view.getCurView().getScene().getWindow());

        messageAlert.okBtn.setOnAction(e -> stage.close());
        stage.sizeToScene();
        stage.showAndWait();
    }

    private void showInputTransaction(){
        InputTransactionInfo inputTransactionInfo=new InputTransactionInfo();

        Scene scene=new Scene(inputTransactionInfo);
        Stage stage=new Stage(StageStyle.UTILITY);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(scene);
        stage.initOwner(view.getCurView().getScene().getWindow());

        inputTransactionInfo.okBtn.setOnAction((ActionEvent e)->{
            try{
                String u1=model.getCurUserInfo().userName;
                String t1=inputTransactionInfo.t1Tf.getText();
                String u2=model.getKernelInfo().userName;
                String t2=inputTransactionInfo.t2Tf.getText();
                if(!t1.isEmpty()&&!t2.isEmpty()){
                    TransactionInfo nTrans=new TransactionInfo(u1,u2,t1,t2);
                    model.addTransactionInfo(nTrans);
                    commuFunction.sendToAll("ADDTRANSACTION "+nTrans.toString());
                }
                stage.close();
            }catch (Exception ne){
                ne.printStackTrace();
            }
        });
        inputTransactionInfo.cancelBtn.setOnAction(e->stage.close());
        stage.sizeToScene();
        stage.showAndWait();
    }

    private void addLoanModeA(){
        try{
            LoanModeAInfo n=new LoanModeAInfo(new TransactionInfo("upstream","root","1t材料","100元"),"create");
            model.addLoanAModeInfo(n);
            commuFunction.sendToAll("ADDLOANMODEA "+n.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void addLoanModeB(){
        try{
            LoanModeBInfo n=new LoanModeBInfo(new TransactionInfo("upstream","root","200元","1t材料"),"create");
            model.addLoanBModeInfo(n);
            commuFunction.sendToAll("ADDLOANMODEB "+n.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void finishLoanModeA(){
        try{
            LoanModeAInfo n=new LoanModeAInfo(new TransactionInfo("upstream","root","1t材料","100元"),"finish");
            model.addLoanAModeInfo(n);
            commuFunction.sendToAll("ADDLOANMODEA "+n.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void transmitLoanModeB(){
        try{
            LoanModeBInfo n=new LoanModeBInfo(new TransactionInfo("upstream","root","200元","1t材料"),"transmit");
            model.addLoanBModeInfo(n);
            commuFunction.sendToAll("ADDLOANMODEB "+n.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void finishLoanModeB(){
        try{
            LoanModeBInfo n=new LoanModeBInfo(new TransactionInfo("upstream","root","200元","1t材料"),"finish");
            model.addLoanBModeInfo(n);
            commuFunction.sendToAll("ADDLOANMODEB "+n.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addLog(String raw) {
        view.addLog(model.buildLog(raw));
    }

    private class Controller implements Runnable{
        @Override
        public void run(){
            try {
                int blockBuildFlag=0;
                int blockSaveFlag=0;
                while (quitFlag == 0) {
                    Calendar calendar=Calendar.getInstance();
                    int t = calendar.get(Calendar.SECOND);
                    System.out.println(t+" s");
                    if(t>50&&t<=55){
                        if(blockBuildFlag==0){
                            //model.transactionBlock.buildContent();
                            //model.loanModeABlock.buildContent();
                            model.loanModeBBlock.buildContent();
                            blockBuildFlag=1;
                        }
                    }else if(t>55){
                        if(blockSaveFlag==0){
                            //model.transactionBlock.save();
                            //model.loanModeABlock.save();
                            model.loanModeBBlock.save();
                            blockSaveFlag=1;
                        }
                    }else{
                        blockBuildFlag=0;
                        blockSaveFlag=0;
                    }
                    Thread.sleep(2000);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
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