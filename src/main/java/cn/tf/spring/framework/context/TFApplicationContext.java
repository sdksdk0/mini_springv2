package cn.tf.spring.framework.context;

import cn.tf.spring.framework.core.TFBeanFactory;
import cn.tf.spring.framework.beans.config.TFBeanDefinition;
import cn.tf.spring.framework.beans.TFBeanWrapper;
import cn.tf.spring.framework.beans.support.TFDefaultListableBeanFactory;
import cn.tf.spring.framework.context.support.TFBeanDefinitionReader;

import java.util.List;
import java.util.Map;

public class TFApplicationContext extends TFDefaultListableBeanFactory implements TFBeanFactory {

    private String [] configLocations;

    private TFBeanDefinitionReader reader;

    public TFApplicationContext(String ... configLocations){
        this.configLocations = configLocations;
        try {
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void refresh()  {
        //1、定位配置文件
        reader = new TFBeanDefinitionReader(this.configLocations);

        //2、加载
        List<TFBeanDefinition> beanDefinitions = reader.loadBeanDefinitions();

        //3、注册
        doRegisterBeanDefinition(beanDefinitions);

        //4、初始化
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
            if(super.beanDefinitionMap.containsKey(beanDefinition.getFactoryBeanName())){
                System.out.println("the " + beanDefinition.getFactoryBeanName() + " is existes!");
                continue;
            }
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

    private void populateBean(String beanName, TFBeanDefinition tfBeanDefinition, TFBeanWrapper tfBeanWrapper) {

    }

    private void instantiateBean(String beanName, TFBeanDefinition tfBeanDefinition) {
    }
}
