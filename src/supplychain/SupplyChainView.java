package supplychain;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.*;

class SupplyChainView {
    private final SupplyChainModel model;

    private LogInView logInView;
    private MainForm mainForm;
    private RegisterView registerView;
    private Stage stage;

    private int curView;

    public SupplyChainView(SupplyChainModel model, Stage stage) {
        this.model = model;
        this.stage = stage;
        logInView = new LogInView();
        mainForm = new MainForm();
        registerView = new RegisterView();
        curView = 1;
    }

    public LogInView getLogInView() {
        return logInView;
    }

    public MainForm getMainForm() {
        return mainForm;
    }

    public RegisterView getRegisterView() {
        return registerView;
    }

    public void setCurView(int curView) {
        this.curView = curView;
    }

    public Node getCurView() {
        if (curView == 1)
            return logInView;
        else if (curView == 2)
            return mainForm;
        else if (curView == 3)
            return registerView;
        return null;
    }

    public Stage getStage() {
        return stage;
    }

    public void showLogInView() {
        curView=1;
        logInView.clear();
        if (logInView.getScene() != null)
            stage.setScene(logInView.getScene());
        else {
            Scene scene = new Scene(logInView, 800, 600);
            stage.setScene(scene);
        }
    }

    public void showMainForm() {
        curView=2;
        if (mainForm.getScene() != null)
            stage.setScene(mainForm.getScene());
        else {
            Scene scene = new Scene(mainForm, 800, 600);
            stage.setScene(scene);
        }
        mainForm.setActions(model.getUserStatus());
    }

    public void showRegisterView() {
        curView=3;
        registerView.clear();
        if (registerView.getScene() != null)
            stage.setScene(registerView.getScene());
        else {
            Scene scene = new Scene(registerView, 800, 600);
            stage.setScene(scene);
        }
    }

    public void addLog(String log) {
        mainForm.addLog(log);
    }
}