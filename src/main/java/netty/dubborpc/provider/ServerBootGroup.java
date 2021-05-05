package netty.dubborpc.provider;

import netty.dubborpc.netty.NettyServer;

public class ServerBootGroup {

    public static void main(String[] args) throws InterruptedException {

        NettyServer.startServer("127.0.0.1",7000);
    }

}
