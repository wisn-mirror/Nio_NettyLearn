package nettyhart.server;

import io.netty.channel.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.EventExecutorGroup;
import utils.LogUtils;

public class HeatTcpServerHandler extends SimpleChannelInboundHandler<String>{
    private static final String TAG="HeatTcpServerHandler";
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        LogUtils.d(TAG," message:"+s);
        if(s.equals("aa")){
            channelHandlerContext.channel().writeAndFlush(" receive:"+s);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if(evt instanceof IdleStateEvent){
            IdleStateEvent idleStateEvent= (IdleStateEvent) evt;
            LogUtils.d(TAG,"aaa idle state event   "+evt+IdleStateEvent.READER_IDLE_STATE_EVENT+IdleStateEvent.WRITER_IDLE_STATE_EVENT+IdleStateEvent.ALL_IDLE_STATE_EVENT);
            if(idleStateEvent.state()== IdleState.READER_IDLE){
//                ctx.writeAndFlush("red idle state event  close");
                LogUtils.d(TAG," red idle state event  close:");
            }else if(idleStateEvent.state()==IdleState.WRITER_IDLE){
//                ctx.writeAndFlush("write idle state event  close");
                LogUtils.d(TAG," write idle state event  close:");
            }else if(idleStateEvent.state()==IdleState.ALL_IDLE){
                ChannelFuture all_idle_state_event = ctx.writeAndFlush("all idle state event  close");
                LogUtils.d(TAG," all idle state event  close:");
                all_idle_state_event.addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        ctx.channel().close();
                    }
                });
            }
        }else{
            super.userEventTriggered(ctx, evt);
        }
    }

    /**
     * 新的客户端接入
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        LogUtils.d(TAG," channelActive");
    }

    /**
     * 断开
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        LogUtils.d(TAG," channelInactive");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        LogUtils.d(TAG," exceptionCaught");
    }
}
