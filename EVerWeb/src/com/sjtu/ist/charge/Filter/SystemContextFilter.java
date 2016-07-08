package com.sjtu.ist.charge.Filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.sjtu.ist.charge.Model.SystemContext;


public class SystemContextFilter implements Filter {

	private int pageSize = 0;
	
	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		try {
			int pageOffset;
			try {
				pageOffset = Integer.parseInt(req.getParameter("pager.offset"));
			} catch (NumberFormatException e) {
				pageOffset = 0;
			}
			SystemContext.setPageOffset(pageOffset);
			SystemContext.setPageSize(pageSize);
			chain.doFilter(req, resp);
		} catch (Exception e) {
		} finally {
			SystemContext.removePageOffset();
			SystemContext.removePageSize();
		}
	}

	public void init(FilterConfig cfg) throws ServletException {
		try {
			pageSize = Integer.parseInt(cfg.getInitParameter("pageSize"));
		} catch (NumberFormatException e) {
			pageSize = 10;
		}
	}

}
