package io.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileChannel04 {

    public static void main(String[] args) throws IOException {
        FileInputStream inputStream = new FileInputStream("D:\\DDY\\a.jpg");
        FileOutputStream outputStream = new FileOutputStream("D:\\DDY\\b.jpg");
        FileChannel sou = inputStream.getChannel();
        FileChannel des = outputStream.getChannel();

        des.transferFrom(sou,0,sou.size());
        sou.close();
        des.close();
        inputStream.close();
        outputStream.close();
    }
}
