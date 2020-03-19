package server;

import model.*;
import network.NetworkCalls;

import java.io.*;

public class ServerImpl {
    private NetworkData networkData = null;
    private NetworkCalls networkCalls = null;
    private static Listener listener = null;
    public static long FILE_SIZE = 0l;
    public ServerImpl(Listener listener) {
        ServerImpl.listener = listener;
    }


    private static NetworkData setNetworkData() {
        NetworkData networkData = new NetworkData();
        networkData.setClientName("localhost");
        networkData.setPortNumber(5555);
        return networkData;
    }

    protected void initServer() {
        NetworkData networkData = setNetworkData();
        networkCalls = new NetworkCalls(networkData);
        networkCalls.initConnection();
        listener.onServerInitializedSuccessfully();
    }

    protected void receiveDataFromClient() {
        while (true) {
            Object receivedObj = networkCalls.receiveDataFromClient();
            if (receivedObj == null) return; //TODO Ask for retransmission

            PacketAck packetAck = new PacketAck();
            if (receivedObj instanceof EstablishConnection) {
                EstablishConnection establishConnection = (EstablishConnection) receivedObj;
                packetAck.setClient_id((establishConnection.getClient_id()));
                packetAck.setSeq_no(establishConnection.getSeq_no());
                packetAck.setTransmissionType(establishConnection.getTransmissionType());
                System.out.println("EstablishConnection received\n" + receivedObj);
                listener.onDataReceivedFromClient(packetAck);
                //TODO save EstablishConnection data on server side
            } else if (receivedObj instanceof ImageMetaData) {
                ImageMetaData imageMetaData = (ImageMetaData) receivedObj;
                packetAck.setClient_id((imageMetaData.getClient_id()));
                packetAck.setSeq_no(imageMetaData.getSeq_no());
                packetAck.setTransmissionType(imageMetaData.getTransmissionType());
                System.out.println("ImageMetaData received\n" + receivedObj);
                FILE_SIZE = imageMetaData.getFile_length();
                listener.onDataReceivedFromClient(packetAck);
                //TODO save ImageMetaData data on server side
            } else if (receivedObj instanceof DataTransfer) {
                DataTransfer dataTransfer = (DataTransfer) receivedObj;
                packetAck.setClient_id((dataTransfer.getClient_id()));
                packetAck.setSeq_no(dataTransfer.getSeq_no());
                packetAck.setTransmissionType(dataTransfer.getTransmissionType());
                packetAck.setIsLastPacket(dataTransfer.isLastPacket());
                System.out.println("DataTransfer received\n" + receivedObj);
                listener.onDataReceivedFromClient(packetAck);
                //TODO save/update DataTransfer data on server side
            } else {
                //TODO object corrupt or not identified.
                File f = new File("Output.jpeg");
                try {
                    FileOutputStream fo = new FileOutputStream(f);
                    networkCalls.receiveTempImage(fo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void sendAckToClient(PacketAck packetAck) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream os = null;
        try {
            os = new ObjectOutputStream(outputStream);
            os.writeObject(packetAck);
        } catch (IOException e) {
            e.printStackTrace();
        }
        networkCalls.sendAckToClient(outputStream.toByteArray());
    }

    interface Listener {
        void onServerInitializedSuccessfully();

        void onDataReceivedFromClient(PacketAck packetAck);
    }

}
