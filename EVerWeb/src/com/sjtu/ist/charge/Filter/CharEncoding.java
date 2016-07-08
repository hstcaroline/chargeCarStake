package com.sjtu.ist.charge.Filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


public class CharEncoding implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		resp.setCharacterEncoding("utf-8");
		req.setCharacterEncoding("utf-8");
		chain.doFilter(req, resp);
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
