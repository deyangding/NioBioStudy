package netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestSeverInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //向管道加入处理器

        //得到管道
        ChannelPipeline pipeline = ch.pipeline();
        //加入一个netty  提供的httpseverCodec code,decoder

        pipeline.addLast("MyHttpserverCodec", new HttpServerCodec());

        pipeline.addLast("TestHttpSeverHander",new TestHttpSeverHander());





    }
}
