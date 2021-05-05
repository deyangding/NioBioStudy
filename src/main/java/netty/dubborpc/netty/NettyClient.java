package netty.dubborpc.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NettyClient {

    private static ExecutorService executors = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


    private static NettyClientHandler client;

    //使用代理模式获取一个代理对象
    public Object getBean(final Class<?> clazz, final String provideName) {

        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{clazz}, ((proxy, method, args) -> {
                    if (client == null) {
                        ininClient();
                    }

                    client.setPara(provideName + args[0]);
                    return executors.submit(client).get();
                }));
    }

    private static void ininClient() throws InterruptedException {
        client = new NettyClientHandler();
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class).
                option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new StringDecoder());
                pipeline.addLast(new StringEncoder());
                pipeline.addLast(client);

            }
        });
        ChannelFuture sync = bootstrap.connect("127.0.0.1", 7000).sync();
        System.out.println("客户端。。。");
//        sync.channel().closeFuture().sync();
//        group.shutdownGracefully();
    }

}
