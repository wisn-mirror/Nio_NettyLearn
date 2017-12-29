package nettyw.pool;

import java.nio.channels.SocketChannel;

public interface Worker {
    void  registerWorkerChannelTask(SocketChannel socketChannel);
}
