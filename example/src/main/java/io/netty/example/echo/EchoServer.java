/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.example.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

/**
 * Echoes back any received data from a client.
 */
public final class EchoServer {

    static final boolean SSL = System.getProperty("ssl") != null;
    static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));


//    这四步代码主要完成了：
//            ① NioEventLoopGroup的构建。在NIO模式中我们会在构建EventLoopGroup的时
// 候就将EventLoop一并的给构建处理。因为在NIO模式中，EventLoop和Channel的关系是一对多的关系，
// 多个Channel可以注册到同一个EventLoop中，因此每个EventLoopGroup中的EventLoop的个数是固定的，
// 而在OIO模式下，我们就无法在构建EventLoopGroup的同时将EventLoop也构建出来了。因为在OIO模式中，
// EventLoop和Channel是一对一的关系，每个Channel都会注册到一个唯一的EventLoop中。因此在OIO模式中，
// EventLoop的个数随着Channel的增加而增加，而EventLoop的构建也放到了将Channel注册到EventLoop时进行。
//    而造成上面实现不同的本质原因还是应用传输协议模式的不同。NIO模式是非阻塞模式，底层使用了Selector来
// 通过更少的线程同时管理大量的连接；而OIO模式是阻塞模式，每一个连接都需要一个线程来处理。
//            ② 通过ServerBootstrap对服务的配置进行设置。其中a) group()；b) channel()
// or channelFactory()；c) childHandler() 配置是不可缺少的。在上面的源码解析中，我们可以总结出，
// 涉及到对child Channel(即，服务端接收客户端的请求连接后生产的child Channel，该child Channel就是真
// 正和客户端连接的Channel)的配置都会设置在ServerBootstrap中，比如childGroup、childHandler；
// 而AbstractBootstrap中则保持了ServerChannel相关的配置，比如bossGroup、handle、channelFactory。






    public static void main(String[] args) throws Exception {
        // Configure SSL.
        final SslContext sslCtx;
        if (SSL) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        } else {
            sslCtx = null;
        }

        // Configure the server.
        // 创建线程池

        // NioEventLoop的实现就是NIO中的selector增强，本质还是selector，
        // 而NioEventLoop是不能创建线程的，这里用到了Excutor来创建线程，本身是个线程池
        // 在线程run方法中会轮训执行selector.select方法和taskqueue里的内容，
        // 每个EventLoop对应一个轮询线程。eventLoop是不会自己执行线程的，
        // 只有当有任务提交时才触发eventloop，如果eventloop想要自己执行任务，
        // 那么就需要使用execute方法来提交一个runnable


//        EventLoopGroup是用于处理ServerChannel和Channel的所有的事件以及I/O操作

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(2);
        final EchoServerHandler serverHandler = new EchoServerHandler();
        final EchoServerOutHandler echoServerOutHandler = new EchoServerOutHandler();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             //NioServerSocketChannel类，用来初始化一个新的Channel去接收到达的connection
             .channel(NioServerSocketChannel.class)

            //option()用来配置NioServerSocketChannel(负责接收到来的connection)，
            // 而childOption()是用来配置被ServerChannel
             // (这里是NioServerSocketChannel) 所接收的Channel
             .option(ChannelOption.SO_BACKLOG, 100)
                    //属于 ServerSocketChannel
             .handler(new LoggingHandler(LogLevel.INFO))
                //SocketChannel 使用  handler

                //handler 会被用来处理新接收的Channel。
                // ChannelInitializer是一个特殊的 handler，
                // 帮助开发者配置Channel，而多数情况下你会配置Channel下
                // 的ChannelPipeline，往 pipeline 添加一些 handler
             .childHandler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
                     ChannelPipeline p = ch.pipeline();
                     if (sslCtx != null) {
                         p.addLast(sslCtx.newHandler(ch.alloc()));
                     }
                     //p.addLast(new LoggingHandler(LogLevel.INFO));

                     p.addLast(echoServerOutHandler);
                     p.addLast("myhandler",serverHandler);




//                     p.addLast(
//                             new ObjectEncoder(),
//                             new ObjectDecoder(ClassResolvers.cacheDisabled(null)),serverHandler);

                 }
             });

            // Start the server.
            ChannelFuture f = b.bind(PORT).sync();

            // 阻塞主线程，知道网络服务被关闭
            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
        } finally {
            // Shut down all event loops to terminate all threads.
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
