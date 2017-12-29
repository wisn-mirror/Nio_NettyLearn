package nettyw.pool;


import java.nio.channels.ServerSocketChannel;


public interface Boss {
   void  registerAcceptChannelTask(ServerSocketChannel serverSocketChannel);
}
