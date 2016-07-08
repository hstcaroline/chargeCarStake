package com.sjtu.ist.charge.Action;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.sjtu.ist.charge.Model.Notice;
import com.sjtu.ist.charge.Model.Pager;
import com.sjtu.ist.charge.Service.NoticeService;

@Controller(value = "noticeAction")
@Scope(value = "prototype")
public class NoticeAction extends ActionSupport {
	private int type;
	private String title;
	private String content;
	private NoticeService noticeService;
	private List<Notice> notices;
	private Notice notice;
	private int currentNoticeId;
	private Pager<Notice> pagerNotice;

	public List<Notice> getNotices() {
		return notices;
	}

	public void setNotices(List<Notice> notices) {
		this.notices = notices;
	}

	public NoticeService getNoticeService() {
		return noticeService;
	}
	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}
	@Resource(name = "noticeService")
	public void setNoticeService(NoticeService noticeService) {
		this.noticeService = noticeService;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public void setCurrentNoticeId(int id) {
		this.currentNoticeId = id;
	}

	public int getCurrentNoticeId() {
		return this.currentNoticeId;
	}

	public Pager<Notice> getPagerNotice() {
		return pagerNotice;
	}

	public void setPagerNotice(Pager<Notice> pagerNotice) {
		this.pagerNotice = pagerNotice;
	}

	public String addNotice() {
		Timestamp t = new Timestamp(System.currentTimeMillis());
		Notice notice = new Notice(type, title, content, t);
		if (noticeService.addNotice(notice)) {
			// 如果插入成功显示所有notice
			List<Notice> noticeList = noticeService.showAllNotice();
			setNotices(noticeList);
			return "addNoticeSuccess";
		}
		return "login";
	}

	public String loadNoticeAdd() {
		return "loadNoticeAdd";
	}

	public String showAllNotice() {
		setPagerNotice(noticeService.getNoticeByPage());
		setNotices(getPagerNotice().getDatas());
		return "showAllNoticeSuccess";
	}

	public String deleteNotice() {
		if (!noticeService.deleteNoitce(getCurrentNoticeId())) {
			return "login";
		}
		ActionContext.getContext().put("url", "NoticeAction_showAllNotice");
		return "redirect";
	}
	public String noticeDetail(){
		notice = noticeService.getNotice(currentNoticeId);
		return "noticeDetail";
	}
}
