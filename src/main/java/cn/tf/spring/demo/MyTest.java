package cn.tf.spring.demo;

import cn.tf.spring.framework.context.TFApplicationContext;

public class MyTest {

    public static void main(String[] args) {
        TFApplicationContext context = new TFApplicationContext("classpath:application.properties");
        Object myAction = context.getBean("myAction");
        System.out.println(myAction);
        System.out.println(context);

    }
}
