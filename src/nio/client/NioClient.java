package nio.client;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

public class NioClient {

    private SocketChannel channel;
    private Selector selector;

    public void init(String address, int port) throws IOException {
        InetSocketAddress socketAddress = new InetSocketAddress(address, port);
        channel = SocketChannel.open(socketAddress);
        channel.configureBlocking(false);
        //打开选择器，注册到信道
        selector = Selector.open();
        SelectionKey selectionKey = channel.register(selector, SelectionKey.OP_READ);
        getMessage(selector);

    }

    public void sendMsg(String msg) throws IOException {
        channel.write(ByteBuffer.wrap(msg.getBytes()));
    }

    public void getMessage(Selector selector) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (selector.select() > 0) {
                        Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                        while (iterator.hasNext()) {
                            SelectionKey next = iterator.next();
                            if (next == null) continue;
                            if (next.isReadable()) {
                                SocketChannel channel = (SocketChannel) next.channel();
                                ByteBuffer allocate = ByteBuffer.allocate(256);
                                channel.read(allocate);
                                allocate.flip();
                                String receivedString = Charset.forName("UTF-16").newDecoder().decode(allocate).toString();
                                System.out.println("receive  : " + receivedString);
                            }
                            //为下次读取做准备
                            next.interestOps(SelectionKey.OP_READ);
                            selector.selectedKeys().remove(next);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    public static void main(String[] args) throws Exception {
        NioClient nioClient = new NioClient();
        nioClient.init("localhost", 8883);
        nioClient.sendMsg("hello nio");
    }
}
