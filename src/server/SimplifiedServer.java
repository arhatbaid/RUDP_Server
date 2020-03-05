package server;

public class SimplifiedServer {
    private static ConnectionDetails connectionDetails = null;

    public static void main(String args[]) throws Exception {
        System.out.println("Ready!");
        setServerInfo();

        ConnectionClass connectionClass = new ConnectionClass(connectionDetails);
        connectionClass.receiveDataFromClient();
        /*if (receivedData.length > 0) {
            connectionClass.writeToFile(receivedData);
            connectionClass.sendAckToClient("1");
        } else {
            connectionClass.sendAckToClient("0");
        }*/
    }

    private static void setServerInfo() {
        connectionDetails = new ConnectionDetails();
        connectionDetails.setHostName("localhost");
        connectionDetails.setPortNumber(5555);
    }
}
