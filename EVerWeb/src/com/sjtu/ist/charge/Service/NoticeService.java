package com.sjtu.ist.charge.Service;

import java.util.List;

import com.sjtu.ist.charge.Model.Notice;
import com.sjtu.ist.charge.Model.Pager;


public interface NoticeService {

	//添加消息
	public boolean addNotice(Notice notice);
	//查看所有消息
	public List<Notice> showAllNotice();
	
	/**
	 * 根据id删除消息
	 * @param id
	 * @return
	 */
	public boolean deleteNoitce(int id);
	
	/**
	 * 获取分页后的通知
	 * @return
	 */
	public Pager<Notice> getNoticeByPage();
	
	/**
	 * 根据id返回notice
	 * @param id
	 * @return
	 */
	public Notice getNotice(int id);
}
