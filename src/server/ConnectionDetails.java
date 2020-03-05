package server;

public class ConnectionDetails {
    private String hostName;
    private int portNumber;
    private String fileName;
    private int noOfPartition;

    private static String ESTABLISH_CONNECTION = "0";
    private static String SEND_DATA = "1";
    private static String SEND_META_DATA = "2";
    private static String SEND_ACK = "3";

    private static String CONNECTED = "4";
    private static String DISCONNECTED = "5";
    private static String IS_CONNECTING = "6";

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getNoOfPartition() {
        return noOfPartition;
    }

    public void setNoOfPartition(int noOfPartition) {
        this.noOfPartition = noOfPartition;
    }
}
