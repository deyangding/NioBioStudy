package io.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileChannel02 {

    public static void main(String[] args) throws IOException {
        //创建输入流
        File file = new File("C:\\Users\\v_deydding\\Desktop\\a\\ddd.txt");
        FileInputStream fileInputStream = new FileInputStream(file);

        FileChannel fileChannel = fileInputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
//将通道数据读到buff
        fileChannel.read(byteBuffer);
        System.out.println(new String(byteBuffer.array()));
    }
}
