package coder.androidclient.service.pool;

import java.nio.channels.SocketChannel;

public interface Business {
    void  registerBusinessChannelTask(SocketChannel socketChannel);

}
