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
        try {
            socket = new DatagramSocket(connectionDetails.getPortNumber());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveDataFromClient() {
        byte[] data = new byte[64999];
        int count = 1;
        byte[] finalImageArray = new byte[130000];
        while (128535 > 65000 * (count - 1)) {
            try {
                receivedDataPacket = new DatagramPacket(data, data.length);
                socket.receive(receivedDataPacket);
                data = receivedDataPacket.getData();
                System.arraycopy(data, 0, finalImageArray, data.length * (count - 1), data.length);
                count++;
                writeToFile(finalImageArray);
            } catch (IOException e) {
                e.printStackTrace();
                data = new byte[0];
            }
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


    public byte[] receiveAckFromClient() {
        byte[] ack = new byte[1024];
        sendAckPacket = new DatagramPacket(ack, ack.length);
        try {
            socket.receive(sendAckPacket);
            return receivedDataPacket.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public void sendAckToClient(String status) {
        byte[] ack = status.getBytes();
        try {
            receiveAckPacket = new DatagramPacket(ack, ack.length, receivedDataPacket.getAddress(), receivedDataPacket.getPort());
            socket.send(receiveAckPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
