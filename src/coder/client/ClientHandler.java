package coder.client;

import coder.response.Response;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutorGroup;

import java.nio.ByteBuffer;

public class ClientHandler extends SimpleChannelInboundHandler<Response>{
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Response response) throws Exception {
        System.out.println("response:"+new String(response.getData()));
    }
}
