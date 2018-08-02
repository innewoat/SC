package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class InputTransactionInfo extends GridPane{
    Label t1Lbl;
    public TextField t1Tf;
    Label t2Lbl;
    public TextField t2Tf;

    public Button okBtn;
    public Button cancelBtn;

    public InputTransactionInfo(){
        t1Lbl=new Label("商品1：");
        t2Lbl=new Label("商品2：");
        t1Tf=new TextField();
        t2Tf=new TextField();
        
        okBtn=new Button("确认");
        cancelBtn=new Button("取消");

        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setVgap(10);

        this.add(t1Lbl,0,0);
        this.add(t1Tf,1,0);
        this.add(t2Lbl,0,1);
        this.add(t2Tf,1,1);
        this.add(okBtn,0,2);
        this.add(cancelBtn,1,2);
    }
}