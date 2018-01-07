package protobuf.test;

import com.google.protobuf.InvalidProtocolBufferException;
import protobuf.bean.Player;
import protobuf.bean.PlayerModule;
import utils.LogUtils;

import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Java2bytes {
    private static final String TAG ="Java2bytes" ;

    public static void main(String[] args){

      byte[]  bytes=  tobyte();
        toPlayer(bytes);
    }
    private static void toPlayer(byte[] bytes){
        try {
            ObjectInputStream  objectInputStream=new ObjectInputStream(new ByteArrayInputStream(bytes));
            Player aa= (Player) objectInputStream.readObject();
            LogUtils.d(TAG,"getName："+aa.getName());
            LogUtils.d(TAG,"getPlayerId："+aa.getId());
            List<String> skillsList = aa.getSkill();
            Iterator<String> iterator = skillsList.iterator();
            while (iterator.hasNext()){
                String next = iterator.next();
                LogUtils.d(TAG," skill:"+next);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static  byte[] tobyte()  {
        Player  player=new Player();
        player.setId(4321432);
        player.setName("wisn");
        player.getSkill().add("aaa1");
        player.getSkill().add("aaa2");
        player.getSkill().add("aaa3");
        ByteArrayOutputStream  byteArrayOutputStream= null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(player);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = byteArrayOutputStream.toByteArray();
        LogUtils.d(TAG, Arrays.toString(bytes));
        return bytes;
    }
}
