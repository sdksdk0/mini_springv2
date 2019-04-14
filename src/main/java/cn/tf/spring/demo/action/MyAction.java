package cn.tf.spring.demo.action;

import cn.tf.spring.demo.service.IModifyService;
import cn.tf.spring.demo.service.IQueryService;
import cn.tf.spring.framework.annotation.TFAutowired;
import cn.tf.spring.framework.annotation.TFController;
import cn.tf.spring.framework.annotation.TFRequestMapping;
import cn.tf.spring.framework.annotation.TFRequestParam;

import java.io.IOException;

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
	public void query(HttpServletRequest request, HttpServletResponse response,
								@TFRequestParam("name") String name){
		String result = queryService.query(name);
		out(response,result);
	}
	
	@TFRequestMapping("/add*.json")
	public void add(HttpServletRequest request,HttpServletResponse response,
			   @TFRequestParam("name") String name,@TFRequestParam("addr") String addr){
		String result = modifyService.add(name,addr);
		out(response,result);
	}
	
	@TFRequestMapping("/remove.json")
	public void remove(HttpServletRequest request,HttpServletResponse response,
		   @TFRequestParam("id") Integer id){
		String result = modifyService.remove(id);
		out(response,result);
	}
	
	@TFRequestMapping("/edit.json")
	public void edit(HttpServletRequest request,HttpServletResponse response,
			@TFRequestParam("id") Integer id,
			@TFRequestParam("name") String name){
		String result = modifyService.edit(id,name);
		out(response,result);
	}
	
	
	
	private void out(HttpServletResponse resp,String str){
		try {
			resp.getWriter().write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
