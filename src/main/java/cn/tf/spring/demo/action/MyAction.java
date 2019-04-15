package cn.tf.spring.demo.action;

import cn.tf.spring.demo.service.IModifyService;
import cn.tf.spring.demo.service.IQueryService;
import cn.tf.spring.framework.annotation.TFAutowired;
import cn.tf.spring.framework.annotation.TFController;
import cn.tf.spring.framework.annotation.TFRequestMapping;
import cn.tf.spring.framework.annotation.TFRequestParam;
import cn.tf.spring.framework.webmvc.TFModelAndView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




@TFController
@TFRequestMapping("/web")
public class MyAction {

	@TFAutowired
	IQueryService queryService;
	@TFAutowired
	IModifyService modifyService;

	@TFRequestMapping("/query.json")
	public TFModelAndView query(HttpServletRequest request, HttpServletResponse response,
								@TFRequestParam("name") String name){
		String result = queryService.query(name);
		return out(response,result);
	}
	
	@TFRequestMapping("/add*.json")
	public TFModelAndView add(HttpServletRequest request, HttpServletResponse response,
							  @TFRequestParam("name") String name, @TFRequestParam("addr") String addr){
		String result = null;
		try {
			result = modifyService.add(name,addr);
			return out(response,result);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String,Object> model = new HashMap<>();
			System.out.println("e"+e.getMessage());
			model.put("detail",e.getMessage());
			model.put("stackTrace", Arrays.toString(e.getStackTrace()).replaceAll("\\[|\\]",""));
			return new TFModelAndView("500",model);
		}
	}
	
	@TFRequestMapping("/remove.json")
	public TFModelAndView remove(HttpServletRequest request,HttpServletResponse response,
		   @TFRequestParam("id") Integer id){
		String result = modifyService.remove(id);
		return out(response,result);
	}
	
	@TFRequestMapping("/edit.json")
	public void edit(HttpServletRequest request,HttpServletResponse response,
			@TFRequestParam("id") Integer id,
			@TFRequestParam("name") String name){
		String result = modifyService.edit(id,name);
		out(response,result);
	}
	
	
	
	private TFModelAndView out(HttpServletResponse resp,String str){
		try {
			resp.getWriter().write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
