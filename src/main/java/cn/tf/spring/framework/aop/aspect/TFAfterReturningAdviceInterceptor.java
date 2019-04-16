package cn.tf.spring.framework.aop.aspect;

import cn.tf.spring.framework.aop.intercept.TFMethodInterceptor;
import cn.tf.spring.framework.aop.intercept.TFMethodInvocation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TFAfterReturningAdviceInterceptor extends TFAbstractAspectAdvice implements TFAdvice,TFMethodInterceptor {

    private TFJoinPoint joinPoint;

    public TFAfterReturningAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(TFMethodInvocation mi) throws Throwable {
        Object retVal = mi.proceed();
        this.joinPoint = mi;
        this.afterReturning(retVal,mi.getMethod(),mi.getArguments(),mi.getThis());
        return retVal;
    }

    private void afterReturning(Object retVal, Method method, Object[] arguments, Object aThis) throws InvocationTargetException, IllegalAccessException {
        super.invokeAdviceMethod(this.joinPoint, retVal, null);



    }
}
