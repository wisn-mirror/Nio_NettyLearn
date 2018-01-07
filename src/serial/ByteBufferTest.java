package serial;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import utils.LogUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class ByteBufferTest {
    private static final String TAG ="ByteBufferTest" ;

    public static void main(String[] args) throws IOException {
        int id=257;
        int age=20;
        ByteBuffer  byteBuffer=ByteBuffer.allocate(8);
        
        byteBuffer.putInt(id);
        byteBuffer.putInt(age);
        byte[] array = byteBuffer.array();
        LogUtils.d(TAG, Arrays.toString(array));

        ByteBuffer  byteBuffer1=ByteBuffer.wrap(array);
        System.out.println(byteBuffer1.getInt());
        System.out.println(byteBuffer1.getInt());
    }

}
