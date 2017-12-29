package nettyw;


import nettyw.pool.NioSelectorRunnablePool;
import nettyw.pool.Worker;

import java.nio.channels.SocketChannel;
import java.util.concurrent.Executor;

public class NioServerWorker implements Worker {
    public NioServerWorker(Executor executor, String threadName, NioSelectorRunnablePool nioSelectorRunnablePool) {
    }

    @Override
    public void registerWorkerChannelTask(SocketChannel socketChannel) {

    }
}
