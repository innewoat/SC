package view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class LogInView extends GridPane {

    public Label userNameLabel;
    public Label userPasswdLabel;

    public TextField userNameTextField;
    public PasswordField userPasswdTextField;

    public Button loginButton;
    public Button registerButton;

    public LogInView(){
        userNameLabel=new Label("用户名：");
        userPasswdLabel=new Label("密码：");
        userNameTextField=new TextField();
        userPasswdTextField=new PasswordField();
        loginButton=new Button("登陆");
        registerButton=new Button("注册");


        this.setHgap(10);
        this.setVgap(10);

        this.add(userNameLabel,1,1);
        this.add(userPasswdLabel,1,2);
        this.add(userNameTextField,2,1);
        this.add(userPasswdTextField,2,2);
        this.add(loginButton,1,3);
        this.add(registerButton,2,3);
    }

    public void clear(){
        userNameTextField.clear();
        userPasswdTextField.clear();
    }
}