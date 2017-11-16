package nettytest.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class TcpClientHandler extends SimpleChannelInboundHandler<Object> {
    private static final  String TAG="TcpClientHandler";
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
//        LogUtils.d(TAG,"client receive service msg:"+o);
    }
}
