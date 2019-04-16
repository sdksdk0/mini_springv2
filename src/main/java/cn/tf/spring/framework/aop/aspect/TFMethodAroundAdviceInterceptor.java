package cn.tf.spring.framework.aop.aspect;

import cn.tf.spring.framework.aop.intercept.TFMethodInterceptor;
import cn.tf.spring.framework.aop.intercept.TFMethodInvocation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TFMethodAroundAdviceInterceptor extends TFAbstractAspectAdvice implements TFAdvice,TFMethodInterceptor {

    private TFJoinPoint joinPoint;

    public TFMethodAroundAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod,aspectTarget);
    }

    @Override
    public Object invoke(TFMethodInvocation mi) throws Throwable {
        this.joinPoint = mi;
        //环绕之前
        before(mi.getMethod(),mi.getArguments(),mi.getThis());
        Object retVal = mi.proceed();
        //环绕之后
        this.afterReturning(retVal,mi.getMethod(),mi.getArguments(),mi.getThis());
        return retVal;
    }

    private void before(Method method,Object[] args,Object target) throws Throwable{
        System.out.println("环绕之前通知");
        super.invokeAdviceMethod(this.joinPoint,null,null);
    }

    private void afterReturning(Object retVal, Method method, Object[] arguments, Object aThis) throws InvocationTargetException, IllegalAccessException {
        System.out.println("环绕之后通知");
        super.invokeAdviceMethod(this.joinPoint, retVal, null);
    }


}
