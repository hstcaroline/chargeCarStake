package com.sjtu.ist.charge.Action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.sjtu.ist.charge.Model.Pager;
import com.sjtu.ist.charge.Model.UseStake;
import com.sjtu.ist.charge.Service.UseStakeService;

@Service(value = "useStakeAction")
@Scope("prototype")
public class UseStakeAction extends ActionSupport implements ModelDriven<UseStake> {
	private int userId;
	private List<UseStake> useStakes;
	private UseStakeService useStakeService;
	private Pager<UseStake> pageUseStake;
	private int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	private int currUseStakeId;

	public int getCurrUseStakeId() {
		return currUseStakeId;
	}

	public void setCurrUseStakeId(int currUseStakeId) {
		this.currUseStakeId = currUseStakeId;
	}

	private UseStake useStake;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public List<UseStake> getUseStakes() {
		return useStakes;
	}

	public void setUseStakes(List<UseStake> useStakes) {
		this.useStakes = useStakes;
	}

	public UseStake getModel() {
		return null;
	}

	public Pager<UseStake> getPageUseStake() {
		return pageUseStake;
	}

	public void setPageUseStake(Pager<UseStake> pageUseStake) {
		this.pageUseStake = pageUseStake;
	}

	public UseStake getUseStake() {
		return useStake;
	}

	public void setUseStake(UseStake useStake) {
		this.useStake = useStake;
	}

	public UseStakeService getUseStakeService() {
		return useStakeService;
	}

	@Resource(name = "useStakeService")
	public void setUseStakeService(UseStakeService useStakeService) {
		this.useStakeService = useStakeService;
	}

	public String showAllUseStake() {
		pageUseStake = useStakeService.showAllUseStake();
		useStakes = pageUseStake.getDatas();
		return "showAllUseStakeSuccess";
	}

	public String showUseStakeByUserId() {
		pageUseStake = useStakeService.showUserStakeByUserId(userId);
		useStakes = pageUseStake.getDatas();
		return "showByUserIdSuccess";
	}

	public String loadUpdate() {
		ActionContext.getContext().getSession().put("currUseStakeId", currUseStakeId);
		useStake = useStakeService.load(currUseStakeId);
		return "loadUpdateSuccess";
	}

	public String updateUS() {
		currUseStakeId = (Integer) ActionContext.getContext().getSession().get("currUseStakeId");
		UseStake us = useStakeService.load(currUseStakeId);
		us.setStatus(status);
		if (useStakeService.updateUS(us)) {
			// pageUseStake = useStakeService.showUserStakeByUserId(userId);
			pageUseStake = useStakeService.showAllUseStake();
			useStakes = pageUseStake.getDatas();
			return "updateSuccess";
		}
		return "login";
	}

	public String deleteUseStake() {
		if (!useStakeService.deleteUseStake(getCurrUseStakeId())) {
			return "login";
		}
		ActionContext.getContext().put("url", "UseStakeAction_showAllUseStake");
		return "redirect";
	}
}
