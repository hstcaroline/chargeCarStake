package com.sjtu.ist.charge.Action;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.sjtu.ist.charge.Model.Message;
import com.sjtu.ist.charge.Model.Pager;
import com.sjtu.ist.charge.Model.Stake;
import com.sjtu.ist.charge.Service.MessageService;
import com.sjtu.ist.charge.Service.StakeService;
import com.sjtu.ist.charge.Service.UserService;

@Controller("messageAction")
@Scope(value="prototype")
public class MessageAction extends ActionSupport implements ModelDriven<Message> {
    private List<Message> messages;
    private MessageService messageService;
    private int sendTo;//发送给id为sendTo的user
    private String title;//消息标题
    private String content;//消息内容
    private Message message;
    private UserService userService;
	private Pager<Stake> pagerStake;
	private List<Stake> stakes;
	private StakeService stakeService;
	
    public Pager<Stake> getPagerStake() {
		return pagerStake;
	}
	public void setPagerStake(Pager<Stake> pagerStake) {
		this.pagerStake = pagerStake;
	}
	public List<Stake> getStakes() {
		return stakes;
	}
	public void setStakes(List<Stake> stakes) {
		this.stakes = stakes;
	}
	public StakeService getStakeService() {
		return stakeService;
	}
	 @Resource(name = "stakeService")
	public void setStakeService(StakeService stakeService) {
		this.stakeService = stakeService;
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	
    public UserService getUserService() {
		return userService;
	}
    @Resource(name = "userService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List<Message> getMessages() {
        return messages;
    }

    public int getSendTo() {
		return sendTo;
	}

	public void setSendTo(int sendTo) {
		this.sendTo = sendTo;
	}
	

	public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public MessageService getMessageService() {
        return messageService;
    }

    @Resource(name = "messageService")
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    public Message getModel() {
        // TODO Auto-generated method stub
        return null;
    }

    public String addMsg()
    {
    	Message msg = new Message();
    	msg.setTitle(title);
    	msg.setContent(content);
    	int Manage_id = (Integer) ActionContext.getContext().getSession().get("loginUserId");
    	msg.setUserByFromId(userService.load(Manage_id));
    	msg.setUserByToId(userService.load(sendTo));
    	msg.setDone(0);
    	msg.setType(0);
    	Timestamp time = new Timestamp(System.currentTimeMillis()); 
    	msg.setTime(time);
    	if(messageService.addMessage(msg))
    	{
    		setPagerStake(stakeService.getUncheckedStakeByPage());
    		setStakes(getPagerStake().getDatas());
    		return "addSuccess";
    	}
    	return "login";
    	
    }


}
