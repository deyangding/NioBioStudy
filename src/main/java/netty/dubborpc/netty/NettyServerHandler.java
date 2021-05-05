package netty.dubborpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.dubborpc.provider.HelloServiceImpl;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private static final String protoctl = "HelloService#hello#";

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("msg=" + msg);
        //客户端再调用api是我们需要定义一个协议
        //每次发消息都需要已一个字符串开头 ”Helloserveice#hello#“
        if (msg.toString().startsWith(protoctl)) {
            String result = new HelloServiceImpl().hello(msg.toString().substring(msg.toString().lastIndexOf("#")));
            ctx.writeAndFlush(result);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
