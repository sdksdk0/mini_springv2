package cn.tf.spring.framework.aop.aspect;

import java.lang.reflect.Method;

public interface TFJoinPoint {

    Object getThis();

    Object[] getArguments();

    Method getMethod();

    void setUserAttribute(String s, Object currentTimeMillis);

    Object getUserAttribute(String s);
}
