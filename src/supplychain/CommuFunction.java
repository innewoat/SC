package supplychain;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;

public class CommuFunction {
    private SupplyChainPresenter presenter;
    private SupplyChainModel model;
    private SupplyChainView view;

    public CommuFunction(SupplyChainPresenter presenter) {
        this.presenter = presenter;
        this.model = presenter.getModel();
        this.view = presenter.getView();
    }

    public void askFile(String userName, String addr, int port, String fileName) {
        try {
            Socket socket = new Socket(addr, port);

            OutputStream ops = socket.getOutputStream();
            InputStream ips = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();

            String message = "ASKFILE " + fileName;
            ops.write(message.getBytes("UTF-8"));
            socket.shutdownOutput();
            while ((len = ips.read(bytes)) != -1) {
                sb.append(new String(bytes, 0, len, "UTF-8"));
            }
            ips.close();
            ops.close();
            socket.close();
            File f = new File(userName + File.separator + fileName);
            FileOutputStream fs = new FileOutputStream(f);
            fs.write(sb.toString().getBytes("UTF-8"));
            fs.close();
            System.out.println("获取" + fileName + "完成");
            presenter.addLog("获取" + fileName + "完成");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void askFile(String userName, ChainNodeInfo asked, String fileName) {
        askFile(userName, asked.getUserAddress(), asked.getUserPort(), fileName);
    }

    public void sendRegisterInfo(ChainNodeInfo n) {
        try {
            String userName = n.getUserName();
            String userPasswd = n.getUserPasswd();
            String userStatus = n.getUserStatus();
            String userAddress = n.getUserAddress();
            String userPort = String.valueOf(n.getUserPort());

            String kernelAddr = model.getKernelAddress();
            int kernelPort = model.getKernelPort();

            Socket socket = new Socket(kernelAddr, kernelPort);
            String message = "ADDUSER " + userName + " " + userPasswd + " " + userStatus + " " + userAddress + " " + userPort;

            OutputStream ops = socket.getOutputStream();
            ops.write(message.getBytes("UTF-8"));
            socket.shutdownOutput();

            ops.close();
            socket.close();
            System.out.println("发送注册完成");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void sendPullRequest() {

    }

    public void initializeTransactionBlock(String userName) {
        try {
            String kernelAddr = model.getKernelAddress();
            int kernelPort = model.getKernelPort();

            askFile(userName, kernelAddr, kernelPort, "transaction" + File.separator + "info.txt");
            FileReader fr = new FileReader(userName + File.separator + "transaction" + File.separator + "info.txt");
            BufferedReader br = new BufferedReader(fr);
            String s = br.readLine();
            br.close();
            fr.close();
            String[] t = s.split(" ");
            int index=Integer.valueOf(t[0]);
            for(int i=0;i<index;i++){
                askFile(userName,kernelAddr,kernelPort,"transaction"+File.separator+String.valueOf(i)+".txt");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initializeLoanModeABlock(String userName){
        try{
            String kernelAddr = model.getKernelAddress();
            int kernelPort = model.getKernelPort();

            askFile(userName, kernelAddr, kernelPort, "loanModeA" + File.separator + "info.txt");
            FileReader fr = new FileReader(userName + File.separator + "loanModeA" + File.separator + "info.txt");
            BufferedReader br = new BufferedReader(fr);
            String s = br.readLine();
            br.close();
            fr.close();
            String[] t = s.split(" ");
            int index=Integer.valueOf(t[0]);
            for(int i=0;i<index;i++){
                askFile(userName,kernelAddr,kernelPort,"loanModeA"+File.separator+String.valueOf(i)+".txt");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void initializeLoanModeBBlock(String userName){
        try{
            String kernelAddr = model.getKernelAddress();
            int kernelPort = model.getKernelPort();

            askFile(userName, kernelAddr, kernelPort, "loanModeB" + File.separator + "info.txt");
            FileReader fr = new FileReader(userName + File.separator + "loanModeB" + File.separator + "info.txt");
            BufferedReader br = new BufferedReader(fr);
            String s = br.readLine();
            br.close();
            fr.close();
            String[] t = s.split(" ");
            int index=Integer.valueOf(t[0]);
            for(int i=0;i<index;i++){
                askFile(userName,kernelAddr,kernelPort,"loanModeB"+File.separator+String.valueOf(i)+".txt");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendToAll(String message) {
        System.out.println(message);
        String uName = model.getUserName();
        for (ChainNodeInfo c : model.userList) {
            if (!c.userName.equals(uName))
                sendToOne(message, c);
        }
    }

    public void sendToOne(String message, ChainNodeInfo target) {
        try {
            Socket socket = new Socket(target.userAddress, target.userPort);

            OutputStream ops = socket.getOutputStream();
            ops.write(message.getBytes("UTF-8"));
            socket.shutdownOutput();
            ops.close();
            socket.close();
            System.out.println("发送给" + target.userName + "成功");
        } catch (Exception e) {
            System.out.println("发送给" + target.userName + "失败");
        }
    }

    public void serverHandle(Socket socket) {
        try {
            InputStream ips = socket.getInputStream();
            OutputStream ops = socket.getOutputStream();
            byte[] bytes = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = ips.read(bytes)) != -1) {
                sb.append(new String(bytes, 0, len, "UTF-8"));
            }
            String[] orders = sb.toString().split(" ");
            switch (orders[0]) {
                case "ASKFILE":
                    ChainNodeInfo curUser = model.getCurUserInfo();
                    String userName = curUser.getUserName();
                    FileReader fr = new FileReader(userName + File.separator + orders[1]);
                    BufferedReader br = new BufferedReader(fr);
                    String s;
                    StringBuilder content = new StringBuilder();
                    while ((s = br.readLine()) != null) {
                        content.append(new String(s.getBytes("UTF-8")) + "\n");
                    }
                    br.close();
                    fr.close();
                    ops.write(content.toString().getBytes("UTF-8"));
                    socket.shutdownOutput();
                    break;
                case "ADDUSER":
                    ChainNodeInfo n = new ChainNodeInfo(orders[1], orders[2], orders[3], orders[4], Integer.valueOf(orders[5]));
                    model.userList.add(n);
                    view.getMainForm().setUserList(model.userList);

                    File f = new File(model.getUserName() + File.separator + "UserInfo.txt");
                    FileOutputStream fs = new FileOutputStream(f);
                    for (ChainNodeInfo c : model.getUserInfoList()) {
                        fs.write(c.toString().getBytes("UTF-8"));
                    }
                    fs.close();
                    // TODO: request user to pull

                    break;
                case "ADDTRANSACTION":
                    TransactionInfo newTrans = new TransactionInfo(orders[1], orders[2], orders[3], orders[4], SupplyChainModel.sdf.parse(orders[5]));
                    model.addTransactionInfo(newTrans);
                    break;
                case "ADDLOANMODEA":
                    LoanModeAInfo newLoanA=new LoanModeAInfo(new TransactionInfo(orders[1],orders[2],orders[3],orders[4],SupplyChainModel.sdf.parse(orders[5])),orders[6]);
                    model.addLoanAModeInfo(newLoanA);
                    break;
                case "ADDLOANMODEB":
                    LoanModeBInfo newLoanB=new LoanModeBInfo(new TransactionInfo(orders[1],orders[2],orders[3],orders[4],SupplyChainModel.sdf.parse(orders[5])),orders[6]);
                    model.addLoanBModeInfo(newLoanB);
                    break;
                default:
                    if (orders.length > 0)
                        if (!orders[0].equals(""))
                            System.out.println("未知命令 " + orders[0]);
                    break;
            }
            ips.close();
            ops.close();
            socket.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}