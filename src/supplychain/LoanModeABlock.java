package supplychain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

public class LoanModeABlock extends Block {
    ObservableList<LoanModeAInfo> loanModeAInfos;

    public LoanModeABlock(String user){
        this.userName=user;
        loanModeAInfos=FXCollections.observableArrayList();
        //loanModeAInfos.add(new LoanModeAInfo(new TransactionInfo("u1","u2","t1","t2"),"create"));
        init();
    }

    public void addOrder(LoanModeAInfo n){
        loanModeAInfos.add(n);
    }

    public void init(){
        try {
            curhash = -1;
            timestamp = null;
            content = "";
            FileReader fr = new FileReader(userName + File.separator + "loanModeA" + File.separator + "info.txt");
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

    public void buildContent(){
        content = new String();
        if (!loanModeAInfos.isEmpty()) {
            for (LoanModeAInfo i : loanModeAInfos)
                content += i.toString() + "\n";
            timestamp = loanModeAInfos.get(loanModeAInfos.size() - 1).time;
            curhash = (String.valueOf(index) + String.valueOf(prehash) + SupplyChainModel.sdf.format(timestamp) + content).hashCode();
        }
        System.out.println("build loan A Content");
    }

    public void save(){
        try {
            if (!content.isEmpty()) {
                File f = new File(userName + File.separator + "loanModeA" + File.separator + String.valueOf(index) + ".txt");
                FileOutputStream fs = new FileOutputStream(f);
                fs.write((String.valueOf(index) +" "+String.valueOf(prehash)+" "+String.valueOf(curhash)+" "+SupplyChainModel.sdf.format(timestamp)+"\n").getBytes("UTF-8"));
                fs.write(content.getBytes("UTF-8"));
                fs.close();
                renew();
                File indexf=new File(userName+File.separator+"loanModeA"+File.separator+"info.txt");
                fs=new FileOutputStream(indexf);
                fs.write((String.valueOf(index)+" "+String.valueOf(prehash)).getBytes("UTF-8"));
                fs.close();
            }
            System.out.println("save mode A");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void renew(){
        index++;
        prehash=curhash;
        loanModeAInfos.clear();
    }
}
