package cglib;

import net.sf.cglib.proxy.Enhancer;

public class ProxyTarget {

    public static void abc() {
        System.out.println("我执行static");
    }

    public  void abcb() {
        System.out.println("我执行abcb。。");
    }

    public static void main(String[] args) {
        // 增强处理器：拦截的方法插入代理控制逻辑的处理器
        Enhancer enhancer = new Enhancer();
        // 设置要代理的目标类
        enhancer.setSuperclass(ProxyTarget.class);
        // 设置要代理的拦截器
        enhancer.setCallback(new AroundAdvice());
        // 生成并返回代理对象
        ProxyTarget p = (ProxyTarget) enhancer.create();

        p.abc();
        p.abcb();
    }

}
