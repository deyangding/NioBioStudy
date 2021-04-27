package io.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileChannel01 {


    public static void main(String[] args) throws IOException {
        String a = "te";
        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\v_deydding\\Desktop\\a\\ddd.txt");

        //创建一个channel
        FileChannel fileChannel = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //string 放入byteBuffer
        byteBuffer.put(a.getBytes());
        //反转
        byteBuffer.flip();
        fileChannel.write(byteBuffer);

        fileOutputStream.close();
    }
}
