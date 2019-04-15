package cn.tf.spring.framework.aop;

public interface TFAopProxy {

    Object getProxy();

    Object getProxy(ClassLoader classLoader);

}
