package cn.tf.spring.framework.aop.aspect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class TFAbstractAspectAdvice implements TFAdvice {

    private Method aspectMethod;
    private Object aspectTarget;

    public TFAbstractAspectAdvice(Method aspectMethod, Object aspectTarget) {
        this.aspectTarget=aspectTarget;
        this.aspectMethod = aspectMethod;
    }

    protected Object invokeAdviceMethod(TFJoinPoint joinPoint, Object returnVal, Throwable tx) throws InvocationTargetException, IllegalAccessException {

        Class<?>[] paramTypes = this.aspectMethod.getParameterTypes();
        if(null == paramTypes || paramTypes.length ==0){
            return this.aspectMethod.invoke(aspectTarget);
        }else{
            Object[] args = new Object[paramTypes.length];
            for(int i=0;i<paramTypes.length;i++){
                if(paramTypes[i] == TFJoinPoint.class){
                    args[i] = joinPoint;
                }else if(paramTypes[i] == Throwable.class){
                    args[i] = tx;
                }else if(paramTypes[i] == Object.class){
                    args[i] = returnVal;
                }
            }
            return this.aspectMethod.invoke(aspectTarget,args);
        }
    }
}
