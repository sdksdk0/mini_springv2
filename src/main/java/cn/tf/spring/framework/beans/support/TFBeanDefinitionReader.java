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


    public List<TFBeanDefinition> loadBeanDefinitions(){
        List<TFBeanDefinition> result = new ArrayList<TFBeanDefinition>();
        for (String className : registyBeanClasses){
            TFBeanDefinition beanDefinition = doCreateBeanDefinitions(className);
            if(null ==beanDefinition){continue;}
            result.add(beanDefinition);
        }
        return result;
    }
    public Properties getConfig(){
        return this.config;
    }


    private TFBeanDefinition doCreateBeanDefinitions(String className){
        try {
                Class<?> beanClass = Class.forName(className);
                //
                if(!beanClass.isInterface()){
                    TFBeanDefinition beanDefinition = new TFBeanDefinition();
                    beanDefinition.setBeanClassName(className);
                    beanDefinition.setFactoryBeanName(lowerFirstCase(beanClass.getSimpleName()));
                    return beanDefinition;
                }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }




    private String lowerFirstCase(String str){
        char [] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }


}

