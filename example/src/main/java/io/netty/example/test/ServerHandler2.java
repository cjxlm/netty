package io.netty.example.test;

import io.netty.channel.*;

import java.util.ArrayList;
import java.util.List;

/**
 * ServerHandler1
 *
 * @author caijinxun
 * @date 2020/4/4
 */
@ChannelHandler.Sharable
public class ServerHandler2 extends ChannelDuplexHandler {

    List<Channel> channelList = new ArrayList<>();


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        channelList.add(ctx.channel());
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        channelList.remove(ctx.channel());
    }

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ServerHandler2 read");
        super.read(ctx);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("ServerHandler2 write  "+msg);
        super.write(ctx, msg, promise);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("ServerHandler2 channelRead");
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ServerHandler2 channelReadComplete");
        super.channelReadComplete(ctx);
    }


    public List<Channel> getChannelList() {
        return channelList;
    }

    public void setChannelList(List<Channel> channelList) {
        this.channelList = channelList;
    }
}
