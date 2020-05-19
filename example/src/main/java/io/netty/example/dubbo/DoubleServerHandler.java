package io.netty.example.dubbo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DoubleServerHandler
 *
 * @author caijinxun
 * @date 2020/4/3
 */

@ChannelHandler.Sharable
public class DoubleServerHandler extends ChannelDuplexHandler {
    private static final Logger logger = LoggerFactory.getLogger(DoubleServerHandler.class);
    /**
     * the cache for alive worker channel.
     * <ip:port, dubbo channel>
     */
    private final Map<String, Channel> channels = new ConcurrentHashMap<String, Channel>();

//    private final URL url;

//    private final ChannelHandler handler;

    public DoubleServerHandler() {
//        ChannelHandler handler
//        this.handler = handler;
    }

    public Map<String, Channel> getChannels() {
        return channels;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        super.channelActive(ctx);

//        NettyChannel channel = NettyChannel.getOrAddChannel(ctx.channel(), url, handler);
//        if (channel != null) {
//            channels.put(NetUtils.toAddressString((InetSocketAddress) ctx.channel().remoteAddress()), channel);
//        }
//        handler.connected(channel);
//
//        if (logger.isInfoEnabled()) {
//            logger.info("The connection of " + channel.getRemoteAddress() + " -> " + channel.getLocalAddress() + " is established.");
//        }



    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);


//        NettyChannel channel = NettyChannel.getOrAddChannel(ctx.channel(), url, handler);
//        try {
//            channels.remove(NetUtils.toAddressString((InetSocketAddress) ctx.channel().remoteAddress()));
//            handler.disconnected(channel);
//        } finally {
//            NettyChannel.removeChannel(ctx.channel());
//        }
//
//        if (logger.isInfoEnabled()) {
//            logger.info("The connection of " + channel.getRemoteAddress() + " -> " + channel.getLocalAddress() + " is disconnected.");
//        }




    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        NettyChannel channel = NettyChannel.getOrAddChannel(ctx.channel(), url, handler);
//        handler.received(channel, msg);

        logger.info("client say: {} ",msg);

        String response = "hello client!";
        ((ByteBuf) msg).writeBytes(response.getBytes());

        ctx.writeAndFlush(msg);



    }


    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {

        System.out.println("client:"+msg);

        super.write(ctx, msg, promise);
//        NettyChannel channel = NettyChannel.getOrAddChannel(ctx.channel(), url, handler);
//        handler.sent(channel, msg);




    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // server will close channel when server don't receive any heartbeat from client util timeout.
//        if (evt instanceof IdleStateEvent) {
//            NettyChannel channel = NettyChannel.getOrAddChannel(ctx.channel(), url, handler);
//            try {
//                logger.info("IdleStateEvent triggered, close channel " + channel);
//                channel.close();
//            } finally {
//                NettyChannel.removeChannelIfDisconnected(ctx.channel());
//            }
//        }




        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {

        super.exceptionCaught(ctx,cause);

//        NettyChannel channel = NettyChannel.getOrAddChannel(ctx.channel(), url, handler);
//        try {
//            handler.caught(channel, cause);
//        } finally {
//            NettyChannel.removeChannelIfDisconnected(ctx.channel());
//        }




    }

//    public void handshakeCompleted(HandshakeCompletionEvent evt) {
//        // TODO
//    }




}
