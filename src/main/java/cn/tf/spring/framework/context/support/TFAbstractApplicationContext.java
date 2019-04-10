package cn.tf.spring.framework.context.support;

public abstract class TFAbstractApplicationContext {

    //提供给子类重写
    protected void onRefresh(){

    }

    protected abstract void refreshBeanFactory();



}
