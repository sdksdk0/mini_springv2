package cn.tf.spring.framework.core;

public interface TFBeanFactory {
    /**
     * 根据beanName从IOC容器之中获得一个实例Bean
     * @param beanName
     * @return
     */
    Object  getBean(String beanName) throws Exception;

    Object  getBean(Class<?> clazz) throws Exception;
}
