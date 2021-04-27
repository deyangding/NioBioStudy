package io.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileChannel03 {

    public static void main(String[] args) throws IOException {
        //创建输入流
        FileInputStream fileInputStream = new FileInputStream("a.txt");
        FileChannel fileChannel01 = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("b.txt");
        FileChannel fileChannel02 = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while (true) {

//            public final Buffer clear() {
//                position = 0;
//                limit = capacity;
//                mark = -1;
//                return this;
//            }
//
            byteBuffer.clear();//清空buff

            int read = fileChannel01.read(byteBuffer);
            if (read == -1) {
                break;
            }

            byteBuffer.flip();
            fileChannel02.write(byteBuffer); //写入b.txt
        }

        fileInputStream.close();
        fileOutputStream.close();

    }
}
