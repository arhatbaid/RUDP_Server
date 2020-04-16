package server;

import model.*;
import network.NetworkCalls;

import java.io.*;

public class ServerImpl {
    private NetworkData networkData = null;
    private NetworkCalls networkCalls = null;
    private static Listener listener = null;
    private static ImageChunksMetaData[] arrImagesChunkData = null;
    File f;
    FileOutputStream fo = null;

    public ServerImpl(Listener listener) {
        ServerImpl.listener = listener;
    }

    public ServerImpl() {
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
                packetAck.setClientId((establishConnection.getClientId()));
                packetAck.setSeqNo(establishConnection.getSeqNo());
                packetAck.setTransmissionType(establishConnection.getTransmissionType());
//                System.out.println("EstablishConnection received\n" + receivedObj);
                listener.onDataReceivedFromClient(packetAck);
                //TODO save EstablishConnection data on server side
            } else if (receivedObj instanceof ImageMetaData) {
                ImageMetaData imageMetaData = (ImageMetaData) receivedObj;
                packetAck.setClientId((imageMetaData.getClientId()));
                packetAck.setSeqNo(imageMetaData.getSeqNo());
                packetAck.setTransmissionType(imageMetaData.getTransmissionType());
//                System.out.println("ImageMetaData received\n" + receivedObj);
                arrImagesChunkData = imageMetaData.getArrImageChunks();
                listener.onDataReceivedFromClient(packetAck);
                //TODO save ImageMetaData data on server side
            } else if (receivedObj instanceof DataTransfer) {
                DataTransfer dataTransfer = (DataTransfer) receivedObj;
                packetAck.setClientId((dataTransfer.getClientId()));
                packetAck.setSeqNo(dataTransfer.getSeqNo());
                packetAck.setTransmissionType(dataTransfer.getTransmissionType());
                packetAck.setIsLastPacket(dataTransfer.getIsLastPacket());
//                System.out.println("DataTransfer received\n" + receivedObj);

                try {
                    if (dataTransfer.getIsFirstPacketOfImageBlock() == 1) {
                        f = new File(arrImagesChunkData[dataTransfer.getCurrentImageSeqNo() - 1].getImageName());
                        fo = new FileOutputStream(f);
                    }
                    fo.write(dataTransfer.getArrImage());
                    networkCalls.receiveTempImage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (dataTransfer.getIsLastPacket() == 1) {
                    listener.onDataReceivedFromClient(packetAck);
                }
                //TODO save/update DataTransfer data on server side
            } else {
                //TODO object corrupt or not identified.
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

    public static ImageChunksMetaData[] getArrImagesChunkData() {
        return arrImagesChunkData;
    }

    interface Listener {

        void onServerInitializedSuccessfully();

        void onDataReceivedFromClient(PacketAck packetAck);

    }

    private static NetworkData setNetworkData() {
        NetworkData networkData = new NetworkData();
        networkData.setClientName("localhost");
        networkData.setPortNumber(5555);
        return networkData;
    }



}
