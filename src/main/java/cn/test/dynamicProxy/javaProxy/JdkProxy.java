package cn.test.dynamicProxy.javaProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by xiaoni on 2020/3/4.
 */
public class JdkProxy implements InvocationHandler {
    private IHouse target;
    public IHouse getInstance(IHouse target) {
        this.target = target;
        Class<?> clazz = target.getClass();
        return (IHouse) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("clean house for rent");
        Object result = method.invoke(this.target, args);
        System.out.println("get rent from tenant");
        return result;
    }
}
