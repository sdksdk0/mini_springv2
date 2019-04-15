package cn.tf.spring.framework.webmvc;

import lombok.Data;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

@Data
public class TFHandlerMapping {

    private Object controller;
    private Method method;
    private Pattern pattern;


    public TFHandlerMapping(Pattern pattern, Object controller, Method method) {
        this.pattern = pattern;
        this.controller=controller;
        this.method=method;
    }

}
