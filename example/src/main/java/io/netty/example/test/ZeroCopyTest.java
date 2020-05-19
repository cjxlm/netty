package io.netty.example.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.jupiter.api.Test;

/**
 * ZeroCopyTest
 *
 * CompositeByteBuf，将多个ByteBuf合并为一个逻辑上的ByteBuf，避免了各个ByteBuf之间的拷贝。
 *wrapedBuffer（）方法，将byte[]数组包装成ByteBuf对象。
 * slice（）方法，将一个ByteBuf对象切分成多个ByteBuf对象
 *
 *
 * 零拷贝就是不改变原buf只是逻辑上做拆分、合并、包装。减少了大量的内存复制，由此提升性能。
 *
 * @author caijinxun
 * @date 2020/4/8
 */
public class ZeroCopyTest {

    @Test
    public void wrapTest() {
        byte[] arr = {1, 2, 3, 4, 5};
        ByteBuf byteBuf = Unpooled.wrappedBuffer(arr);
        System.out.println(byteBuf.getByte(4));
        arr[4] = 6;
        System.out.println(byteBuf.getByte(4));
    }

    @Test
    public void sliceTest() {
        ByteBuf buffer1 = Unpooled.wrappedBuffer("hello".getBytes());
        ByteBuf newBuffer = buffer1.slice(1, 2);
        newBuffer.unwrap();
        System.out.println(newBuffer.toString());
    }

    @Test
    public void compositeTest() {
        ByteBuf buffer1 = Unpooled.buffer(3);
        buffer1.writeByte(1);
        ByteBuf buffer2 = Unpooled.buffer(3);
        buffer2.writeByte(4);
        CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer();
        CompositeByteBuf newBuffer = compositeByteBuf.addComponents(true, buffer1, buffer2);
        System.out.println(newBuffer);
    }

}
