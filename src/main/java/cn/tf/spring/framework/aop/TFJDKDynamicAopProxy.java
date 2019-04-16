package cn.tf.spring.framework.aop;

import cn.tf.spring.framework.aop.intercept.TFMethodInvocation;
import cn.tf.spring.framework.aop.support.TFAdviseSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class TFJDKDynamicAopProxy implements TFAopProxy, InvocationHandler {

    private TFAdviseSupport advised;

    TFJDKDynamicAopProxy(TFAdviseSupport advised){
        this.advised = advised;
    }

    @Override
    public Object getProxy() {
        return getProxy(this.advised.getTargetClass().getClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return  Proxy.newProxyInstance(classLoader,this.advised.getTargetClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<Object> interceptorsAndDynamicInterceptionAdvice = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, this.advised.getTargetClass());

        TFMethodInvocation invocation = new TFMethodInvocation(proxy,null,method,args,
                this.advised.getTargetClass(),interceptorsAndDynamicInterceptionAdvice);
        return invocation.process();
    }
}
