package com.sjtu.ist.charge.Action;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sjtu.ist.charge.Model.Pager;
import com.sjtu.ist.charge.Model.UseStake;
import com.sjtu.ist.charge.Model.User;
import com.sjtu.ist.charge.Service.UseStakeService;
import com.sjtu.ist.charge.Service.UserService;

@Controller("showPersonalInfo")
@Scope(value = "prototype")
public class ShowPersonalInfo extends ActionSupport {

	private String oldpassword;
	private String newpassword;
	private String searchName;
	private int currentCarUserId;
	private int faith;
	private double remaining;
	private String erroMsg;

	private User user;
	private List<User> carUsers;
	private List<UseStake> useStakes;
	private User carUser;

	private Pager<User> pageUsers;
	private Pager<UseStake> pageUseStakes;

	private UserService userService;
	private UseStakeService useStakeService;



	@Resource(name = "userService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public List<UseStake> getUseStakes() {
		return useStakes;
	}

	public String getErroMsg() {
		return erroMsg;
	}
	public void setErroMsg(String erroMsg) {
		this.erroMsg = erroMsg;
	}

	public void setUseStakes(List<UseStake> useStakes) {
		this.useStakes = useStakes;
	}

	public Pager<UseStake> getPageUseStakes() {
		return pageUseStakes;
	}

	public void setPageUseStakes(Pager<UseStake> pageUseStakes) {
		this.pageUseStakes = pageUseStakes;
	}

	public UseStakeService getUseStakeService() {
		return useStakeService;
	}
	@Resource(name = "useStakeService")
	public void setUseStakeService(UseStakeService useStakeService) {
		this.useStakeService = useStakeService;
	}

	public Pager<User> getPageUsers() {
		return pageUsers;
	}

	public void setPageUsers(Pager<User> pageUsers) {
		this.pageUsers = pageUsers;
	}

	public String getOldpassword() {
		return oldpassword;
	}

	public String getNewpassword() {
		return newpassword;
	}

	public int getCurrentCarUserId() {
		return currentCarUserId;
	}

	public void setCurrentCarUserId(int currentCarUserId) {
		this.currentCarUserId = currentCarUserId;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}
	public int getFaith() {
		return faith;
	}

	public void setFaith(int faith) {
		this.faith = faith;
	}

	public double getRemaining() {
		return remaining;
	}

	public void setRemaining(double remaining) {
		this.remaining = remaining;
	}
	public List<User> getCarUsers() {
		return carUsers;
	}

	public void setCarUsers(List<User> carUsers) {
		this.carUsers = carUsers;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public User getCarUser() {
		return carUser;
	}

	public void setCarUser(User carUser) {
		this.carUser = carUser;
	}

	private void getUser() {
		int userId = (Integer) ActionContext.getContext().getSession()
				.get("loginUserId");
		user = userService.load(userId);
	}

	// check old password of the user
	public String checkOldpsw() {
		// get the current login user
		getUser();

		String psw = user.getPassword();
		System.out.println("oldpsw: " + oldpassword + "psw:" + psw);
		// success
		if (psw.equals(oldpassword)) {
			return "checkSuccess";
		}
		else {
			//ActionContext.getContext().getSession().put("erroMsg", "密码填写错误");
			//ActionContext.getContext().getApplication().put("erroMsg","密码填写错误");
			erroMsg = "密码填写错误";
		}
		return "check";
	}

	public String load() {
		return "load";
	}

	public String updatepsw() {
		if (userService.updatepsw(newpassword)) {
			return "updatePswSuccess";
		}
		return "login";
	}

	public String showAllCarUser() {
		pageUsers = userService.pageCarUser();
		carUsers = pageUsers.getDatas();
		System.out.println(carUsers.size());
		return "showAllUserSuccess";
	}

	/**
	 * 查看当前所选的车主用户
	 * 
	 * @return
	 */
	public String showCurrentCarUser() {
		// 如果是第一次显示某用户信息,加入到session
		if (!ActionContext.getContext().getSession()
				.containsKey("CurrentCarUserId")) {
			ActionContext.getContext().getSession()
					.put("CurrentCarUserId", currentCarUserId);
		}
		// 如果是已经显示过该用户，在进行下一页order的查询
		else {
			int userId = (Integer) ActionContext.getContext().getSession()
					.get("CurrentCarUserId");
			// 当当前的user不是已有的session user且传入了当前userId的情况下更新session
			if (userId != currentCarUserId && currentCarUserId != 0) {
				ActionContext.getContext().getSession()
						.put("CurrentCarUserId", currentCarUserId);
			}
		}
		currentCarUserId = (Integer) ActionContext.getContext().getSession()
				.get("CurrentCarUserId");
		User u = userService.load(currentCarUserId);
		if (u != null) {
			carUser = userService.load(currentCarUserId);
			pageUseStakes = useStakeService.showUserStakeByUserId(currentCarUserId);
			useStakes = pageUseStakes.getDatas();
			if (useStakes.size()==0) {
				pageUseStakes = useStakeService.showUseStakeByStakeOwnerId(currentCarUserId);
				useStakes = pageUseStakes.getDatas();
			}
			return "showCurrUserSuccess";
		}
		return "login";
	}

	public String searchByName() {
		pageUsers = userService.searchByName(searchName);
		if (pageUsers != null) {
			carUsers = pageUsers.getDatas();
		}
		else
		{
			pageUsers = userService.pageCarUser();
			carUsers = pageUsers.getDatas();
			erroMsg = "当前未查找到该姓名的用户";
		}
		return "searchdone";
	}

	public String deleteById() {
		if (!userService.deleteUser(getCurrentCarUserId())) {
			return "login";
		}
		ActionContext.getContext().put("url", "ShowPersonalInfo_showAllCarUser");
		return "redirect";
	}

	public String loadUpdate() {
		carUser = userService.load((Integer) ActionContext.getContext()
				.getSession().get("CurrentCarUserId"));
		return "loadUpdateSuccess";
	}

	public String update()
	{
		currentCarUserId = (Integer)ActionContext.getContext().getSession().get("CurrentCarUserId");
		User u = userService.load(currentCarUserId);
		u.setFaith(faith);
		u.setRemaining(remaining);
		if (userService.updateUser(u)) {
			carUser = u;
			pageUseStakes = useStakeService.showUserStakeByUserId(currentCarUserId);
			if (pageUseStakes != null) {
				useStakes = pageUseStakes.getDatas();
			}
			return "updateSuccess";
		}
		return "login";
	}


}
