package io.netty.example.echo;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * EchoServerOutHandler
 *
 * @author caijinxun
 * @date 2020/3/25
 */
@ChannelHandler.Sharable
public class EchoServerOutHandler extends ChannelOutboundHandlerAdapter {

//    @Override
//    public void read(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("read"+ctx.read());
//        super.read(ctx);
////        System.out.println(ctx.read());
//    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {

        System.out.println("EchoServerOutHandler write "+msg );
        super.write(ctx, msg, promise);
    }

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        System.out.println("EchoServerOutHandler read " );
        super.read(ctx);
    }
}
