package bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class BioClient {
    public static void main(String[] args) {
        try {
            for (int i = 0; i < 30; i++) {
                Socket socket = new Socket("localhost", 9000);
                handleMsg(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleMsg(Socket socket) {
        try {
            InputStream inputStream = socket.getInputStream();
            while (true) {
                byte[] bytes = new byte[1024];
                int index = -1;
                if ((index = inputStream.read(bytes)) != -1) {
                    System.out.println("收到：" + new String(bytes, 0, index));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
