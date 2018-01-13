package coder.androidclient.service.pool;

import java.nio.channels.SocketChannel;

public interface Worker {
    void  registerWorkerChannelTask(SocketChannel socketChannel);
}
