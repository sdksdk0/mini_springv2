package cn.tf.spring.framework.webmvc;

import lombok.Data;

import java.util.Map;

@Data
public class TFModelAndView {

    private String viewName;
    private Map<String,?> model;

    public TFModelAndView(String viewName) {
        this.viewName = viewName;
    }

    public TFModelAndView(String viewName, Map<String, ?> model) {
        this.viewName = viewName;
        this.model = model;
    }
}
