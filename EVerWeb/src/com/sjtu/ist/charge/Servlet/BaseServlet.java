package com.sjtu.ist.charge.Servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class BaseServlet extends HttpServlet {
	private static String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";
	private ApplicationContext ctx;
	
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		//获取web应用中的ApplicationContext实例
		ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
	}
	
	//返回web应用中的spring容器
	public ApplicationContext getCtx() {
		return this.ctx;
	}

    public Map<String, String> createMsg(String telephone) {
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(Url);

		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType",
				"application/x-www-form-urlencoded;charset=UTF-8");
		String mobile_code ;
		mobile_code = (int) ((Math.random() * 9 + 1) * 100000)+"";

		// System.out.println(mobile);

		String content = new String("您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。");

		NameValuePair[] data = {// 提交短信
				new NameValuePair("account", "cf_hst123"),//注册的用户名
				new NameValuePair("password", "123456"), // 注册的密码,可以使用明文密码或使用32位MD5加密
				// new NameValuePair("password",
				// util.StringUtil.MD5Encode("密码")),
				new NameValuePair("mobile", telephone),
				new NameValuePair("content", content), };

		method.setRequestBody(data);

		try {
			client.executeMethod(method);

			String SubmitResult = method.getResponseBodyAsString();

			// System.out.println(SubmitResult);

			Document doc = DocumentHelper.parseText(SubmitResult);
			Element root = doc.getRootElement();

			String code = root.elementText("code");
			String msg = root.elementText("msg");
			String smsid = root.elementText("smsid");

			System.out.println(code);
			System.out.println(msg);
			System.out.println(smsid);

			if ("2".equals(code)) {
				System.out.println("短信提交成功");
                Map<String, String> map = new HashMap<String, String>();
				 map.put(telephone, mobile_code);
                return map;
			}
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
