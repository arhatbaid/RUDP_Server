package server;

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
    private static DatagramPacket receivedDataPacket = null, sendAckPacket = null, receiveAckPacket = null;

    public ConnectionClass(ConnectionDetails connectionDetails) {
        ConnectionClass.connectionDetails = connectionDetails;
    }

    public byte[] intUDPServer() {
        byte[] data = new byte[30126];
        try {
            socket = new DatagramSocket(5555);
            receivedDataPacket = new DatagramPacket(data, data.length);
            socket.receive(receivedDataPacket);
            data = receivedDataPacket.getData();
        } catch (IOException e) {
            e.printStackTrace();
            data = new byte[0];
        }
        return data;
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


    public void receiveAckFromClient() {
        byte[] ack = new byte[2];
        sendAckPacket = new DatagramPacket(ack, ack.length);
        try {
            socket.receive(sendAckPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendAckToClient(String status) {
        byte[] ack = status.getBytes();
        try {
            receiveAckPacket = new DatagramPacket(ack, ack.length, InetAddress.getByName(connectionDetails.getHostName()), connectionDetails.getPortNumber());
            socket.send(receiveAckPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
