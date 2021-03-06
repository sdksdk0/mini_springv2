package cn.tf.spring.framework.annotation;

import java.lang.annotation.*;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TFAutowired {
    String value() default  "";
}
