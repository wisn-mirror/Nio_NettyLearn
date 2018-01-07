package nettytest.service;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import utils.LogUtils;

public class TcpServerHandler extends SimpleChannelInboundHandler<Object> {
    private static String TAG="TcpServerHandler";
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        channelHandlerContext.channel().writeAndFlush("service is accept you \" "+o+" \"");
        LogUtils.d(TAG,"channelRead0:"+o);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        LogUtils.e(TAG,cause.getMessage());
        ctx.close();
    }
}
