package nettyw;

import nettyw.pool.Boss;
import nettyw.pool.NioSelectorRunnablePool;

import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.Executor;

public class NioServerBoss implements Boss {
    private NioSelectorRunnablePool nioSelectorRunnablePool;

    public NioServerBoss(Executor executor, String threadName, NioSelectorRunnablePool nioSelectorRunnablePool) {

    }

    @Override
    public void registerAcceptChannelTask(ServerSocketChannel serverSocketChannel) {

    }
}
