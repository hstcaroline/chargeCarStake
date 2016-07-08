package com.sjtu.ist.charge.Dao;

import java.util.List;

import com.sjtu.ist.charge.Model.Notice;
import com.sjtu.ist.charge.Model.Pager;


public interface NoticeDao extends BaseDao<Notice> {
	//查看所有消息
	public List<Notice> allNotice();
	
	/**
	 * @return 分页后的通知数据
	 */
	public Pager<Notice> getNoticeByPage();
}
