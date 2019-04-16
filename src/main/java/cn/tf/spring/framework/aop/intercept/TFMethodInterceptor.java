package cn.tf.spring.framework.aop.intercept;

public interface TFMethodInterceptor {

    Object invoke(TFMethodInvocation invocation) throws Throwable;

}
