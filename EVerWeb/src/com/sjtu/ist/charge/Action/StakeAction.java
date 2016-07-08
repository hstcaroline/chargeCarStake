package com.sjtu.ist.charge.Action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.sjtu.ist.charge.Dto.json.StakeDto;
import com.sjtu.ist.charge.Model.Pager;
import com.sjtu.ist.charge.Model.Stake;
import com.sjtu.ist.charge.Model.User;
import com.sjtu.ist.charge.QRcode.Generate;
import com.sjtu.ist.charge.Service.StakeService;
import com.sjtu.ist.charge.Service.UserService;

@Controller("stackAction")
@Scope(value = "prototype")
public class StakeAction extends ActionSupport implements ModelDriven<Stake> {
	private Stake stake;
	private StakeService stakeService;
	private UserService userService;
	private List<Stake> stakes;
	private int currentId;
	private Pager<Stake> pagerStake;
	private int status;
	private double initLongitude;
	private double initlatitude;
	private double checkok;
	
	public int getCurrentId() {
		return currentId;
	}

	public void setCurrentId(int currentId) {
		this.currentId = currentId;
	}

	public Stake getStake() {
		return stake;
	}

	public void setStake(Stake stake) {
		this.stake = stake;
	}

	public StakeService getStakeService() {
		return stakeService;
	}

	public Pager<Stake> getPagerStake() {
		return pagerStake;
	}

	public void setPagerStake(Pager<Stake> pagerStake) {
		this.pagerStake = pagerStake;
	}

	@Resource(name = "stakeService")
	public void setStakeService(StakeService stakeService) {
		this.stakeService = stakeService;
	}

	public UserService getUserService() {
		return userService;
	}
	@Resource(name = "userService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List<Stake> getStakes() {
		return stakes;
	}

	public void setStakes(List<Stake> stakes) {
		this.stakes = stakes;
	}

	public Stake getModel() {
		return null;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	public double getInitlatitude() {
		return initlatitude;
	}

	public void setInitlatitude(double initlatitude) {
		this.initlatitude = initlatitude;
	}

	public double getInitLongitude() {
		return initLongitude;
	}

	public void setInitLongitude(double initLongitude) {
		this.initLongitude = initLongitude;
	}
	public double getCheckok() {
		return checkok;
	}

	public void setCheckok(double checkok) {
		this.checkok = checkok;
	}

	/**
	 * 分页显示所有车桩
	 * @return
	 */
	public String showAllStake() {
		setPagerStake(stakeService.getStakeByPage());
		setStakes(getPagerStake().getDatas());
		return "showAllStakeSuccess";
	}

	public String showUncheckedStake() {
		setPagerStake(stakeService.getUncheckedStakeByPage());
		setStakes(getPagerStake().getDatas());
		return "showUncheckedSuccess";
	}
	
	public String loadUpdate()
	{
		
		ActionContext.getContext().getSession().put("currentStakeId", currentId);
		stake = stakeService.load(currentId);
		return "loadUpdateSuccess";
	}
	public String update() {
		currentId = (Integer) ActionContext.getContext().getSession().get("currentStakeId");
		Stake s = stakeService.load(currentId);
		s.setStatus(status);
		if (stakeService.update(s)) {
			setPagerStake(stakeService.getStakeByPage());
			setStakes(getPagerStake().getDatas());
			return "updateSuccess";
		}
		return "login";
	}
	public String loadCheck()
	{
		//System.err.println("current stake id is :" + currentId);
		ActionContext.getContext().getSession().put("currentStakeId", currentId);
		stake = stakeService.load(currentId);
		initLongitude = stake.getLongitude();
		initlatitude = stake.getLatitude();
		return "loadCheckSuccess";
	}
	public String showStakeInMap()
	{
		//根据所选状态显示对应的所有车桩，默认情况下显示所有的车桩
		if(status!= -1)
		{
			setStakes(stakeService.showStakeByStatus(status));
		}
		else {
			setStakes(stakeService.showAllStakes());
		}
		//默认地图显示的中心点在闵行校区，如果有超过一个车桩，地图初始化的中心点为前两个车桩的中点
		initLongitude = 121.442941;
		initlatitude = 31.031892;
		if (stakes.size()>=1) {
			initLongitude = stakes.get(0).getLongitude();
			initlatitude = stakes.get(0).getLatitude();
		}
		else if(stakes.size()==1) {
			
		}
		System.err.println("一共有："+stakes.size());
		return "showStakeInMapSuccess";
	}

	public String deleteStake() {
		//获取车桩主的id
		int ownerId = stakeService.load(getCurrentId()).getUser().getId();
		if (!stakeService.deleteStake(getCurrentId())) {
			return "login";
		}
		//如果车桩被删除完,将车桩主的拥有车桩属性置为0，表示未拥有车桩
		if(stakeService.getStakesByOwnerId(ownerId).size()==0)
		{
			User user = userService.load(ownerId);
			user.setHaveStake(0);
			userService.updateUser(user);
		}
		ActionContext.getContext().put("url", "StakeAction_showAllStake");
		return "redirect";
	}

	public String deleteStakeInMap(){
		//获取车桩主的id
		int ownerId = stakeService.load(getCurrentId()).getUser().getId();
		if (!stakeService.deleteStake(getCurrentId())) {
			return "login";
		}
		//如果车桩被删除完,将车桩主的拥有车桩属性置为0，表示未拥有车桩
		if(stakeService.getStakesByOwnerId(ownerId).size()==0)
		{
			User user = userService.load(ownerId);
			user.setHaveStake(0);
			userService.updateUser(user);
		}
		ActionContext.getContext().put("url", "StakeAction_showStakeInMap?status=-1");
		return "redirect";
	}
	public String deleteUncheckedStake() {
		if (!stakeService.deleteStake(getCurrentId())) {
			return "login";
		}
		ActionContext.getContext().put("url", "StakeAction_showUncheckedStake");
		return "redirect";
	}
	public String check()
	{
		boolean done = false;
		//审核通过
		if(checkok==0)
		{
			//为车桩生成二维码
			stake= stakeService.load(currentId);
			stake.setStatus(2);
			//JSONObject jsonObject = new JSONObject(stake);
			//String content = "test";
			StakeDto stakeDto =new StakeDto(stakeService.load(currentId));
			JSONObject jsonObject = new JSONObject(stakeDto);
			Generate generate = new Generate();
			String content = jsonObject.toString();
			String filePath = generate.generateQRCode(content,stake.getId());
			System.out.println(filePath);
			stake.setQrCode(filePath);
			if (stakeService.update(stake)) {
				done = true; 
			}
		}
		//审核被驳回
		else {
			stake = stakeService.load(currentId);
			stake.setStatus(5);
			if (stakeService.update(stake)) {
				done = true; 
			}
		}
		if(done)
		{
			return "checkdone";
		}
		return "login";
	}


}
