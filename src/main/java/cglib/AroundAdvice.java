package cglib;


import java.lang.reflect.Method;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class AroundAdvice implements MethodInterceptor {
    /*
     * 重写方法
     *
     * @see net.sf.cglib.proxy.MethodInterceptor#intercept(java.lang.Object,
     * java.lang.reflect.Method, java.lang.Object[],
     * net.sf.cglib.proxy.MethodProxy)
     */

    /**
     * Parameters: obj - "this", the enhanced object
     *
     * method - intercepted Method args - argument array; primitive types are
     * wrapped
     *
     * proxy - used to invoke super (non-intercepted method); may be called as
     * many times as needed
     *
     * Returns: any value compatible with the signature of the proxied method.
     * Method returning void will ignore this value.
     */

    public Object intercept(Object target, Method method, Object[] arg2,
            MethodProxy methodProxy) throws Throwable {
        System.out.println("目标对象/真实对象      方法调用之前...");
        Object result = methodProxy.invokeSuper(target, arg2);
        System.out.println("目标对象/真实对象      方法调用之后...");
        return result + "<--真实对象的返回值。    \"通知\"中的新加内容";
    }
}

