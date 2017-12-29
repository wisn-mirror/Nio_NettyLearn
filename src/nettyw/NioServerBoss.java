package nettyw;

import nettyw.pool.Boss;
import nettyw.pool.NioSelectorRunnablePool;
import nettyw.pool.Worker;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;

public class NioServerBoss  extends  AbstractNioSelector implements Boss {

    public NioServerBoss(Executor executor, String threadName, NioSelectorRunnablePool nioSelectorRunnablePool) {
        super(executor, threadName, nioSelectorRunnablePool);
    }

    @Override
    public void selector(Selector selector) {
        try {
            selector.select();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(Selector selector) {
        try {
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator =selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                ServerSocketChannel server = (ServerSocketChannel) key.channel();
                SocketChannel accept = server.accept();
                System.out.println("accept:  " + accept);
                accept.configureBlocking(false);
                Worker worker = nioSelectorRunnablePool.nextWorker();
                worker.registerWorkerChannelTask(accept);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerAcceptChannelTask(ServerSocketChannel serversocketchannel) {
        registerTask(new Runnable() {
            @Override
            public void run() {
                try {
                    serversocketchannel.register(selector,SelectionKey.OP_ACCEPT);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
