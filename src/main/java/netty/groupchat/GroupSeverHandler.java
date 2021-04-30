package netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class GroupSeverHandler extends SimpleChannelInboundHandler<String> {

    //管理所有channel
    ///全局的事件执行器 单例
    private static ChannelGroup channelGroup =
            new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    //等同于channelGroup 会麻烦
//    private static List<Channel> channelGroup = new ArrayList<>();


    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        // 遍历channelGroup 根据不同的情况回送消息

        channelGroup.forEach(ch -> {
            if (ch != channel) {
                ch.writeAndFlush("[客户]" + channel.remoteAddress() + "说" + msg);
            } else {
                ch.writeAndFlush("[自己]" + channel.remoteAddress() + "说" + msg);
            }
        });

    }

    //表示链接建立，一旦链接 第一个被执行
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //将该客户加入消息分发给其他客户
        //这里不需要自己遍历，底层 会遍历
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + "加入");
        channelGroup.add(channel);
    }

    //将xx客户离开的消息推送给其他客户
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + "离开");

        //channelGroup.remove(channel) 不需要

        System.out.println("channelGroup size = " + channelGroup.size());
    }

    //表示channel 处于活动状态
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "上线。。。。");
    }

    //表示channel 处于非活动状态
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "下线。。。。");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
