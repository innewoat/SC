package supplychain;

import java.util.Date;

public class LoanModeBInfo {
    public String u1;
    public String u2;
    public String t1;
    public String t2;
    public Date time;
    public String status;

    public LoanModeBInfo(TransactionInfo transactionInfo, String status){
        this.u1=transactionInfo.u1;
        this.u2=transactionInfo.u2;
        this.t1=transactionInfo.t1;
        this.t2=transactionInfo.t2;
        this.time=transactionInfo.time;
        this.status=status;
    }

    public String getT1() {
        return t1;
    }

    public String getT2() {
        return t2;
    }

    public String getU1() {
        return u1;
    }

    public String getU2() {
        return u2;
    }

    public Date getTime() {
        return time;
    }

    public void setT1(String t1) {
        this.t1 = t1;
    }

    public void setT2(String t2) {
        this.t2 = t2;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setU1(String u1) {
        this.u1 = u1;
    }

    public void setU2(String u2) {
        this.u2 = u2;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return u1+" "+u2+" "+t1+" "+t2+" "+SupplyChainModel.sdf.format(time)+" "+status;
    }
}
