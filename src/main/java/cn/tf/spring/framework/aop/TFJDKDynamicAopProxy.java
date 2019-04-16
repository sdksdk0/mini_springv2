package cn.tf.spring.framework.aop;

import cn.tf.spring.framework.aop.intercept.TFMethodInvocation;
import cn.tf.spring.framework.aop.support.TFAdviseSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class TFJDKDynamicAopProxy implements TFAopProxy, InvocationHandler {

    private TFAdviseSupport advised;

    public TFJDKDynamicAopProxy(TFAdviseSupport config){
        this.advised = config;
    }

    @Override
    public Object getProxy() {
        return getProxy(this.advised.getTargetClass().getClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return Proxy.newProxyInstance(classLoader,this.advised.getTargetClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<Object> interceptorsAndDynamicMethodMatchers = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method,this.advised.getTargetClass());
        System.out.println("interceptorsAndDynamicMethodMatchers:"+interceptorsAndDynamicMethodMatchers);
        TFMethodInvocation invocation = new TFMethodInvocation(proxy,this.advised.getTarget(),method,args,this.advised.getTargetClass(),interceptorsAndDynamicMethodMatchers);
        return invocation.proceed();
    }
}
