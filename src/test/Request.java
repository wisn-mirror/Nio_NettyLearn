package test;

public class Request {
    private String name;
    public Request(String name) {
        this.name=name;
    }

    public void excute(){
        Identity.getInstance().checkToken(new TokenCallBack() {
            @Override
            public void receiveNewToken(String token) {
                System.out.println(System.nanoTime() +":  name:"+name+" token:"+token);
            }
        },name);
    }
}
