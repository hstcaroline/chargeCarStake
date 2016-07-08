package com.sjtu.ist.charge.Action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sjtu.ist.charge.Dto.AdminInfoDto;
import com.sjtu.ist.charge.Model.User;
import com.sjtu.ist.charge.Service.AdminInfoService;
import com.sjtu.ist.charge.Service.UserService;

@Controller("loginAction")
@Scope(value="prototype")
public class LoginAction extends ActionSupport {

	private String userName;
	private String passWord;
	private User user;
	private AdminInfoDto adminInfoDto;
	
	private UserService userService;
	private AdminInfoService adminInfoService;
	//private long unsolvedCompMsgCount;
	
	@Resource(name = "userService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@Resource(name="adminInfoService")
	public void setAdminInfoService(AdminInfoService adminInfoService) {
		this.adminInfoService = adminInfoService;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public AdminInfoDto getAdminInfoDto() {
		return adminInfoDto;
	}

	public void setAdminInfoDto(AdminInfoDto adminInfoDto) {
		this.adminInfoDto = adminInfoDto;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
		
	public String login() throws Exception {
			User loginUser=userService.validLogin(userName, passWord);
			if ( loginUser!= null) {
				//如果是管理员
				if (loginUser.getType()==0) {
					ActionContext.getContext().getSession().put("loginUser", userName);
					ActionContext.getContext().getSession().put("loginUserId", loginUser.getId());
					setUser(loginUser);
					AdminInfoDto adminInfo=adminInfoService.showAdminInfo();
					if (adminInfo!=null) {
						setAdminInfoDto(adminInfo);
						return SUCCESS;
					}
					return "input";
					
				}
				else {
					return "input";
				}
			} else {
				System.out.println("logging: " + userName + " " + passWord);
				this.addFieldError(passWord, "用户名或密码错误");
				return "input";
			}
	}
	
	public void validateLogin() {
		if (userName == null || "".equals(userName)) {
			this.addFieldError(userName, "用户名不能为空");
		} else if (passWord == null || "".equals(passWord)) {
			this.addFieldError(passWord, "密码不能为空");
		}
	}
	public String load()
	{
		//如果有已登录的对象,直接加载
		if(ActionContext.getContext().getSession().size()>0)
		{
			System.out.println("username:"+ActionContext.getContext().getSession().get("loginUser"));
			AdminInfoDto adminInfo=adminInfoService.showAdminInfo();
			System.err.println("loginAction : unsolvedCompMsgCount :"+adminInfo.getUnsolvedCompMsgCount());
			if (adminInfo!=null) {
				setAdminInfoDto(adminInfo);
				return "loadSuccess";
			}
		}
		return "loadFail";
	}

	
}
