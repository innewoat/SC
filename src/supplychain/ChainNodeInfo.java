package supplychain;

public class ChainNodeInfo {
    String userName;
    String userPasswd;
    String userStatus;
    String userAddress;
    int userPort;

    public ChainNodeInfo(String userName, String userPasswd, String userStatus, String userAddress, int userPort) {
        this.userName = userName;
        this.userPasswd = userPasswd;
        this.userStatus = userStatus;
        this.userAddress = userAddress;
        this.userPort = userPort;
    }

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

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public int getUserPort() {
        return userPort;
    }

    public void setUserPort(int userPort) {
        this.userPort = userPort;
    }

    @Override
    public String toString() {
        return  userName + " " + userPasswd + " " + userStatus + " " + userAddress + " " + userPort + "\n";
    }
}