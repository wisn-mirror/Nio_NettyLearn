package aio.server;

public class AioServer {
    public volatile static long clientCount = 0;

    public static void main(String[] arg) {
        AsyncServerHandler asyncServerHandler = new AsyncServerHandler(12345);
        new Thread(asyncServerHandler).start();
    }
}
