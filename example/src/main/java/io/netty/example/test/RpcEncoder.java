package io.netty.example.test;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * RPC Encoder
 * @author huangyong
 */
public class RpcEncoder extends MessageToByteEncoder<String> {

    private Class<?> genericClass;

    public RpcEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    public void encode(ChannelHandlerContext ctx, String in, ByteBuf out) throws Exception {
        System.out.println("encode");
//        if (genericClass.isInstance(in)) {
//            byte[] data = SerializationUtil.serialize(in);

            byte[] data = in.getBytes();
            //byte[] data = JsonUtil.serialize(in); // Not use this, have some bugs
            out.writeInt(data.length);
            out.writeBytes(data);
//        }
    }
}
