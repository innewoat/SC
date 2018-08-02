package supplychain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.LinkedList;

public class TransactionBlock extends Block {
    ObservableList<TransactionInfo> transactionInfos;

    public TransactionBlock(String user) {
        this.userName = user;
        transactionInfos = FXCollections.observableArrayList();
        //transactionInfos.add(new TransactionInfo("upstream", "root", "1t材料", "100元"));
        init();
    }

    public void addOrder(TransactionInfo n) {
        transactionInfos.add(n);
    }

    public void init() {
        try {
            curhash = -1;
            timestamp = null;
            content = "";
            FileReader fr = new FileReader(userName + File.separator + "transaction" + File.separator + "info.txt");
            BufferedReader br = new BufferedReader(fr);
            String s = br.readLine();
            br.close();
            fr.close();
            String[] t = s.split(" ");
            index = Integer.valueOf(t[0]);
            prehash = Integer.valueOf(t[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buildContent() {
        content = new String();
        if (!transactionInfos.isEmpty()) {
            for (TransactionInfo i : transactionInfos)
                content += i.toString() + "\n";
            timestamp = transactionInfos.get(transactionInfos.size() - 1).time;
            curhash = (String.valueOf(index) + String.valueOf(prehash) + SupplyChainModel.sdf.format(timestamp) + content).hashCode();
        }
        System.out.println("build transaction Content");
    }

    public void save() {
        try {
            if (!content.isEmpty()) {
                File f = new File(userName + File.separator + "transaction" + File.separator + String.valueOf(index) + ".txt");
                FileOutputStream fs = new FileOutputStream(f);
                fs.write((String.valueOf(index) +" "+String.valueOf(prehash)+" "+String.valueOf(curhash)+" "+SupplyChainModel.sdf.format(timestamp)+"\n").getBytes("UTF-8"));
                fs.write(content.getBytes("UTF-8"));
                fs.close();
                renew();
                File indexf=new File(userName+File.separator+"transaction"+File.separator+"info.txt");
                fs=new FileOutputStream(indexf);
                fs.write((String.valueOf(index)+" "+String.valueOf(prehash)).getBytes("UTF-8"));
                fs.close();
            }
            System.out.println("save transaction");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void renew() {
        index++;
        prehash=curhash;
        transactionInfos.clear();
    }
}