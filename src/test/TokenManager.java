package test;

public class TokenManager {
    public void getNewToken(String tag,TokenCallBack tokenCallBack){
        ExecutorUtils.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2);
                    callBack(new CallBack() {
                        @Override
                        public void call() {
                            Identity.getInstance().setNewToken("token"+tag);
                            tokenCallBack.receiveNewToken("token"+tag);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private  static void callBack(CallBack callBack){
        ExecutorUtils.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3);
                    callBack.call();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    interface  CallBack{
        void call();
    }
}
