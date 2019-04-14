package cn.tf.spring.framework.beans;



public class TFBeanWrapper {


    private Object wrapperInstance;
    //原始的通过反射new出来，要把包装起来，存下来
    private Class<?> wrappedClass;

    public TFBeanWrapper(Object wrapperInstance) {
        this.wrapperInstance = wrapperInstance;
    }


    public Object getWrappedInstance(){
        return this.wrapperInstance;
    }

    // 返回代理以后的Class
    public Class<?> getWrappedClass(){
        return this.wrapperInstance.getClass();
    }


}
