package cn.tf.spring.framework.aop.intercept;

public interface TFMethodIntercaptor {

    Object invoke(TFMethodInvocation invocation) throws Throwable;

}
