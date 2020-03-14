package server;

import model.ConnectionDetails;
import model.ConnectionEstablish;

import java.util.Arrays;

import static server.ConnectionClass.MAX_BUFFER_SIZE;

public class SimplifiedServer {
    private static ConnectionDetails connectionDetails = null;
    private static ConnectionClass connectionClass = null;
    private static ConnectionEstablish connectionEstablish = null;

    public static void main(String[] args) throws Exception {
        System.out.println("Ready!");
        setServerInfo();

        connectionClass = new ConnectionClass(connectionDetails);
        connectionClass.initConnection();
        byte[] receivedData = connectionClass.receiveDataFromClient();
        if (!Arrays.equals(receivedData, new byte[MAX_BUFFER_SIZE])) {
            switch (receivedData[2]) {
                case 0:
                    connectEst(receivedData);
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                default:
                    break;
            }
        }
    }

    private static void connectEst(byte[] receivedData) {
        connectionEstablish = new ConnectionEstablish();
        connectionEstablish.setClient_id(receivedData[0]);
        connectionEstablish.setSeq_no(receivedData[1]);
        connectionEstablish.setTransmission_type(receivedData[2]);
        connectionEstablish.setRetransmission_timeout(receivedData[3]);
        connectionEstablish.toString();
    }

    private static void setServerInfo() {
        connectionDetails = new ConnectionDetails();
        connectionDetails.setClientName("localhost");
        connectionDetails.setPortNumber(5555);
    }
}
