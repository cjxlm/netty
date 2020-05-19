package io.netty.example.echo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * EchoClientOutChannelHandler
 *
 * @author caijinxun
 * @date 2020/4/1
 */
public class EchoClientOutChannelHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {

        System.out.println("write   :" +msg);
        super.write(ctx, msg, promise);

//        ctx.write(msg,promise);
    }
}
