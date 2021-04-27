package io.nio.nioserverandclient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioServerClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        //创建一个服务端SocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //打开一个Selector
        Selector selector = Selector.open();

        //设置非阻塞
        serverSocketChannel.configureBlocking(false);
        //绑定端口
        serverSocketChannel.socket().bind(new InetSocketAddress(7000));

        //SocketChannel注册到Selector
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            if (selector.select(1000) == 0) {
                System.out.println("等待1s 没有连接。。。");
                continue;
            }

            //selectedKeys 不是keys
            Set<SelectionKey> keys = selector.selectedKeys();

            Iterator<SelectionKey> keyIterator = keys.iterator();

            while (keyIterator.hasNext()) {

                SelectionKey selectionKey = keyIterator.next();

                //是否有连接发生
                if (selectionKey.isAcceptable()) {
                    System.out.println("客户端 连接。。。");
                    //一般accept是阻塞的 但是有了selectionKey.isAcceptable判断已经有连接发生
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    //设置非阻塞 不然会IllegalBlockingModeException
                    socketChannel.configureBlocking(false);
                    //socketChannel 注册到selector上
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }

                //OP_READ读事件
                if (selectionKey.isReadable()) {
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                    //写入buff
                    channel.read(byteBuffer);
                    System.out.println("客户端发了：" + new String(byteBuffer.array()));
                }

                //多线程防止重复消费
                keyIterator.remove();
                Thread.sleep(2000);
            }
        }
    }
}
