package cn.tf.spring.framework.aop.support;

import lombok.Data;

import java.lang.reflect.Method;
import java.util.List;

@Data
public class TFAdviseSupport {

    private Class<?> targetClass;
    private Object target;



    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass) throws Exception{


        return null;
    }




}
