package nettyw;


import nettyw.pool.Boss;
import nettyw.pool.NioSelectorRunnablePool;

import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;

public class ServerBootstrap {
    private NioSelectorRunnablePool nioSelectorRunnablePool;

    public ServerBootstrap(NioSelectorRunnablePool nioSelectorRunnablePool) {
        this.nioSelectorRunnablePool = nioSelectorRunnablePool;
    }

    public void bind(SocketAddress socketAddress) {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(socketAddress);
            Boss boss = nioSelectorRunnablePool.nextBoss();
            boss.registerAcceptChannelTask(serverSocketChannel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
