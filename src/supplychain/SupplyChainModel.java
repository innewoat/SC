package supplychain;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

class SupplyChainModel {
    private String userName = "Not_Log_In";
    private String userPasswd = "Not_Log_In";
    private String userStatus = "Not_Log_In";

    ObservableList<ChainNodeInfo> userList;

    private String kernelAddress = "127.0.0.1";
    private int kernelPort = 3000;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPasswd() {
        return userPasswd;
    }

    public void setUserPasswd(String userPasswd) {
        this.userPasswd = userPasswd;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getKernelAddress() {
        return kernelAddress;
    }

    public int getKernelPort() {
        return kernelPort;
    }

    public ObservableList<ChainNodeInfo> getUserInfoList() {
        return userList;
    }

    public ChainNodeInfo getCurUserInfo() {
        for (ChainNodeInfo c : userList) {
            if (c.getUserName().equals(this.userName))
                return c;
        }
        return null;
    }

    public SupplyChainModel() {
        System.out.println("model constructor");
        userList = FXCollections.observableArrayList();
    }

    public String buildLog(String raw) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(new Date());
        String n = date + " " + raw + "\n";
        try {
            if (!userName.equals("Not_Log_In")) {
                File f = new File(userName + File.separator + "Log.txt");
                FileWriter fw = new FileWriter(f, true);
                PrintWriter pw = new PrintWriter(fw);
                pw.print(new String(n.getBytes("UTF-8")));
                pw.close();
                fw.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return n;
    }

    public int initializeUser(String name) {
        try {
            FileReader fr = new FileReader(name + File.separator + "UserInfo.txt");

            //userList = FXCollections.observableArrayList();
            String uname;
            String upasswd;
            String ustatus;
            String uaddr;
            int uport;

            BufferedReader br = new BufferedReader(fr);
            String s = null;
            String[] sl;
            while ((s = br.readLine()) != null) {
                sl = s.split(" ");
                uname = sl[0];
                upasswd = sl[1];
                ustatus = sl[2];
                uaddr = sl[3];
                uport = Integer.valueOf(sl[4]);
                userList.add(new ChainNodeInfo(uname, upasswd, ustatus, uaddr, uport));
            }

            br.close();
            fr.close();

            return 0;
        } catch (Exception e) {
            System.out.println(e);
            return -1;
        }
    }

    public boolean checkIdentidy(String userName, String userPasswd) {
        for (ChainNodeInfo i : userList) {
            if (i.userName.equals(userName)) {
                if (i.userPasswd.equals(userPasswd))
                    return true;
                else
                    return false;
            }
        }
        return false;
    }
}