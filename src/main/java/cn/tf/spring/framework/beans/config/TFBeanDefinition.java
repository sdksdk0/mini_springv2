package cn.tf.spring.framework.beans.config;


import lombok.Data;

//用来存储配置文件中的信息
//相当于保存在内存中的配置
@Data
public class TFBeanDefinition {

    private String beanClassName;
    private boolean lazyInit = false;
    private String factoryBeanName;


}
