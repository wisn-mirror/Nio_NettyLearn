package nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class NioServer {

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private Charset charset = Charset.forName("UTF-8");

    public void initServer(String address, int port) throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(address, port));
        selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void listen() throws IOException {

        while (true) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isAcceptable()) {
                    //接受请求
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel accept = server.accept();
                    System.out.println("accept:  " + accept);
                    accept.configureBlocking(false);
                    accept.register(selector, SelectionKey.OP_READ);
                    bordCast(key,accept+"上线");
                } else if (key.isReadable()) {
                    recvAndReply(key);
                } else if (key.isConnectable()) {

                } else if (key.isValid()) {

                } else if (key.isWritable()) {

                }
            }
        }
    }


    private void recvAndReply(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer allocate = ByteBuffer.allocate(256);
        int read = channel.read(allocate);
        if (read != -1) {
            String msg = new String(allocate.array()).trim();
            System.out.println("server received message:" + msg);
            bordCast(key,channel+"发送消息："+msg+"\n");
//            channel.write(ByteBuffer.wrap((msg + new Date()).getBytes()));
        } else {
            channel.close();
        }
    }

    public void bordCast(SelectionKey key,String msg){
        Set<SelectionKey> keys = selector.keys();
        Iterator<SelectionKey> iterator = keys.iterator();
        while(iterator.hasNext()){
            SelectionKey next = iterator.next();
            SelectableChannel channel = next.channel();
            if(channel instanceof SocketChannel&&next!=key){
                try {
                    ((SocketChannel) channel).write(charset.encode(msg));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) throws Exception {
        NioServer nioServer = new NioServer();
        nioServer.initServer("10.0.34.22", 8883);
        nioServer.listen();
    }
}
