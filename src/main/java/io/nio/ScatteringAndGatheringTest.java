package io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

//Scattering 将数据写入到buffer时，可以采用buffer数据依次写入
//Gathering 从bff读数据可以采用bff数组依次读取
public class ScatteringAndGatheringTest {

    public static void main(String[] args) throws IOException {
        //ServerSocketChannel

        ServerSocketChannel s = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
        s.socket().bind(inetSocketAddress);

        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        SocketChannel socketChannel = s.accept();
        //假定冲客户端接受8个字节
        int max = 8;
        while (true) {
            int read = 0;
            while (read < max) {
                long l = socketChannel.read(byteBuffers);
                read += l;

                System.out.println("read=" + read);

                //使用流打印
               Arrays.asList(byteBuffers).stream().forEach(t->{
                   System.out.println("position="+ t.position() +"limit="+t.limit());
               });
            }

            System.out.println(" t.flip();");
            Arrays.asList(byteBuffers).stream().forEach(t->{
                t.flip();
            });


            //将数据读出到客户端
            int byteWrite = 0;

            while (byteWrite < max) {
              long l =  socketChannel.write(byteBuffers);
              byteWrite +=l;
            }

            Arrays.asList(byteBuffers).stream().forEach(t->{
                t.clear();
            });

            System.out.println("byteWrite"+byteWrite+"read"+read);
        }
    }
}
