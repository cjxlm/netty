package io.netty.example.dubbo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.Map;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * DoubleServer
 *
 * @author caijinxun
 * @date 2020/4/3
 */
public class DoubleServer {

    static final boolean SSL = System.getProperty("ssl") != null;
    static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));


    /**
     * the cache for alive worker channel.
     * <ip:port, dubbo channel>
     */
    private Map<String, Channel> channels;

    /**
     * the boss channel that receive connections and dispatch these to worker channel.
     */
    private static  io.netty.channel.Channel channel;

    static int DEFAULT_IO_THREADS = Math.min(Runtime.getRuntime().availableProcessors() + 1, 32);


    public static void main(String[] args) {

        DoubleServer doubleServer = new DoubleServer();
        doubleServer.testDouble();

    }




    public void testDouble(){
        ServerBootstrap  bootstrap = new ServerBootstrap();

        EventLoopGroup bossGroup = new NioEventLoopGroup(1, new DefaultThreadFactory("NettyServerBoss", true));
        EventLoopGroup workerGroup = new NioEventLoopGroup(2,
                new DefaultThreadFactory("NettyServerWorker", true));

        final DoubleServerHandler nettyServerHandler = new DoubleServerHandler();

//        channels = nettyServerHandler.getChannels();

        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.TCP_NODELAY, Boolean.TRUE)
//                .childOption(ChannelOption.SO_REUSEADDR, Boolean.TRUE)
//                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        // FIXME: should we use getTimeout()?
                        int idleTimeout = 1000;
                        NettyCodecAdapter adapter = new NettyCodecAdapter();

//                        if (getUrl().getParameter(SSL_ENABLED_KEY, false)) {
//                            ch.pipeline().addLast("negotiation",
//                                    SslHandlerInitializer.sslServerHandler(getUrl(), nettyServerHandler));
//                        }

                        ch.pipeline()
//                                .addLast("decoder", adapter.getDecoder())
//                                .addLast("encoder", adapter.getEncoder())
//                                .addLast("server-idle-handler", new IdleStateHandler(0, 0, idleTimeout, MILLISECONDS))
                                .addLast("handler", nettyServerHandler);
                    }
                });
        // bind
        ChannelFuture channelFuture = bootstrap.bind(PORT);
//        channelFuture.syncUninterruptibly();
//        channel = channelFuture.channel();

        try {
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
