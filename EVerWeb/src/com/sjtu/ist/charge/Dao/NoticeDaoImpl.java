package com.sjtu.ist.charge.Dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.opensymphony.xwork2.ActionContext;
import com.sjtu.ist.charge.Model.Notice;
import com.sjtu.ist.charge.Model.Pager;
import com.sjtu.ist.charge.Model.SystemContext;

@Repository(value = "noticeDao")
public class NoticeDaoImpl extends BaseDaoImpl<Notice> implements NoticeDao {

	public List<Notice> allNotice() {
		String hql = "from Notice notice order by notice.time DESC";
		Session session = getSession();
		List<Notice> notices = session.createQuery(hql).list();
		releaseSession(session);
		if (!notices.isEmpty()) {
			return notices;
		}
		return null;
	}

	public Pager<Notice> getNoticeByPage() {
		Pager<Notice> pager = new Pager<Notice>();
		String hql = "from Notice";
		String chql = "select count(*) from Notice";
		
		int pageOffset = SystemContext.getPageOffset();
		int pageSize = SystemContext.getPageSize();
		List<Notice> datas =  SearchPerPage(pageOffset,pageSize,hql);
		long count = count(chql);
		
		pager.setDatas(datas);
		pager.setPageOffset(pageOffset);
		pager.setPageSize(pageSize);
		pager.setTotalRecord(count);
		
		return pager;
	}
}
