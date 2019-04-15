package cn.tf.spring.framework.webmvc.servlet;

import cn.tf.spring.framework.annotation.TFController;
import cn.tf.spring.framework.annotation.TFRequestMapping;
import cn.tf.spring.framework.context.TFApplicationContext;
import cn.tf.spring.framework.webmvc.*;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class TFDispatchServlet extends HttpServlet {

    private final String CONTEXT_CONFIG_="contextConfigLocation";
    private TFApplicationContext context;

    private List<TFHandlerMapping> handlerMappings = new ArrayList<>();
    private Map<TFHandlerMapping, TFHandlerAdapter> handlerAdapters = new HashMap<>();
    private List<TFViewResolver> viewResolvers = new ArrayList<>();


    @Override
    public void init(ServletConfig config) throws ServletException {
        //1、初始化ApplicationContext
        context = new TFApplicationContext(config.getInitParameter(CONTEXT_CONFIG_));
        //2、初始化spring MVC的九大组件
        initStrategies(context);

    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.doDispatch(req,resp);
        } catch (Exception e) {
            try {
                processDispatcherResult(req,resp,new TFModelAndView("500"));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //通过从request中拿到url
        TFHandlerMapping handlerMapping = getHandler(req);
        if(null ==handlerMapping){
            processDispatcherResult(req,resp,new TFModelAndView("404"));
            return ;
        }
        //准备调用前的参数
       TFHandlerAdapter handlerAdapter = getHandlerAdapter(handlerMapping);

        TFModelAndView mv = handlerAdapter.handle(req,resp,handlerMapping);
        //真正的输出
        processDispatcherResult(req,resp,mv);

    }

    private void processDispatcherResult(HttpServletRequest req,HttpServletResponse resp,TFModelAndView mv) throws Exception {
        //把modelview变成html或者json
        //调用 viewResolver 的 resolveView 方法
        if(null == mv){return;}

        if(this.viewResolvers.isEmpty()){return;}

        System.out.println(this.viewResolvers.get(0).getViewName());
        for (TFViewResolver viewResolver : this.viewResolvers) {
            TFView view = viewResolver.resolveViewName(mv.getViewName(),null);
            view.render(mv.getModel(),req,resp);
            return;
        }

    }

    private TFHandlerAdapter getHandlerAdapter(TFHandlerMapping handler) {
        if(this.handlerAdapters.isEmpty()){return null;}
        TFHandlerAdapter ha = this.handlerAdapters.get(handler);
        if(ha.supports(handler)){
            return ha;
        }
        return null;
    }


    protected void initStrategies(TFApplicationContext context) {
        initMultipartResolver(context);
        initLocaleResolver(context);
        initThemeResolver(context);
        initHandlerMappings(context);
        initHandlerAdapters(context);
        initHandlerExceptionResolvers(context);
        initRequestToViewNameTranslator(context);
        initViewResolvers(context);
        initFlashMapManager(context);
    }

    private TFHandlerMapping getHandler(HttpServletRequest request) throws  Exception{
        if(this.handlerMappings.isEmpty()){return null;}

        String url = request.getRequestURI();
        String contextPath = request.getContextPath();
        url = url.replaceAll(contextPath,"").replaceAll("/+","/");
        for(TFHandlerMapping handlerMapping:this.handlerMappings){
            try {
                Matcher matcher = handlerMapping.getPattern().matcher(url);
                if (!matcher.matches()) {
                    continue;
                }
                return handlerMapping;
            }catch (Exception e){
                throw  e;
            }
        }
        return null;
    }



    private void initFlashMapManager(TFApplicationContext context) {

    }

    private void initViewResolvers(TFApplicationContext context) {
        String templateRoot = context.getConfig().getProperty("templateRoot");
        String templateRootPath =
                this.getClass().getClassLoader().getResource(templateRoot).getFile();
        File templateRootDir = new File(templateRootPath);
        for (File template : templateRootDir.listFiles()) {
            this.viewResolvers.add(new TFViewResolver(templateRoot));
        }
    }

    private void initRequestToViewNameTranslator(TFApplicationContext context) {

    }

    private void initHandlerExceptionResolvers(TFApplicationContext context) {

    }

    private void initHandlerAdapters(TFApplicationContext context) {
        for(TFHandlerMapping handlerMapping: this.handlerMappings){
            this.handlerAdapters.put(handlerMapping,new TFHandlerAdapter());
        }
    }

    private void initHandlerMappings(TFApplicationContext context) {
        String[] beanNames = context.getBeanDefinitionNames();
        try{

            for(String beanName :beanNames){
                Object controller = context.getBean(beanName);
                Class<?> clazz = controller.getClass();
                if(!clazz.isAnnotationPresent(TFController.class)){
                    continue;
                }
                String baseUrl = "";
                //获取Controller的url配置
                if(clazz.isAnnotationPresent(TFRequestMapping.class)){
                    TFRequestMapping requestMapping = clazz.getAnnotation(TFRequestMapping.class);
                    baseUrl = requestMapping.value();
                }

                //获取Method的url配置
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {

                    //没有加RequestMapping注解的直接忽略
                    if(!method.isAnnotationPresent(TFRequestMapping.class)){ continue; }

                    //映射URL
                    TFRequestMapping requestMapping = method.getAnnotation(TFRequestMapping.class);

                    String regex = ("/" + baseUrl + "/" + requestMapping.value().replaceAll("\\*",".*")).replaceAll("/+", "/");
                    Pattern pattern = Pattern.compile(regex);

                    this.handlerMappings.add(new TFHandlerMapping(pattern,controller,method));
                    log.info("Mapped " + regex + "," + method);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void initThemeResolver(TFApplicationContext context) {

    }

    private void initLocaleResolver(TFApplicationContext context) {

    }

    private void initMultipartResolver(TFApplicationContext context) {

    }



}
