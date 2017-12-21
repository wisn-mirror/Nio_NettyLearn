package test;

import java.util.Random;

public class Identity {
    private static Identity identity=new Identity();
    public String newToken;
    private Identity(){}
    public  synchronized void checkToken(TokenCallBack tokenCallBack,String tag){
        System.out.println("check:"+tag+"  "+newToken);
       validateStoredToken(tokenCallBack, tag);
    }
    private  void validateStoredToken(TokenCallBack tokenCallBack,String tag){
        int ran=new Random().nextInt(5);
        if(ran==4){
            tokenCallBack.receiveNewToken(newToken+"random:"+ran);
            return ;
        }
        if(newToken==null||!newToken.equals(tag)){
            TokenManager tokenManager=new TokenManager();
            tokenManager.getNewToken(tag,tokenCallBack);
        }else{
            tokenCallBack.receiveNewToken(newToken);
        }
    }
    public void setNewToken(String newToken){
        this.newToken=newToken;
    }

    public static Identity getInstance(){
        return identity;
    }

}
