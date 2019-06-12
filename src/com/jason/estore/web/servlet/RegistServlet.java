package com.jason.estore.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.jason.estore.domain.User;
import com.jason.estore.exception.RegistException;
import com.jason.estore.service.UserService;
import com.jason.estore.utils.ActiveCodeUtils;
import com.mchange.v2.beans.BeansUtils;

public class RegistServlet extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.得到所有请求参数，封装到User对象。
		User user=new User();
		try {
			BeanUtils.populate(user, request.getParameterMap());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//调用校验方法
		//在通过BeanUtils将请求参数封装到javaBean后，调用校验方法.
		//如果判断Map集合中有数据，说明存储了错误信息，就跳转到regist.jsp页面。
		//在页面上展示错误信息，使用jstl标签。
		Map<String,String> map=user.validation();
		if (map.size()!=0) {
			request.setAttribute("map", map);
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		
		
		
		//手动封装激活码
		user.setActivecode(ActiveCodeUtils.getActiveCode());
		//2.调用service完成注册工作。
		UserService service=new UserService();
		try {
			service.regist(user);
			//注册成功
			//response.setHeader("refresh", "3;url=www.estore.com");
			response.sendRedirect(request.getContextPath()+"/regist_success.jsp");
			return;
		} catch (RegistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("regist.message", "注册失败");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		

	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);

		
	}

}
