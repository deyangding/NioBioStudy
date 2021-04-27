package io.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioByteBufferPutGet {

    public static void main(String[] args) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(65);
        byteBuffer.putInt(122);
        byteBuffer.putLong(1);
        byteBuffer.putChar('d');

        byteBuffer.flip();

        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.getLong());
        //java.nio.BufferUnderflowException
//        System.out.println(byteBuffer.getLong());

    }
}
