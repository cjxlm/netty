package io.netty.example.test;

import io.netty.channel.*;

import java.util.concurrent.CountDownLatch;

/**
 * ClientHandler1
 *
 * @author caijinxun
 * @date 2020/4/4
 */
public class ClientHandler1 extends SimpleChannelInboundHandler<String> {

    private volatile Channel channel;



    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        this.channel = ctx.channel();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("ClientHandler1 "+msg);

        //调用write方法不会往后面的channelHandler中执行,找自己下一个出栈的channelHandler的write方法
//        ctx.writeAndFlush(msg);

//        ctx.write(msg);



    }

    public void send(String msg){
        System.out.println(msg);

//        channel.writeAndFlush(msg);

        final CountDownLatch latch = new CountDownLatch(1);
        channel.writeAndFlush(msg).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
//            logger.error(e.getMessage());
        }
    }
}
