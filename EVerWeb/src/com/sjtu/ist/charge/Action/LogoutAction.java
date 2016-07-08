package com.sjtu.ist.charge.Action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Controller("logoutAction")
@Scope(value="prototype")
public class LogoutAction extends ActionSupport {

	public String execute() throws Exception {
		ActionContext.getContext().getSession().clear();
		return SUCCESS;
	}
}
