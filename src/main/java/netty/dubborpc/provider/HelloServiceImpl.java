package netty.dubborpc.provider;

import netty.dubborpc.publicinterface.HelloService;

public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String msg) {
        System.out.println("收到客户端：" + msg);
        if (msg != null) {
            return "我收到客户端消息=[：" + msg;
        }
        return "我收到客户端消息";
    }
}
