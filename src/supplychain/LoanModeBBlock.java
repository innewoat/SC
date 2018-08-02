package supplychain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

public class LoanModeBBlock extends Block{
    ObservableList<LoanModeBInfo> loanModeBInfos;

    public LoanModeBBlock(String user){
        this.userName=user;
        loanModeBInfos =FXCollections.observableArrayList();
        //loanModeBInfos.add(new LoanModeBInfo(new TransactionInfo("u1","u2","t1","t2"),"create"));
        init();
    }

    public void addOrder(LoanModeBInfo n){
        loanModeBInfos.add(n);
    }

    public void init(){
        try {
            curhash = -1;
            timestamp = null;
            content = "";
            FileReader fr = new FileReader(userName + File.separator + "loanModeB" + File.separator + "info.txt");
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
        if (!loanModeBInfos.isEmpty()) {
            for (LoanModeBInfo i : loanModeBInfos)
                content += i.toString() + "\n";
            timestamp = loanModeBInfos.get(loanModeBInfos.size() - 1).time;
            curhash = (String.valueOf(index) + String.valueOf(prehash) + SupplyChainModel.sdf.format(timestamp) + content).hashCode();
        }
        System.out.println("build loan B Content");
    }

    public void save(){
        try {
            if (!content.isEmpty()) {
                File f = new File(userName + File.separator + "loanModeB" + File.separator + String.valueOf(index) + ".txt");
                FileOutputStream fs = new FileOutputStream(f);
                fs.write((String.valueOf(index) +" "+String.valueOf(prehash)+" "+String.valueOf(curhash)+" "+SupplyChainModel.sdf.format(timestamp)+"\n").getBytes("UTF-8"));
                fs.write(content.getBytes("UTF-8"));
                fs.close();
                renew();
                File indexf=new File(userName+File.separator+"loanModeB"+File.separator+"info.txt");
                fs=new FileOutputStream(indexf);
                fs.write((String.valueOf(index)+" "+String.valueOf(prehash)).getBytes("UTF-8"));
                fs.close();
            }
            System.out.println("save mode B");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void renew(){
        index++;
        prehash=curhash;
        loanModeBInfos.clear();
    }
}
