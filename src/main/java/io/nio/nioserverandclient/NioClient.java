package io.nio.nioserverandclient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NioClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1",7000);

        if (!socketChannel.connect(inetSocketAddress)){
            while (!socketChannel.finishConnect()) {
                System.out.println("我还在连接----->客户端干其他事情");
            }
        }

        System.out.println("客户端干其他事情....");

        String str = "我是客户端";

        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
        socketChannel.write(byteBuffer);
        System.out.println("byteBuffer over....");
        System.in.read();
    }
}
