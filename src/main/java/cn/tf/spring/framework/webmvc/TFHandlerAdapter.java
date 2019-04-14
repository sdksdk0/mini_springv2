package cn.tf.spring.framework.webmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TFHandlerAdapter {

    public boolean supports(Object handler){return (handler instanceof TFHandlerMapping);}
    public TFModelAndView handler (HttpServletRequest request,HttpServletResponse response,Object handler) throws  Exception{
        return null;
    }


}
