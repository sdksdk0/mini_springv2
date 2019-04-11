package cn.tf.spring.framework.beans.support;

import cn.tf.spring.framework.beans.config.TFBeanDefinition;
import cn.tf.spring.framework.context.support.TFAbstractApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TFDefaultListableBeanFactory extends TFAbstractApplicationContext{


    //beanDefinitionMap用来保存配置信息
    protected final  Map<String, TFBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String,TFBeanDefinition>();

    public void refresh() throws Exception{

    }


}
