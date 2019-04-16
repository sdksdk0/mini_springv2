package cn.tf.spring.framework.aop.aspect;

import cn.tf.spring.framework.aop.intercept.TFMethodInterceptor;
import cn.tf.spring.framework.aop.intercept.TFMethodInvocation;

import java.lang.reflect.Method;

public class TFMethodBeforeAdviceInterceptor extends TFAbstractAspectAdvice implements TFAdvice,TFMethodInterceptor {

    private TFJoinPoint joinPoint;

    public TFMethodBeforeAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod,aspectTarget);
    }

    @Override
    public Object invoke(TFMethodInvocation mi) throws Throwable {
        this.joinPoint = mi;
        before(mi.getMethod(),mi.getArguments(),mi.getThis());
        return mi.proceed();
    }

    private void before(Method method,Object[] args,Object target) throws Throwable{
        super.invokeAdviceMethod(this.joinPoint,null,null);
    }


}
