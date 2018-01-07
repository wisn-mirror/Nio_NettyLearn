package protobuf.test;

import com.google.protobuf.InvalidProtocolBufferException;
import protobuf.bean.PlayerModule;
import utils.LogUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class PB2bytes {
    private static final String TAG ="PB2bytes" ;

    public static void main(String[] args){

      byte[]  bytes=  tobyte();
        toPlayer(bytes);
    }
    private static void toPlayer(byte[] bytes){
        try {
            PlayerModule.Player player = PlayerModule.Player.parseFrom(bytes);
            LogUtils.d(TAG,"getName："+player.getName());
            LogUtils.d(TAG,"getPlayerId："+player.getPlayerId());
            List<String> skillsList = player.getSkillsList();
            Iterator<String> iterator = skillsList.iterator();
            while (iterator.hasNext()){
                String next = iterator.next();
                LogUtils.d(TAG," skill:"+next);
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }
    private static  byte[] tobyte() {
        PlayerModule.Player.Builder builder = PlayerModule.Player.newBuilder();
        builder.setName("wisn");
        builder.setPlayerId(4321432);
        builder.addSkills("aaa1");
        builder.addSkills("aaa2");
        builder.addSkills("aaa3");
//        builder.setSkills(0,"wisnskinll1");
//        builder.setSkills(1,"wisnskinll2");
//        builder.setSkills(2,"wisnskinll3");
        PlayerModule.Player player = builder.build();
        byte[] bytes = player.toByteArray();
        LogUtils.d(TAG, Arrays.toString(bytes));
        return bytes;
    }
}
