package cn.tf.spring.framework.context;

import cn.tf.spring.framework.annotation.TFAutowired;
import cn.tf.spring.framework.annotation.TFController;
import cn.tf.spring.framework.annotation.TFService;
import cn.tf.spring.framework.beans.config.TFBeanPostProcessor;
import cn.tf.spring.framework.core.TFBeanFactory;
import cn.tf.spring.framework.beans.config.TFBeanDefinition;
import cn.tf.spring.framework.beans.TFBeanWrapper;
import cn.tf.spring.framework.beans.support.TFDefaultListableBeanFactory;
import cn.tf.spring.framework.beans.support.TFBeanDefinitionReader;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class TFApplicationContext extends TFDefaultListableBeanFactory implements TFBeanFactory {

    private String [] configLocations;

    private TFBeanDefinitionReader reader;

    private Map<String,Object> singletonBeanCacheMap = new ConcurrentHashMap<>();

    private Map<String,TFBeanWrapper> beanWrapperMap = new ConcurrentHashMap<>();

    public TFApplicationContext(String ... configLocations){
        this.configLocations = configLocations;
        try {
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void refresh()  throws  Exception{
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
    private void doRegisterBeanDefinition(List<TFBeanDefinition> beanDefinitions) throws Exception{
        for (TFBeanDefinition beanDefinition:beanDefinitions){
            if(super.beanDefinitionMap.containsKey(beanDefinition.getFactoryBeanName())){
                throw  new Exception("the " + beanDefinition.getFactoryBeanName() + " is existes!");
            }
            super.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(),beanDefinition);
        }
    }


    @Override
    public Object getBean(String beanName) {

        TFBeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);

        try{
            TFBeanPostProcessor beanPostProcessor = new TFBeanPostProcessor();
            Object instance = instantiateBean(beanDefinition);
            if(null == instance){return null;}

            //在实例初始化以前调用一次
            beanPostProcessor.postProcessBeforeInitialization(instance,beanName);

            //1、初始化
            TFBeanWrapper tfBeanWrapper = new TFBeanWrapper(instance);
            this.beanWrapperMap.put(beanName,tfBeanWrapper);
            beanPostProcessor.postProcessAfterInitialization(instance,beanName);

            //注入
            populateBean(beanName,instance);
            return this.beanWrapperMap.get(beanName).getWrappedInstance();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }



    }

    public Object getBean(Class<?> beanClass) {
        return (beanClass.getName());
    }




    private void populateBean(String beanName,Object instance) {

        Class<?> clazz = instance.getClass();
        if(!clazz.isAnnotationPresent(TFController.class) || clazz.isAnnotationPresent(TFService.class)){
            return;
        }
        //获得所有的fields
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if(!field.isAnnotationPresent(TFAutowired.class)){continue;}
            TFAutowired autowried = field.getAnnotation(TFAutowired.class);
            String autowiredBeanName = autowried.value().trim();
            if("".equals(autowiredBeanName)){
                autowiredBeanName = field.getType().getName();
            }
            field.setAccessible(true);
            try {
                if(this.beanWrapperMap.get(autowiredBeanName) == null){ continue; }
                field.set(instance,this.beanWrapperMap.get(autowiredBeanName).getWrappedInstance());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private Object instantiateBean(TFBeanDefinition tfBeanDefinition) {
       //1、拿到要实例化的对象的类名
        String className = tfBeanDefinition.getBeanClassName();


        //2、通过反射实例化
        Object instance=null;
        try {
            if(this.singletonBeanCacheMap.containsKey(className)){
                instance = this.singletonBeanCacheMap.get(className);
            }else{
                Class<?> clazz = Class.forName(className);
                instance = clazz.newInstance();
                this.singletonBeanCacheMap.put(tfBeanDefinition.getFactoryBeanName(),instance);
            }
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public String[] getBeanDefinitionNames() {
        return this.beanWrapperMap.keySet().toArray(new String[this.beanDefinitionMap.size()]);
    }
    public int getBeanDefinitionCount(){
        return this.beanDefinitionMap.size();
    }
    public Properties getConfig(){
        return this.reader.getConfig();
    }
}
