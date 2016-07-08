package com.sjtu.ist.charge.Action;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;
import javax.swing.text.AbstractDocument.Content;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.sjtu.ist.charge.Model.Complaint;
import com.sjtu.ist.charge.Model.Message;
import com.sjtu.ist.charge.Model.Pager;
import com.sjtu.ist.charge.Model.User;
import com.sjtu.ist.charge.Service.ComplaintService;
import com.sjtu.ist.charge.Service.MessageService;
import com.sjtu.ist.charge.Service.UserService;

public class ComplaintAction extends ActionSupport implements ModelDriven<Complaint> {
	private Pager<Complaint> comPager;
	private List<Complaint> complaints;
	private ComplaintService complaintService;
	private MessageService messageService;
	private UserService userService;
	private User carUser;
	private Complaint complaint;
	private int currentCompId;
	private int faith;
	private double remaining;
	
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

	public Pager<Complaint> getComPager() {
		return comPager;
	}

	public void setComPager(Pager<Complaint> comPager) {
		this.comPager = comPager;
	}

	public List<Complaint> getComplaints() {
		return complaints;
	}

	public void setComplaints(List<Complaint> complaints) {
		this.complaints = complaints;
	}

	public ComplaintService getComplaintService() {
		return complaintService;
	}
	public User getCarUser() {
		return carUser;
	}

	public void setCarUser(User carUser) {
		this.carUser = carUser;
	}
	public MessageService getMessageService() {
		return messageService;
	}
	
	@Resource(name = "messageService")
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
	@Resource(name = "complaintService")
	public void setComplaintService(ComplaintService complaintService) {
		this.complaintService = complaintService;
	}
	public Complaint getComplaint() {
		return complaint;
	}

	@Resource(name="userService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public void setComplaint(Complaint complaint) {
		this.complaint = complaint;
	}

	public Complaint getModel() {
		return null;
	}
	public int getCurrentCompId() {
		return currentCompId;
	}

	public void setCurrentCompId(int currentCompId) {
		this.currentCompId = currentCompId;
	}
	
	/**
	 * 获取未处理的投诉消息
	 * @return
	 */
	public String showUnsolvedCom()
	{
		comPager = complaintService.getUnsolvedComplaint();
		complaints = comPager.getDatas();
		return "showUnsolvedComSuccess";
	}
	
	public String loadHandler()
	{
		complaint=complaintService.getById(currentCompId);
		carUser=complaint.getUserByToId();
		return "loadHandlerSuccess";
	}
	
	public String handle()
	{
		complaint=complaintService.getById(currentCompId);
		//修改被投诉者的诚信值和余额作为惩罚
		carUser=complaint.getUserByToId();
		User comFrom_user = complaint.getUserByFromId();
		int manager_id = (Integer) ActionContext.getContext().getSession().get("loginUserId");
		User manager = userService.load(manager_id);
		carUser.setFaith(faith);
		carUser.setRemaining(remaining);
		//如果修改成功，将投诉状态置为1，表示已处理
		if (userService.updateUser(carUser)) {
			complaint.setStatus(1);
			complaintService.updateComplaint(complaint);
			//给投诉者和被投诉者分别发送消息
			String content = "您在"+complaint.getTime()+"对用户"+complaint.getUserByToId().getName()+"的投诉已经被处理，谢谢您的支持";
			String content1 = "用户"+complaint.getUserByFromId().getName()+"在"+complaint.getTime()+"对您进行了投诉,投诉内容为"
	                   +complaint.getContent()+",管理员已对您进行了相应处罚，若有疑问请联系SJTU公司";		
			//发送给投诉者和被投诉者
			if(sendMessage(content, manager, comFrom_user)&&sendMessage(content1, manager, carUser)){
				return "handleSuccess";
			}
			return "login";
		}
		return "login";
	}
	public String ignore()
	{
		complaint = complaintService.getById(currentCompId);
		carUser = complaint.getUserByFromId();
		int manager_id = (Integer) ActionContext.getContext().getSession().get("loginUserId");
		User manager = userService.load(manager_id);
		String content = "您在"+complaint.getTime()+"对用户"+complaint.getUserByToId().getName()+"的投诉被认为不合理已经被忽略，请您谅解";
		complaint.setStatus(1);
		if(sendMessage(content, manager, carUser)&&complaintService.updateComplaint(complaint)){
			ActionContext.getContext().put("url", "ComplaintAction_showUnsolvedCom");
			return "redirect";
		}
		return "login";
	}
	public boolean sendMessage(String content,User from_user,User to_user)
	{
		Message message = new Message();
		Timestamp time = new Timestamp(System.currentTimeMillis()); 
		message.setTitle("投诉消息反馈");
		message.setContent(content);
		message.setDone(0);//默认为未读
		message.setTime(time);
		message.setUserByFromId(from_user);
		message.setUserByToId(to_user);
		message.setType(1);//类型为投诉消息
		return messageService.addMessage(message);
		
	}
}
