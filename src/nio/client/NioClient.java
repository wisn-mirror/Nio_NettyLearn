package nio.client;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NioClient {

    private SocketChannel channel;

    public void init(String address, int port) throws IOException {
        InetSocketAddress socketAddress=new InetSocketAddress(address,port);
        channel = SocketChannel.open(socketAddress);
    }
    public void  sendMsg(String msg) throws IOException {
        channel.write(ByteBuffer.wrap(msg.getBytes()));
    }
    public void getMessage() throws IOException {
        ByteBuffer byteBuffer=ByteBuffer.allocate(256);
        while (true){
            byteBuffer.clear();
            channel.read(byteBuffer);
            System.out.println("receive  : "+new String(byteBuffer.array()));
        }
    }


    public static void main(String[] args) throws Exception {
        NioClient nioClient=new NioClient();
        nioClient.init("localhost",8883);
        nioClient.sendMsg("hello nio");
        nioClient.getMessage();
    }
}
