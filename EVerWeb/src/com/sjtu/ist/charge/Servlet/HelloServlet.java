package com.sjtu.ist.charge.Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sjtu.ist.charge.Model.Hello;
import com.sjtu.ist.charge.Model.User;
import com.sjtu.ist.charge.Service.UserService;



public class HelloServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public HelloServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

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

		UserService userService = (UserService) WebApplicationContextUtils.getWebApplicationContext(getServletContext()).getBean("userService");
		User user = userService.load(4);
		System.out.println(user);
		//String jsonStr = JSON.toJSONString(user);
		//response.getWriter().println(jsonStr);
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
		String userName = request.getParameter("userName");
		System.out.println(userName);
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		Hello hello = null;
		if ("dudu".equals(userName)) {
			hello = new Hello("dudu");
		} else {
			hello = new Hello("android");
		}
		//JSONObject jsonObject = new JSONObject(hello);
		//response.getWriter().println(jsonObject.toString());
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
