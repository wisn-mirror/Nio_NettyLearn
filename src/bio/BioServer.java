package bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BioServer {
    static List<Socket> sockets = new ArrayList<>();

    public static void main(String[] arg) {
        ExecutorService pool = Executors.newCachedThreadPool();
        try {
            ServerSocket serverSocket = new ServerSocket(9000);
            while (true) {
                Socket accept = serverSocket.accept();
                sockets.add(accept);
                pool.execute(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("客户端：" + accept + "链接上来了");
                        handleMessage(accept);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleMessage(Socket socket) {
        try {
            InputStream inputStream = socket.getInputStream();
            while (true) {
                byte[] receiveBuffer = new byte[1024];
                int index = -1;
                String receiverMsg = "";
                if ((index = inputStream.read(receiveBuffer)) != -1) {
                    receiverMsg = new String(receiveBuffer, 0, index);
                    System.out.println(socket+":" + receiverMsg);
                    sendMsgforAll(socket, receiverMsg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendMsgforAll(Socket socket, String msg) {
        for (Socket socketother : sockets) {
            try {
                OutputStream outputStream = socketother.getOutputStream();
                outputStream.write((socket + ":" + msg + "\n").getBytes());
                outputStream.flush();
            } catch (IOException e) {
//                e.printStackTrace();
               if(socketother.isClosed()||!socketother.isConnected()||!socketother.isOutputShutdown()){
                   System.out.println(socketother+" 断开" );
                   sockets.remove(socketother);
               }
            }
        }
    }
}
