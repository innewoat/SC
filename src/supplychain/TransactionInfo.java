package supplychain;

import java.util.Date;

public class TransactionInfo {
    public String u1;
    public String u2;
    public String t1;
    public String t2;
    public Date time;

    public TransactionInfo(String u1,String u2,String t1,String t2){
        this.u1=u1;
        this.u2=u2;
        this.t1=t1;
        this.t2=t2;
        this.time=new Date();
    }

    public TransactionInfo(String u1,String u2,String t1,String t2,Date time){
        this.u1=u1;
        this.u2=u2;
        this.t1=t1;
        this.t2=t2;
        this.time=time;
    }

    public String getU1(){
        return u1;
    }

    public void setU1(String u1) {
        this.u1 = u1;
    }

    public String getU2(){
        return u2;
    }

    public void setU2(String u2){
        this.u2=u2;
    }

    public String getT1(){
        return t1;
    }

    public void setT1(String t1){
        this.t1=t1;
    }

    public String getT2(){
        return t2;
    }

    public void setT2(String t2){
        this.t2=t2;
    }

    public Date getTime(){
        return time;
    }

    public void setTime(Date time){
        this.time=time;
    }

    @Override
    public String toString(){
        return u1+" "+u2+" "+t1+" "+t2+" "+SupplyChainModel.sdf.format(time);
    }
}
