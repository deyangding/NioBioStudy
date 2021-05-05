package netty.dubborpc.customer;

import netty.dubborpc.netty.NettyClient;
import netty.dubborpc.publicinterface.HelloService;

public class ClientBootstrap {

    public static final String providerName = "HelloService#hello#";

    public static void main(String[] args) {
        NettyClient customer = new NettyClient();
        HelloService helloService = (HelloService) customer.getBean(HelloService.class, providerName);
        String msg = helloService.hello("你好 dubbo");

        System.out.println("调用结果=" + msg);

    }

}
