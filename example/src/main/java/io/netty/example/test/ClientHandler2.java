package io.netty.example.test;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * ClientHandler1
 *
 * @author caijinxun
 * @date 2020/4/4
 */
public class ClientHandler2 extends ChannelOutboundHandlerAdapter {



    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ClientHandler2 read");
        super.read(ctx);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("ClientHandler2 write  "+msg);

//        super.write(ctx, msg, promise);
    }
}
