package cn.tf.spring.framework.beans.support;

import cn.tf.spring.framework.beans.config.TFBeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TFBeanDefinitionReader {

    private Properties config = new Properties();

    //在配置文件中，用来获取自动扫描的包名的key
    private final String SCAN_PACKAGE = "scanPackage";

    private List<String> registyBeanClasses = new ArrayList<String>();

    public TFBeanDefinitionReader(String... locations){
        InputStream is =  this.getClass().getClassLoader().getResourceAsStream(locations[0].replace("classpath:",""));
        try {
            config.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(null != is){is.close();}
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        doScanner(config.getProperty(SCAN_PACKAGE));
    }

    private void doScanner(String packageName) {

        //URL url = this.getClass().getClassLoader().getResource("/" + packageName.replaceAll("\\.","/"));
        URL url = this.getClass().getResource("/" + packageName.replaceAll("\\.","/"));

        File classDir = new File(url.getFile());

        for (File file : classDir.listFiles()){
            if(file.isDirectory()){
                doScanner(packageName + "." +file.getName());
            }else {
                if(!file.getName().endsWith(".class")){continue;}
                registyBeanClasses.add(packageName + "." + file.getName().replace(".class",""));
            }
        }

    }


    //把配置文件中扫描到的所有的配置信息转换为TFBeanDefinition对象，以便于之后IOC操作方便
    public List<TFBeanDefinition> loadBeanDefinitions(){
        List<TFBeanDefinition> result = new ArrayList<TFBeanDefinition>();
        try {
            for (String className : registyBeanClasses) {
                Class<?> beanClass = Class.forName(className);
                //如果是一个接口，是不能实例化的
                //用它实现类来实例化
                if(beanClass.isInterface()) { continue; }

                result.add(doCreateBeanDefinition(lowerFirstCase(beanClass.getSimpleName()),beanClass.getName()));
                Class<?> [] interfaces = beanClass.getInterfaces();
                for (Class<?> i : interfaces) {
                    result.add(doCreateBeanDefinition(i.getName(),beanClass.getName()));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public Properties getConfig(){
        return this.config;
    }


    //把每一个配信息解析成一个BeanDefinition
    private TFBeanDefinition doCreateBeanDefinition(String factoryBeanName,String beanClassName){
        TFBeanDefinition beanDefinition = new TFBeanDefinition();
        beanDefinition.setBeanClassName(beanClassName);
        beanDefinition.setFactoryBeanName(factoryBeanName);
        return beanDefinition;
    }


    private String lowerFirstCase(String str){
        char [] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
}

