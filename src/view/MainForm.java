package view;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import supplychain.ChainNodeInfo;
import supplychain.LoanModeAInfo;
import supplychain.LoanModeBInfo;
import supplychain.TransactionInfo;

public class MainForm extends GridPane {

    ToolBar actions;
    TableView<ChainNodeInfo> userInfoList;
    TableView<TransactionInfo> transactionInfoList;
    TableView<LoanModeAInfo> loanModeAInfoList;
    TableView<LoanModeBInfo> loanModeBInfoList;

    public Button addTransaction;
    public Button addLoanModeA;
    public Button addLoanModeB;
    public Button finishLoanModeA;
    public Button transmitLoanModeB;
    public Button finishLoanModeB;
    public Button test;

    TextArea logText;

    public MainForm() {
        actions = new ToolBar();
        addTransaction = new Button("添加交易");
        addLoanModeA = new Button("添加A型贷款");
        addLoanModeB = new Button("添加B型贷款");
        finishLoanModeA = new Button("确认A型还款");
        transmitLoanModeB = new Button("转发B型还款");
        finishLoanModeB = new Button("确认B型还款");
        test = new Button("test");

        userInfoList = new TableView<ChainNodeInfo>();
        transactionInfoList = new TableView<TransactionInfo>();
        loanModeAInfoList = new TableView<LoanModeAInfo>();
        loanModeBInfoList = new TableView<LoanModeBInfo>();

        TableColumn<ChainNodeInfo, String> userNameCol = new TableColumn<ChainNodeInfo, String>("用户名");
        TableColumn<ChainNodeInfo, String> userStatusCol = new TableColumn<ChainNodeInfo, String>("身份");

        TableColumn<TransactionInfo, String> tranU1Col = new TableColumn<TransactionInfo, String>("用户1");
        TableColumn<TransactionInfo, String> tranT1Col = new TableColumn<TransactionInfo, String>("商品1");
        TableColumn<TransactionInfo, String> tranU2Col = new TableColumn<TransactionInfo, String>("用户2");
        TableColumn<TransactionInfo, String> tranT2Col = new TableColumn<TransactionInfo, String>("商品2");

        TableColumn<LoanModeAInfo, String> loanAU1Col = new TableColumn<LoanModeAInfo, String>("用户1");
        TableColumn<LoanModeAInfo, String> loanAT1Col = new TableColumn<LoanModeAInfo, String>("商品1");
        TableColumn<LoanModeAInfo, String> loanAU2Col = new TableColumn<LoanModeAInfo, String>("用户2");
        TableColumn<LoanModeAInfo, String> loanAT2Col = new TableColumn<LoanModeAInfo, String>("商品2");
        TableColumn<LoanModeAInfo, String> loanAStatusCol = new TableColumn<LoanModeAInfo, String>("状态");

        TableColumn<LoanModeBInfo, String> loanBU1Col = new TableColumn<LoanModeBInfo, String>("用户1");
        TableColumn<LoanModeBInfo, String> loanBT1Col = new TableColumn<LoanModeBInfo, String>("商品1");
        TableColumn<LoanModeBInfo, String> loanBU2Col = new TableColumn<LoanModeBInfo, String>("用户2");
        TableColumn<LoanModeBInfo, String> loanBT2Col = new TableColumn<LoanModeBInfo, String>("商品2");
        TableColumn<LoanModeBInfo, String> loanBStatusCol = new TableColumn<LoanModeBInfo, String>("状态");

        userInfoList.getColumns().addAll(userNameCol, userStatusCol);

        transactionInfoList.getColumns().addAll(tranU1Col, tranT1Col, tranU2Col, tranT2Col);

        loanModeAInfoList.getColumns().addAll(loanAU1Col, loanAT1Col, loanAU2Col, loanAT2Col, loanAStatusCol);

        loanModeBInfoList.getColumns().addAll(loanBU1Col, loanBT1Col, loanBU2Col, loanBT2Col, loanBStatusCol);

        userNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        userStatusCol.setCellValueFactory(new PropertyValueFactory<>("userStatus"));

        tranU1Col.setCellValueFactory(new PropertyValueFactory<>("u1"));
        tranT1Col.setCellValueFactory(new PropertyValueFactory<>("t1"));
        tranU2Col.setCellValueFactory(new PropertyValueFactory<>("u2"));
        tranT2Col.setCellValueFactory(new PropertyValueFactory<>("t2"));

        loanAU1Col.setCellValueFactory(new PropertyValueFactory<>("u1"));
        loanAT1Col.setCellValueFactory(new PropertyValueFactory<>("t1"));
        loanAU2Col.setCellValueFactory(new PropertyValueFactory<>("u2"));
        loanAT2Col.setCellValueFactory(new PropertyValueFactory<>("t2"));
        loanAStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        loanBU1Col.setCellValueFactory(new PropertyValueFactory<>("u1"));
        loanBT1Col.setCellValueFactory(new PropertyValueFactory<>("t1"));
        loanBU2Col.setCellValueFactory(new PropertyValueFactory<>("u2"));
        loanBT2Col.setCellValueFactory(new PropertyValueFactory<>("t2"));
        loanBStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));


        logText = new TextArea();
        logText.setPrefHeight(100);

        this.setPadding(new Insets(10));
        this.setVgap(10);
        this.setHgap(10);
        this.getColumnConstraints().add(new ColumnConstraints(350));
        this.getColumnConstraints().add(new ColumnConstraints(350));

        this.add(actions, 0, 0, 2, 1);
        this.add(userInfoList, 0, 1);
        this.add(transactionInfoList, 1, 1);
        this.add(loanModeAInfoList, 0, 2);
        this.add(loanModeBInfoList, 1, 2);
        this.add(logText, 0, 3, 2, 1);

    }

    public void setUserList(ObservableList<ChainNodeInfo> list) {
        userInfoList.setItems(list);
    }

    public void setTransactionInfoList(ObservableList<TransactionInfo> list) {
        transactionInfoList.setItems(list);
    }

    public void setLoanModeAInfoList(ObservableList<LoanModeAInfo> list) {
        loanModeAInfoList.setItems(list);
    }

    public void setLoanModeBInfoList(ObservableList<LoanModeBInfo> list) {
        loanModeBInfoList.setItems(list);
    }

    public void setActions(String userStatus) {
        try {
            switch (userStatus) {
                case "kernel":
                    //actions.getItems().add(test);
                    break;
                case "enterprise":
                    //actions.getItems().add(test);
                    actions.getItems().add(addTransaction);
                    actions.getItems().add(addLoanModeA);
                    actions.getItems().add(addLoanModeB);
                    break;
                case "bank":
                    //actions.getItems().add(test);
                    actions.getItems().add(finishLoanModeA);
                    actions.getItems().add(transmitLoanModeB);
                    break;
                case "logistics":
                    //actions.getItems().add(test);
                    actions.getItems().add(finishLoanModeB);
                    break;
                default:
                    System.out.println(userStatus);
                    actions.getItems().add(test);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addLog(String log) {
        logText.appendText(log);
    }
}