package nettyw;

import nettyw.pool.NioSelectorRunnablePool;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Start {
    public static void main(String[] args){
        NioSelectorRunnablePool nioSelectorRunnablePool=new NioSelectorRunnablePool(Executors.newCachedThreadPool(),Executors.newCachedThreadPool());
        ServerBootstrap serverBootstrap=new ServerBootstrap(nioSelectorRunnablePool);
        InetSocketAddress inetSocketAddress=  new InetSocketAddress("127.0.0.1", 8000);
        serverBootstrap.bind(inetSocketAddress);
    }
}
