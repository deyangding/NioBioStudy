package io.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class GroupChatClient {

    private String HOST = "127.0.0.1";
    private int PORT = 6666;

    private Selector selector;
    private SocketChannel socketChannel;

    private String username;

    public GroupChatClient() throws IOException {
        selector = Selector.open();
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST,PORT));
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        username = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println(username +"is ok...");
    }

    public void send (){
        ByteBuffer byteBuffer = ByteBuffer.wrap("我发的".getBytes());
        try {
            socketChannel.write(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read(){
        Iterator<SelectionKey> iterator = selector.keys().iterator();
        while (iterator.hasNext()) {
            SelectionKey selectionKey = iterator.next();

            if (selectionKey.isReadable()) {
                SocketChannel channel = (SocketChannel) selectionKey.channel();
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                try {
                    channel.read(byteBuffer);

                } catch (IOException e) {
                    e.printStackTrace();
                }
               String a =  new String(byteBuffer.array());
                System.out.println(a);


            }
        }

    }

    public static void main(String[] args) {

    }
}
