package io.nio;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.Random;
import java.util.RandomAccess;

public class MappedByteBufferTest {

    public static void main(String[] args) throws IOException {
        //MappedByteBuffer 可以让文件可以在内存（堆外）拷贝，操作系统不需要拷贝一次
        RandomAccessFile randomAccessFile = new RandomAccessFile("a.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();

        //FileChannel读写模式
        //可以修改的起始位置
        //映射到内存的大小(不是索引位置)，a.txt的多少个字节映射到内存,

        MappedByteBuffer mappedByteBuffer = channel.map(MapMode.READ_WRITE, 0, 5);

        mappedByteBuffer.put(0, (byte) 'A');
        mappedByteBuffer.put(3, (byte) 'c');
        mappedByteBuffer.put(4, (byte) 'c');
        mappedByteBuffer.put(2, (byte) 'c');
        mappedByteBuffer.put(1, (byte) 'c');

    }
}
