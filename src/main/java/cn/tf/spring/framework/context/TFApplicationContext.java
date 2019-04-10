package cn.tf.spring.framework.context;

import cn.tf.spring.framework.beans.TFBeanFactory;
import cn.tf.spring.framework.beans.config.TFBeanDefinition;
import cn.tf.spring.framework.beans.config.TFBeanWrapper;
import cn.tf.spring.framework.beans.support.TFDefaultListableBeanFactory;
import cn.tf.spring.framework.context.support.TFBeanDefinitionReader;

import java.util.List;
import java.util.Map;

public class TFApplicationContext extends TFDefaultListableBeanFactory implements TFBeanFactory {

    private String [] configLocations;

    TFBeanDefinitionReader reader;

    public TFApplicationContext(String ... configLocations){
        this.configLocations = configLocations;
        refresh();
    }



    @Override
    protected void refresh() {
        reader = new TFBeanDefinitionReader().loadBeanDefinitions();


        doAutowrited();

    }

    private void doAutowrited(){
        for(Map.Entry<String,TFBeanDefinition> beanDefinitionEntry:super.beanDefinitionMap.entrySet()){
            String beanName = beanDefinitionEntry.getKey();
            if(!beanDefinitionEntry.getValue().isLazyInit()){
                getBean(beanName);
            }
        }
    }
    private void doRegisterBeanDefinition(List<TFBeanDefinition> beanDefinitions){
        for (TFBeanDefinition beanDefinition:beanDefinitions){
            super.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(),beanDefinition);
        }
    }


    @Override
    public Object getBean(String beanName) {
        //1、初始化
        instantiateBean(beanName,new TFBeanDefinition());
        //注入
        populateBean(beanName,new TFBeanDefinition(),new TFBeanWrapper());
        

        return null;
    }

    private void instantiateBean(String beanName, TFBeanDefinition tfBeanDefinition) {
    }
}
