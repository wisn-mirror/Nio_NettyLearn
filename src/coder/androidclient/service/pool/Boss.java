package coder.androidclient.service.pool;


import java.nio.channels.SocketChannel;


public interface Boss {
   void  registerAcceptChannelTask(SocketChannel serverSocketChannel);
}
