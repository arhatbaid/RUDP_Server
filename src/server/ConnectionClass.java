package server;

import model.ConnectionDetails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ConnectionClass {

    private static ConnectionDetails connectionDetails = null;
    private static DatagramSocket socket = null;
    private static InetAddress address = null;
    private static DatagramPacket receivedDataPacket = null, sendAckPacket = null;
    public static final int MAX_BUFFER_SIZE= 65507;
    private static boolean IS_SERVER_RUNNING= false;

    public ConnectionClass(ConnectionDetails connectionDetails) {
        ConnectionClass.connectionDetails = connectionDetails;
    }

    public void initConnection() {
        try {
            socket = new DatagramSocket(connectionDetails.getPortNumber());
            IS_SERVER_RUNNING = true;
        } catch (IOException e) {
            IS_SERVER_RUNNING = false;
            e.printStackTrace();
        }
    }

    public void writeToFile(byte[] data) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            BufferedImage bImage2 = ImageIO.read(bis);
            ImageIO.write(bImage2, "jpg", new File("output.jpg"));
            System.out.println("image created");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("image failed");
        }
    }

    public byte[] receiveDataFromClient() {
        byte[] arrData = new byte[MAX_BUFFER_SIZE];
        receivedDataPacket = new DatagramPacket(arrData, MAX_BUFFER_SIZE);
        try {
            socket.receive(receivedDataPacket);
            arrData = receivedDataPacket.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrData;
    }

    public void sendAckToClient(String status) {
        byte[] arrAck = status.getBytes();
        try {
            sendAckPacket = new DatagramPacket(arrAck, arrAck.length, receivedDataPacket.getAddress(), receivedDataPacket.getPort());
            socket.send(sendAckPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isServerRunning() {
        return IS_SERVER_RUNNING && socket != null;
    }
}
