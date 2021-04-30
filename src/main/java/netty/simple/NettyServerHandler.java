package netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

//extends netty 规定好的莫个handlerAdapter
public class NettyServerHandler extends ChannelInboundHandlerAdapter {


    //ChannelHandlerContext 上下文对象，含管道pipeline,通道channel,地址
    //msg 客户端发的数据
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        //解决阻塞方案
        //1 自定义任务
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000*10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("异常");
                }
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户1",CharsetUtil.UTF_8));
            }
        });


        //30秒才开始执行（eventLoop 是同一个线程）
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000*20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("异常");
                }
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户2",CharsetUtil.UTF_8));
            }
        });


        //2 用户自定义任务到secheduleTaskQueue中
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000*20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("异常");
                }
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户3",CharsetUtil.UTF_8));
            }
        },5, TimeUnit.SECONDS);


        System.out.println("server ctx = " + Thread.currentThread().getName() +ctx);
        //Netty 提供的ByteBuf 性能高于byteBUffer
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发的消息："+ buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址："+ ctx.channel().remoteAddress());
        //channel 和ChannelPipeline 相互包含
        Channel channel = ctx.channel();
        ChannelPipeline pipeline = ctx.pipeline();//本质是一个双向链表
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户",CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
