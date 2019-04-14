package cn.tf.spring.framework.beans.config;

public class TFBeanPostProcessor {

    //在 Bean 的初始化前提供回调入口
    public Object postProcessBeforeInitialization(Object bean,String beanName) throws Exception{
        return bean;
    }
    //在 Bean 的初始化之后提供回调入口
    public Object postProcessAfterInitialization(Object bean,String beanName) throws Exception{
        return bean;
    }

}
