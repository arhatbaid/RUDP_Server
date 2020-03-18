package server;

import model.PacketAck;

public class Server implements ServerImpl.Listener {

    private static ServerImpl serverImpl = null;
    private ServerImpl.Listener listener = this;

    public static void main(String[] args) {
        System.out.println("Ready!");
        Server server = new Server();
        serverImpl = new ServerImpl(server.listener);
        serverImpl.initServer();
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