package io.netty.example.dubbo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DoubleClientHandler
 *
 * @author caijinxun
 * @date 2020/4/3
 */
public class DoubleClientHandler extends ChannelDuplexHandler {
    private static final Logger logger = LoggerFactory.getLogger(DoubleClientHandler.class);



//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        NettyChannel channel = NettyChannel.getOrAddChannel(ctx.channel(), url, handler);
//        handler.connected(channel);
//        if (logger.isInfoEnabled()) {
//            logger.info("The connection of " + channel.getLocalAddress() + " -> " + channel.getRemoteAddress() + " is established.");
//        }
//    }

//    @Override
//    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        NettyChannel channel = NettyChannel.getOrAddChannel(ctx.channel(), url, handler);
//        try {
//            handler.disconnected(channel);
//        } finally {
//            NettyChannel.removeChannel(ctx.channel());
//        }
//
//        if (logger.isInfoEnabled()) {
//            logger.info("The connection of " + channel.getLocalAddress() + " -> " + channel.getRemoteAddress() + " is disconnected.");
//        }
//    }

    /**
     *此方法会在连接到服务器后被调用
     * */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.write(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        NettyChannel channel = NettyChannel.getOrAddChannel(ctx.channel(), url, handler);
//        handler.received(channel, msg);

        byte[] result1 = new byte[((ByteBuf)msg).readableBytes()];
        ((ByteBuf)msg).readBytes(result1);

        System.out.println("Server said:" + new String(result1));

        ((ByteBuf)msg).release();

        String response = "hello sever!";
        // 在当前场景下，发送的数据必须转换成ByteBuf数组
        ByteBuf encoded = ctx.alloc().buffer(4 * response.length());
        encoded.writeBytes(response.getBytes());
        ctx.write(encoded);
        ctx.flush();
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {


        System.out.println("server"+msg);
        super.write(ctx, msg, promise);

//        final NettyChannel channel = NettyChannel.getOrAddChannel(ctx.channel(), url, handler);
//        final boolean isRequest = msg instanceof Request;
//
//        // We add listeners to make sure our out bound event is correct.
//        // If our out bound event has an error (in most cases the encoder fails),
//        // we need to have the request return directly instead of blocking the invoke process.
//        promise.addListener(future -> {
//            if (future.isSuccess()) {
//                // if our future is success, mark the future to sent.
//                handler.sent(channel, msg);
//                return;
//            }
//
//            Throwable t = future.cause();
//            if (t != null && isRequest) {
//                Request request = (Request) msg;
//                Response response = buildErrorResponse(request, t);
//                handler.received(channel, response);
//            }
//        });
    }

//    @Override
//    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // send heartbeat when read idle.
//        if (evt instanceof IdleStateEvent) {
//            try {
//                NettyChannel channel = NettyChannel.getOrAddChannel(ctx.channel(), url, handler);
//                if (logger.isDebugEnabled()) {
//                    logger.debug("IdleStateEvent triggered, send heartbeat to channel " + channel);
//                }
//                Request req = new Request();
//                req.setVersion(Version.getProtocolVersion());
//                req.setTwoWay(true);
//                req.setEvent(HEARTBEAT_EVENT);
//                channel.send(req);
//            } finally {
//                NettyChannel.removeChannelIfDisconnected(ctx.channel());
//            }
//        } else {
//            super.userEventTriggered(ctx, evt);
//        }
//    }

//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
//            throws Exception {
//        NettyChannel channel = NettyChannel.getOrAddChannel(ctx.channel(), url, handler);
//        try {
//            handler.caught(channel, cause);
//        } finally {
//            NettyChannel.removeChannelIfDisconnected(ctx.channel());
//        }
//    }

//    public void handshakeCompleted(SslHandlerInitializer.HandshakeCompletionEvent evt) {
//        // TODO
//    }

    /**
     * build a bad request's response
     *
     * @param request the request
     * @param t       the throwable. In most cases, serialization fails.
     * @return the response
     */
//    private static Response buildErrorResponse(Request request, Throwable t) {
//        Response response = new Response(request.getId(), request.getVersion());
//        response.setStatus(Response.BAD_REQUEST);
//        response.setErrorMessage(StringUtils.toString(t));
//        return response;
//    }
}
