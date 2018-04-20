package view;

import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class RegisterView extends GridPane{
    public Label userNameLabel;
    public Label userPasswdLabel;
    public Label userStatusLabel;
    public Label userAddrLabel;
    public Label userPortLabel;

    public TextField userNameTextField;
    public PasswordField userPasswdTextField;
    public ChoiceBox<String> userStatusChoiceBox;
    public TextField userAddrTextField;
    public TextField userPortTextField;

    public Button sendInfoButton;
    public Button backToLogin;

    public RegisterView(){
        userNameLabel=new Label("用户名：");
        userPasswdLabel=new Label("密码：");
        userStatusLabel=new Label("身份：");
        userAddrLabel=new Label("地址：");
        userPortLabel=new Label("端口：");

        userNameTextField=new TextField();
        userPasswdTextField=new PasswordField();
        userStatusChoiceBox=new ChoiceBox(FXCollections.observableArrayList("中小企业","银行","物流"));
        userAddrTextField=new TextField();
        userPortTextField=new TextField();

        sendInfoButton=new Button("注册");
        backToLogin=new Button("取消");

        this.setHgap(10);
        this.setVgap(10);

        this.add(userNameLabel,1,1);
        this.add(userNameTextField,2,1);
        this.add(userPasswdLabel,1,2);
        this.add(userPasswdTextField,2,2);
        this.add(userStatusLabel,1,3);
        this.add(userStatusChoiceBox,2,3);
        this.add(userAddrLabel,1,4);
        this.add(userAddrTextField,2,4);
        this.add(userPortLabel,1,5);
        this.add(userPortTextField,2,5);
        this.add(sendInfoButton,1,6);
        this.add(backToLogin,2,6);
    }

    public void clear(){
        userNameTextField.clear();
        userPasswdTextField.clear();
        userStatusChoiceBox.setValue(null);
        userAddrTextField.clear();
        userPortTextField.clear();
    }

}