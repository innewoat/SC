package view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ErrorAlert extends VBox {
    String msg = "";
    Label msgLbl;
    public Button okBtn;

    public ErrorAlert(String errorMessage) {
        msg = errorMessage;
        msgLbl = new Label(msg);
        okBtn = new Button("确认");

        this.getChildren().addAll(new StackPane(msgLbl), new StackPane(okBtn));
        this.setSpacing(10);
    }
}