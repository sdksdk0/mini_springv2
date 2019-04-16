package cn.tf.spring.framework.aop.aspect;

import cn.tf.spring.framework.aop.intercept.TFMethodInterceptor;
import cn.tf.spring.framework.aop.intercept.TFMethodInvocation;

import java.lang.reflect.Method;

public class TFAfterThrowingAdviceInterceptor extends TFAbstractAspectAdvice implements TFAdvice,TFMethodInterceptor {

    private String throwingName;

    public TFAfterThrowingAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(TFMethodInvocation mi) throws Throwable {
       try{
           return mi.proceed();
       }catch (Throwable e){
           invokeAdviceMethod(mi,null,e.getCause());
           throw e;
       }
    }

    public void setThrowName(String aspectAfterThrowingName) {
        this.throwingName = aspectAfterThrowingName;
    }
}
