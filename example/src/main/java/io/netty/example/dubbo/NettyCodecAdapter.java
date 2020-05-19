package io.netty.example.dubbo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;
import java.util.List;

/**
 * NettyCodecAdapter
 *
 * @author caijinxun
 * @date 2020/4/3
 */
public class NettyCodecAdapter {
    private final ChannelHandler encoder = new InternalEncoder();

    private final ChannelHandler decoder = new InternalDecoder();

//    private final Codec2 codec;
//
//    private final URL url;
//
//    private final org.apache.dubbo.remoting.ChannelHandler handler;

    public NettyCodecAdapter() {
//        this.codec = codec;
//        this.url = url;
//        this.handler = handler;
    }

    public ChannelHandler getEncoder() {
        return encoder;
    }

    public ChannelHandler getDecoder() {
        return decoder;
    }

    private class InternalEncoder extends MessageToByteEncoder {

        @Override
        protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
            //dubbo形式
            //            org.apache.dubbo.remoting.buffer.ChannelBuffer buffer = new NettyBackedChannelBuffer(out);
//            Channel ch = ctx.channel();
//            NettyChannel channel = NettyChannel.getOrAddChannel(ch, url, handler);
//            codec.encode(channel, buffer, msg);



            System.out.println("encode");

            ByteBuf result = (ByteBuf) msg;
            byte[] result1 = new byte[result.readableBytes()];
            out.writeBytes(result1);


//            MessagePack形式

//            MessagePack msgpack = new MessagePack();
//            //使用MessagePack对要发送的数据进行序列化
//            byte[] raw = msgpack.write(msg);
//            out.writeBytes(raw);

//            ctx.write(msg);

        }
    }

    private class InternalDecoder extends ByteToMessageDecoder {

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> out) throws Exception {
            System.out.println("decode");
               out.add(String.valueOf(input));


            //从msg中获取需要解码的byte数组
//            final int length = input.readableBytes();
//            byte[] b = new byte[length];
//            input.getBytes(input.readerIndex(), b,0,length);
            //使用MessagePack的read方法将其反序列化成Object对象，并加入到解码列表out中
//            MessagePack msgpack = new MessagePack();
//            out.add(msgpack.read(b));



//            ChannelBuffer message = new NettyBackedChannelBuffer(input);
//
//            NettyChannel channel = NettyChannel.getOrAddChannel(ctx.channel(), url, handler);
//
//            // decode object.
//            do {
//                int saveReaderIndex = message.readerIndex();
//                Object msg = codec.decode(channel, message);
//                if (msg == Codec2.DecodeResult.NEED_MORE_INPUT) {
//                    message.readerIndex(saveReaderIndex);
//                    break;
//                } else {
//                    //is it possible to go here ?
//                    if (saveReaderIndex == message.readerIndex()) {
//                        throw new IOException("Decode without read data.");
//                    }
//                    if (msg != null) {
//                        out.add(msg);
//                    }
//                }
//            } while (message.readable());




        }
    }
}
