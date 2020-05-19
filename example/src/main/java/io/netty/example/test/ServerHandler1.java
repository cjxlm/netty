package io.netty.example.test;

import io.netty.channel.*;

/**
 * ServerHandler1
 *
 * @author caijinxun
 * @date 2020/4/4
 */
@ChannelHandler.Sharable
public class ServerHandler1  extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("ServerHandler1 :"+msg);
        ctx.writeAndFlush(msg);
    }
}
