package nettyhart.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import utils.LogUtils;

public class Service {
    private static final String TAG ="Service" ;

    public static void main(String[] args){
        ServerBootstrap bootstrap=new ServerBootstrap();
        EventLoopGroup  bossGroup=new NioEventLoopGroup(4);
        EventLoopGroup  workGroup=new NioEventLoopGroup(Runtime.getRuntime().availableProcessors()*2);
        bootstrap.group(bossGroup,workGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>(){

            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ChannelPipeline pipeline = socketChannel.pipeline();
//                pipeline.addLast("frameDecoder",new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,10,0,10));
//                pipeline.addLast("frameEncoder",new LengthFieldPrepender(4));
                pipeline.addLast(new IdleStateHandler(5, 5, 10));
                pipeline.addLast("decoder",new StringDecoder(CharsetUtil.UTF_8));
                pipeline.addLast("encoder",new StringEncoder(CharsetUtil.UTF_8));
                pipeline.addLast(new HeatTcpServerHandler());
            }
        });
        try {
            bootstrap.bind("127.0.0.1",9999).sync();
            LogUtils.d(TAG," start");
        } catch (InterruptedException e) {
            e.printStackTrace();
            LogUtils.d(TAG, e.getMessage());
        }
//        } finally {
//            bossGroup.shutdownGracefully();
//            workGroup.shutdownGracefully();
//        }
    }
}
