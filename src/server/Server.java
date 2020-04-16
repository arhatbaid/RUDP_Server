package server;

import model.PacketAck;

import java.io.File;

public class Server implements ServerImpl.Listener {

    private static ServerImpl serverImpl = null;
    private ServerImpl.Listener listener = this;

    public static void main(String[] args) {
        File f = new File("screen_1.jpeg");
        if (f != null && f.exists()){

        }
       /* System.out.println("Ready!");
        Server server = new Server();
        serverImpl = new ServerImpl(server.listener);
        serverImpl.initServer();*/
    }

    @Override
    public void onServerInitializedSuccessfully() {
        serverImpl.receiveDataFromClient();
    }

    @Override
    public void onDataReceivedFromClient(PacketAck packetAck) {
        serverImpl.sendAckToClient(packetAck);
    }
}