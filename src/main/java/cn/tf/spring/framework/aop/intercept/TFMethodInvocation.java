package cn.tf.spring.framework.aop.intercept;

import java.lang.reflect.Method;
import java.util.List;

public class TFMethodInvocation {

    public TFMethodInvocation (
            Object proxy, Object target, Method method, Object[] arguments,
            Class<?> targetClass, List<Object> interceptorsAndDynamicMethodMatchers) {

    }


    public Object process() throws Exception{
        return null;
    }



}
