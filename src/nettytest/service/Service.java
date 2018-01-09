package nettytest.service;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import utils.LogUtils;

public class Service {
    private static final String TAG = "ServiceA";
    private static final String IP = "127.0.0.1";
    private static final int PORT = 9999;
    /**
     * 用于分配处理业务线程的线程组个数
     */
    protected static final int BIZGROUPSIZE = Runtime.getRuntime().availableProcessors() * 2; //默认
    /**
     * 业务出现线程大小
     */
    protected static final int BIZTHREADSIZE = 4;
    /*
 * NioEventLoopGroup实际上就是个线程池,
 * NioEventLoopGroup在后台启动了n个NioEventLoop来处理Channel事件,
 * 每一个NioEventLoop负责处理m个Channel,
 * NioEventLoopGroup从NioEventLoop数组里挨个取出NioEventLoop来处理Channel
 */
    private static final EventLoopGroup bossGroup = new NioEventLoopGroup(BIZGROUPSIZE);
    private static final EventLoopGroup workerGroup = new NioEventLoopGroup(BIZTHREADSIZE);

    protected static void run()  {
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            //netty3中对应设置如下
            //bootstrap.setOption("backlog", 1024);
            //bootstrap.setOption("tcpNoDelay", true);
            //bootstrap.setOption("keepAlive", true);
            bootstrap.childOption(ChannelOption.SO_BACKLOG,2048);//serverSocketchannel的设置，链接缓冲池的大小
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE,true);//socketchannel的设置,维持链接的活跃，清除死链接
            bootstrap.childOption(ChannelOption.TCP_NODELAY,true);//socketchannel的设置关闭延时发送

            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                            pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                            pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
                            pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
                            pipeline.addLast(new TcpServerHandler());
                        }
                    }).bind(IP, PORT).sync();
            LogUtils.d(TAG, "TCP服务器已启动");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            shutdown();
        }
    }

    protected static void shutdown() {
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }

    public static void main(String[] args) throws Exception {
        LogUtils.d(TAG, "开始启动TCP服务器...");
        Service.run();
    }
}
