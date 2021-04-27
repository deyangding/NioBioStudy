package io.nio;

import java.io.FileNotFoundException;
import java.nio.IntBuffer;

public class BasicBuffer {


    public static void main(String[] args) throws FileNotFoundException {
        IntBuffer intBuffer = IntBuffer.allocate(5);
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }

        intBuffer.flip();

//        for (int i = 0; i < intBuffer.capacity(); i++) {
//            System.out.println(intBuffer.get());
//        }

        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
