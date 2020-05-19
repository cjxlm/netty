package io.netty.example.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;

import java.util.concurrent.CountDownLatch;

/**
 * EchoClientHandler2
 *
 * @author caijinxun
 * @date 2020/4/2
 */
public class EchoClientHandler2 extends SimpleChannelInboundHandler<ByteBuf> {


    private Channel channel;
    ChannelHandlerContext ctx;

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        this.channel = ctx.channel();
        this.ctx= ctx;
    }


    /**
     *此方法会在连接到服务器后被调用
     * */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.write(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
        ctx.flush();
    }

    /**
     *此方法会在接收到服务器数据后调用
     * */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf result) {
//        System.out.println("Client received: " + ByteBufUtil.hexDump(result.readBytes(result.readableBytes())));
//        ctx.writeAndFlush("hello netty");


        byte[] result1 = new byte[result.readableBytes()];
        result.readBytes(result1);

        System.out.println("Server said:" + new String(result1));
//        不要释放
//        result.release();
//
        String response = "hello sever!";
        // 在当前场景下，发送的数据必须转换成ByteBuf数组
        ByteBuf encoded = ctx.alloc().buffer(4 * response.length());
        encoded.writeBytes(response.getBytes());
        ctx.write(encoded);
        ctx.flush();

        System.out.println("isWritable:  "+result.isWritable());

            //有问题 用同一个bytebuf
//        System.out.println((result).readableBytes());
//        System.out.println(( result).capacity());
//        System.out.println(( result).maxCapacity());
//
//         result.writeBytes(response.getBytes());
//
//        ctx.writeAndFlush(result);

    }


    public void send(String msg){
        ByteBuf encoded = ctx.alloc().buffer(4 * msg.length());
        encoded.writeBytes(msg.getBytes());

//        ctx.write(encoded);
//        ctx.flush();

        CountDownLatch countDownLatch = new CountDownLatch(1);
        channel.writeAndFlush(encoded).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                System.out.println("operationComplete");
                countDownLatch.countDown();
            }
        });

        try {
            countDownLatch.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }

    /**
     *捕捉到异常
     * */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
