package server;

public class Server {

    private static ServerImpl serverImpl = null;

    public static void main(String[] args) throws Exception {
        System.out.println("Ready!");
        serverImpl = new ServerImpl();
        serverImpl.receiveDataFromClient();
    }
}
