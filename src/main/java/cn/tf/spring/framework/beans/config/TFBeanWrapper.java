package cn.tf.spring.framework.beans.config;


import cn.tf.spring.framework.beans.TFFactoryBean;

public class TFBeanWrapper extends TFFactoryBean {


    private Object wrapperInstance;
    //原始的通过反射new出来，要把包装起来，存下来
    private Object originalInstance;

    public TFBeanWrapper(Object instance){
        //从这里开始，我们要把动态的代码添加进来了
        this.originalInstance = instance;
    }

    public Object getWrappedInstance(){
        return this.wrapperInstance;
    }


    // 返回代理以后的Class
    public Class<?> getWrappedClass(){
        return this.wrapperInstance.getClass();
    }


    public Object getOriginalInstance() {
        return originalInstance;
    }
}
