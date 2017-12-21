package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) {
        String deviceId = " : IMEI : 43243218089743214 : ";
        deviceId=deviceId.toLowerCase();
        deviceId = deviceId.replace("imei", "");
        deviceId = "".replace(":", "").trim();
        System.out.println("==" + deviceId + "==");
//        for (int i=0;i<100;i++){
//            Request request=new Request("request"+i);
//            request.excute();
//        }
    }
}
