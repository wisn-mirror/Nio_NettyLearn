package coder.androidclient.service;


import coder.androidclient.service.pool.NioSelectorRunnablePool;
import coder.request.Request;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executors;

public class Start {

    private static HandlerByteToMessage handlerByteToMessage;

    public static void main(String[] args) throws InterruptedException {
        handlerByteToMessage = new HandlerByteToMessage();
        NioSelectorRunnablePool nioSelectorRunnablePool=new NioSelectorRunnablePool(Executors.newCachedThreadPool(),Executors.newCachedThreadPool(),Executors.newCachedThreadPool(), handlerByteToMessage);
        ClientBootstrap serverBootstrap=new ClientBootstrap(nioSelectorRunnablePool);
        InetSocketAddress inetSocketAddress=  new InetSocketAddress("127.0.0.1", 9999);
        SocketChannel connect = serverBootstrap.connect(inetSocketAddress);
        Thread.sleep(1000);
        for(int i=0;i<100000;i++){
            Request request = Request.valueOf((short) 1, (short) 2, ("hello"+i).getBytes());
            //在这里可以给服务端发送信息哦
            try {
                if(connect.isConnectionPending()){
                   connect.finishConnect();
                }
                System.out.println("send:"+i);
                connect.write(handlerByteToMessage.getBytes(request));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
