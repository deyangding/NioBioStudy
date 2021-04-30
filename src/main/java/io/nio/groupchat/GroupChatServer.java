package io.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class GroupChatServer {

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private static final int PORT = 6666;

    public GroupChatServer() {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void listen() {

        try {
            while (true) {
                int count = selector.select(2000);
                if (count > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();

                        if (key.isAcceptable()) {
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);

                            System.out.println(socketChannel.getRemoteAddress() + "商详");
                        }

                        if (key.isReadable()) {
                            //处理读事件
                            readData(key);
                        }

                        iterator.remove();
                    }
                } else {
                    System.out.println("等待。。。。");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readData(SelectionKey key) {
        //定义一个socketChannel
        SocketChannel channel = null;
        try {
            channel = (SocketChannel) key.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            int count = channel.read(byteBuffer);

            if (count > 0) {
                //读取数据
                String msg = new String(byteBuffer.array());
                System.out.println("from 客户" + msg);

                //向其他客户端转发(排除自己)
                sendInfoToClients(msg, channel);
            }

        } catch (Exception e) {
            try {
                System.out.println("客户" + channel.getRemoteAddress() + "离线了");
                key.cancel();
                channel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void sendInfoToClients(String msg, SocketChannel self) {
        System.out.println("服务器发消息。。。。");
        for (SelectionKey key : selector.keys()) {
            //取出通道
            //SocketChannel
            Channel targetChannel = (SocketChannel) key.channel();
            if (targetChannel instanceof SocketChannel && targetChannel != self) {
                try {
                    ((SocketChannel) targetChannel).write(ByteBuffer.wrap(msg.getBytes()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static void main(String[] args) {
        new GroupChatServer().listen();
    }
}
