package cn.test.dynamicProxy.cglibProxy;

import cn.test.dynamicProxy.javaProxy.IHouse;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by xiaoni on 2020/3/4.
 */
public class CglibProxy implements MethodInterceptor {

    public Object getInstance(Class<?> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("clean house for rent");
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println("get rent from tenant");
        return result;
    }
}
