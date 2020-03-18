package server;

import model.EstablishConnection;
import model.NetworkData;
import network.NetworkCalls;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ServerImpl {
    private NetworkData networkData = null;
    private NetworkCalls networkCalls = null;

    public ServerImpl() {
        NetworkData networkData = setNetworkData();
        networkCalls = new NetworkCalls(networkData);
        networkCalls.initConnection();
    }


    private static NetworkData setNetworkData() {
        NetworkData networkData = new NetworkData();
        networkData.setClientName("localhost");
        networkData.setPortNumber(5555);
        return networkData;
    }

    protected void receiveDataFromClient() {
        Object receivedObj = networkCalls.receiveDataFromClient();
        if (receivedObj == null) return; //TODO Ask for retransmission

        if (receivedObj instanceof EstablishConnection) {
            sendConnectionAckToClient((EstablishConnection) receivedObj);
        } else if (receivedObj instanceof NetworkData) {

        } else if (receivedObj instanceof NetworkData) {

        } else if (receivedObj instanceof NetworkData) {

        }
        System.out.println("ConnectionEstablish object received = " + receivedObj);
    }

    private void sendConnectionAckToClient(EstablishConnection establishConnection) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream os = null;
        try {
            os = new ObjectOutputStream(outputStream);
            os.writeObject(establishConnection);
        } catch (IOException e) {
            e.printStackTrace();
        }
        networkCalls.sendAckToClient(outputStream.toByteArray());
    }

}
