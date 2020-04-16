package server;

import model.PacketAck;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements ServerImpl.Listener {

    private static ServerImpl serverImpl = null;
    private ServerImpl.Listener listener = this;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

        System.out.println("Ready!");
        DemoApplication server = new DemoApplication();
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
