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

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.example.objectecho.ObjectEchoClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Handler implementation for the echo client.  It initiates the ping-pong
 * traffic between the echo client and server by sending the first message to
 * the server.
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

//    private final ByteBuf firstMessage;
//
//    /**
//     * Creates a client-side handler.
//     */
//    public EchoClientHandler() {
//        firstMessage = Unpooled.buffer(EchoClient.SIZE);
//        for (int i = 0; i < firstMessage.capacity(); i ++) {
//            firstMessage.writeByte((byte) i);
//        }
//
//    }
//
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) {
//        System.out.println(firstMessage);
//        ctx.writeAndFlush(firstMessage);
//    }

    //?????
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object result) throws Exception {
        System.out.println(result);

//        byte[] result1 = new byte[result.readableBytes()];
//        result.readBytes(result1);
//
//        System.out.println("Server said:" + new String(result1));
//        不要释放
//        result.release();
//
        String response = "EchoClientHandler hello sever!";

        // 在当前场景下，发送的数据必须转换成ByteBuf数组
//        ByteBuf encoded = ctx.alloc().buffer(4 * response.length());

//        ctx.read(encoded);


        super.channelRead(ctx,result);

    }


}
