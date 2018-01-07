package serial;

import utils.LogUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class Test1 {
    private static final String TAG ="Test1" ;

    public static void main(String[] args) throws IOException {
        int id=257;
        int age=20;
        ByteArrayOutputStream  arrayOutputStream=new ByteArrayOutputStream();
        arrayOutputStream.write(int2Bytes(id));
        arrayOutputStream.write(int2Bytes(age));
        byte[] bytes = arrayOutputStream.toByteArray();
        LogUtils.d(TAG, Arrays.toString(bytes));
        ByteArrayInputStream arrayInputStream=new ByteArrayInputStream(bytes);
        byte[] byteid=new byte[4];
        arrayInputStream.read(byteid);
        byte[] byteage=new byte[4];
        arrayInputStream.read(byteage);
        System.out.println(bytes2Int(byteid));
        System.out.println(bytes2Int(byteage));

    }
    private static byte[] int2Bytes(int in){
        byte[]  bytes=new byte[4];
        bytes[3]=(byte)(in>>0*8);
        bytes[2]=(byte)(in>>1*8);
        bytes[1]=(byte)(in>>2*8);
        bytes[0]=(byte)(in>>3*8);
        return bytes;
    }
    private static int bytes2Int(byte[] bytes){
        return bytes[0]<<3*8|
                bytes[1]<<2*8|
                bytes[2]<<1*8|
                bytes[3];
    }
}
