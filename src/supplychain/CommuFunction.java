package supplychain;

import java.io.*;
import java.net.Socket;
import java.util.Vector;

public class CommuFunction {
    private SupplyChainPresenter presenter;
    private SupplyChainModel model;
    private SupplyChainView view;

    public CommuFunction(SupplyChainPresenter presenter){
        this.presenter=presenter;
        this.model=presenter.getModel();
        this.view=presenter.getView();
    }

    public void pullUserInfo(String userName) {
        try{
            String kernelAddr = model.getKernelAddress();
            int kernelPort = model.getKernelPort();

            Socket socket = new Socket(kernelAddr, kernelPort);

            OutputStream ops = socket.getOutputStream();
            InputStream ips = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();

            String message = "ASKUSERINFO";
            ops.write(message.getBytes("UTF-8"));
            socket.shutdownOutput();
            while ((len = ips.read(bytes)) != -1) {
                sb.append(new String(bytes, 0, len, "UTF-8"));
            }
            ips.close();
            ops.close();
            socket.close();
            File f = new File(userName + File.separator + "UserInfo.txt");
            FileOutputStream fs = new FileOutputStream(f);
            fs.write(sb.toString().getBytes("UTF-8"));
            fs.close();
            System.out.println("获取用户信息完成");
            presenter.addLog("获取用户信息完成");
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void sendRegisterInfo(ChainNodeInfo n){
        try{
            String userName=n.getUserName();
            String userPasswd=n.getUserPasswd();
            String userStatus=n.getUserStatus();
            String userAddress=n.getUserAddress();
            String userPort=String.valueOf(n.getUserPort());

            String kernelAddr=model.getKernelAddress();
            int kernelPort=model.getKernelPort();

            Socket socket=new Socket(kernelAddr,kernelPort);
            String message="ADDUSER "+userName+" "+userPasswd+" "+userStatus+" "+userAddress+" "+userPort;

            OutputStream ops = socket.getOutputStream();
            ops.write(message.getBytes("UTF-8"));
            socket.shutdownOutput();

            ops.close();
            socket.close();
            System.out.println("发送注册完成");
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void serverHandle(Socket socket){
        try {
            InputStream ips = socket.getInputStream();
            OutputStream ops = socket.getOutputStream();
            byte[] bytes = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = ips.read(bytes)) != -1) {
                sb.append(new String(bytes, 0, len, "UTF-8"));
            }
            String[] orders=sb.toString().split(" ");
            if (orders[0].equals("ASKUSERINFO")) {
                ChainNodeInfo curUser = model.getCurUserInfo();
                String userName = curUser.getUserName();
                FileReader fr=new FileReader(userName + File.separator + "UserInfo.txt");
                BufferedReader br = new BufferedReader(fr);
                String s;
                StringBuilder userInfoSb = new StringBuilder();
                while ((s = br.readLine()) != null) {
                    userInfoSb.append(new String(s.getBytes("UTF-8"))+"\n");
                }
                br.close();
                ops.write(userInfoSb.toString().getBytes("UTF-8"));
                socket.shutdownOutput();
            }
            ips.close();
            ops.close();
            socket.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}