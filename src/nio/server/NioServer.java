package nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;

public class NioServer {

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;

    public void initServer(String address, int port) throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(address, port));
        selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void listen() throws IOException {

        while(true){
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while(iterator.hasNext()){
                SelectionKey  key=iterator.next();
                if(key.isAcceptable()){
                    SocketChannel channel=serverSocketChannel.accept();
                    channel.configureBlocking(false);
                    channel.register(selector,SelectionKey.OP_READ);
                }else if(key.isReadable()){
                    recvAndReply(key);
                }else if(key.isConnectable()){

                }else  if(key.isValid()){

                }else if(key.isWritable()){

                }
            }
        }
    }

    private void recvAndReply(SelectionKey key) throws IOException {
        SocketChannel channel= (SocketChannel) key.channel();
        ByteBuffer allocate = ByteBuffer.allocate(256);
        int read = channel.read(allocate);
        if(read!=-1){
            String msg=new String(allocate.array()).trim();
            System.out.println("server received message:"+msg);
            channel.write(ByteBuffer.wrap((msg+new Date()).getBytes()));
        }else{
            channel.close();
        }
     }

    public static void main(String[] args) throws Exception {
            NioServer nioServer=new NioServer();
            nioServer.initServer("localhost",8883);
            nioServer.listen();
    }
}
