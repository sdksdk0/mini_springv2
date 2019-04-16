package cn.tf.spring.framework.aop.config;

import lombok.Data;

@Data
public class TFAopConfig {

    private String pointCut;
    private String aspectBefore;
    private String aspectAfter;
    private String aspectClass;
    private String aspectAfterThrow;
    private String aspectAfterThrowingName;
    private String aspectAround;



}
