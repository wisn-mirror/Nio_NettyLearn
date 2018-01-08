package serial;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.nio.ByteOrder;
import java.util.Arrays;

public class ByteBufTest {
    public static void main(String[] args){
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.heapBuffer();
        byteBuf.order(ByteOrder.BIG_ENDIAN);
        byteBuf.writeInt(222);
        byteBuf.writeInt(33);
        byteBuf.writeDouble(33.2);
        byte[] array = byteBuf.ensureWritable(byteBuf.writerIndex());
        System.out.println(Arrays.toString(array));
    }
}