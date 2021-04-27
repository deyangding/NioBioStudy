package io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BioDemo {

    public static void main(String[] args) throws IOException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("启动。。。。");

        while (true){
            Socket socket = serverSocket.accept();
            System.out.println("来了一个连接");
            executorService.execute(()->{
                handler(socket);
            });
        }

    }

    public static void handler(Socket socket) {
        try {
            byte[] bytes = new byte[1024];
            InputStream inputStream = socket.getInputStream();
            while (true) {
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println(new String (bytes, read));
                } else {
                    break;
                }
            }


        } catch (Exception e){
            e.printStackTrace();
        } finally {
            System.out.println("结束。。。"+ Thread.currentThread().getName());
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
