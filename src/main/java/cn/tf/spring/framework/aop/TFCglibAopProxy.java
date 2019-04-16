package cn.tf.spring.framework.aop;

import cn.tf.spring.framework.aop.support.TFAdviseSupport;

public class TFCglibAopProxy  implements TFAopProxy{

    private TFAdviseSupport advised;

    public TFCglibAopProxy(TFAdviseSupport advised){
        this.advised = advised;
    }

    @Override
    public Object getProxy() {
        return null;
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return null;
    }
}
