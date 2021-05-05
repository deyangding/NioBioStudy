package netty.dubborpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.concurrent.Callable;

public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private ChannelHandlerContext context;
    private String result;  //返回结果
    private String para;    //传入参数


    @Override
    public synchronized Object call() throws Exception {
        //被代理对象调用，发送数据给服务器，wait ->等待唤醒（channelRead）-》返回结果
        context.writeAndFlush(para);
        wait();     //等待   channelRead获取到结果后，唤醒
        return result;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
        context = ctx;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = msg.toString();

        System.out.println(msg.toString());
        //接收到数据后，唤起等待的线程
        notify();
    }

    void setPara(String para) {
        this.para = para;
    }
}
