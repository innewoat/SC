package view;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import supplychain.ChainNodeInfo;

public class MainForm extends GridPane {
    TableView<ChainNodeInfo> userInfoList;

    TextArea logText;

    public MainForm() {
        userInfoList = new TableView<ChainNodeInfo>();

        TableColumn<ChainNodeInfo, String> userNameCol = new TableColumn<ChainNodeInfo, String>("用户名");
        TableColumn<ChainNodeInfo, String> userStatusCol = new TableColumn<ChainNodeInfo, String>("身份");

        userInfoList.getColumns().addAll(userNameCol, userStatusCol);
        userInfoList.setPrefSize(300, 200);

        userNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        userStatusCol.setCellValueFactory(new PropertyValueFactory<>("userStatus"));

        logText=new TextArea();
        logText.setPrefHeight(100);
        //logText.setPrefSize(700, 100);

        this.setPadding(new Insets(30));
        this.setVgap(10);
        this.setHgap(10);

        this.add(userInfoList, 0, 0);
        this.add(logText,0,1);
    }

    public void setUserList(ObservableList<ChainNodeInfo> list){
        userInfoList.setItems(list);
    }

    public void addLog(String log){
        logText.appendText(log);
    }
}