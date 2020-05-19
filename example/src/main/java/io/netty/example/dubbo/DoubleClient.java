package io.netty.example.dubbo;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.proxy.Socks5ProxyHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultThreadFactory;

import javax.net.ssl.SSLException;
import java.net.InetSocketAddress;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * DoubleClient
 *
 * @author caijinxun
 * @date 2020/4/3
 */
public class DoubleClient {

    static final boolean SSL = System.getProperty("ssl") != null;
    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));
    static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));

//    static int DEFAULT_IO_THREADS = Math.min(Runtime.getRuntime().availableProcessors() + 1, 32);

//    private static final NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup(DEFAULT_IO_THREADS, new DefaultThreadFactory("NettyClientWorker", true));

    public static void main(String[] args) throws SSLException {

        DoubleClient doubleClient = new DoubleClient();
        doubleClient.test();


    }


    public void test() throws SSLException {
        final DoubleClientHandler nettyClientHandler = new DoubleClientHandler();
        final SslContext sslCtx;
        if (SSL) {
            sslCtx = SslContextBuilder.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        } else {
            sslCtx = null;
        }
        EventLoopGroup nioEventLoopGroup = new NioEventLoopGroup(1, new DefaultThreadFactory("NettyServerBoss", true));


        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(nioEventLoopGroup)
//                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
//                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                //.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, getTimeout())
                .channel(NioSocketChannel.class);

//        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, Math.max(3000, 2000));
        bootstrap.handler(new ChannelInitializer() {

            @Override
            protected void initChannel(Channel ch) throws Exception {
//                int heartbeatInterval = UrlUtils.getHeartbeat(getUrl());

//                if (getUrl().getParameter(SSL_ENABLED_KEY, false)) {
//                    ch.pipeline().addLast("negotiation", sslCtx);
//                }

//                ChannelPipeline p = ch.pipeline();
//                if (sslCtx != null) {
//                    p.addLast(sslCtx.newHandler(ch.alloc(), HOST, PORT));
//                }



                NettyCodecAdapter adapter = new NettyCodecAdapter();
                ch.pipeline()//.addLast("logging",new LoggingHandler(LogLevel.INFO))//for debug
//                        .addLast("decoder", adapter.getDecoder())
//                        .addLast("encoder", adapter.getEncoder())
//                        .addLast("client-idle-handler", new IdleStateHandler(10000, 0, 0, MILLISECONDS))
                        .addLast("handler", nettyClientHandler);

//                String socksProxyHost = ConfigUtils.getProperty(SOCKS_PROXY_HOST);
//                if(socksProxyHost != null) {
//                    int socksProxyPort = Integer.parseInt(ConfigUtils.getProperty(SOCKS_PROXY_PORT, DEFAULT_SOCKS_PROXY_PORT));
//                    Socks5ProxyHandler socks5ProxyHandler = new Socks5ProxyHandler(new InetSocketAddress(socksProxyHost, PORT));
//                    ch.pipeline().addFirst(socks5ProxyHandler);
//                }
            }
        });


        // Start the client.
        try {
            ChannelFuture  f = bootstrap.connect(HOST, PORT).sync();


            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
