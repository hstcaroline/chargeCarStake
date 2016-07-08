package com.sjtu.ist.charge.Service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sjtu.ist.charge.Dao.NoticeDao;
import com.sjtu.ist.charge.Model.Notice;
import com.sjtu.ist.charge.Model.Pager;


@Service(value = "noticeService")
public class NoticeServiceImpl implements NoticeService {

	private NoticeDao noticeDao;

	@Resource(name="noticeDao")
	public void setNoticeDao(NoticeDao noticeDao) {
		this.noticeDao = noticeDao;
	}

	public boolean addNotice(Notice notice) {
		return noticeDao.add(notice);
	}

	public List<Notice> showAllNotice() {
		return noticeDao.allNotice();
	}

	public boolean deleteNoitce(int id) {
		return noticeDao.delete(id);
	}

	public Pager<Notice> getNoticeByPage() {
		return noticeDao.getNoticeByPage(); 
	}

	public Notice getNotice(int id) {
		return noticeDao.load(id);
	}

}
