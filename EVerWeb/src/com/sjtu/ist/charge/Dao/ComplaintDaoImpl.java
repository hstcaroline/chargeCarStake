package com.sjtu.ist.charge.Dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.sjtu.ist.charge.Model.Complaint;
import com.sjtu.ist.charge.Model.Pager;
import com.sjtu.ist.charge.Model.SystemContext;


@Repository(value = "complaintDao")
public class ComplaintDaoImpl extends BaseDaoImpl<Complaint> implements ComplaintDao{

	public Pager<Complaint> showUnsolvedComplaint() {
		Pager<Complaint> pager = new Pager<Complaint>();
		String hql = "from Complaint complaint where status=0";
		String chql = "select count(*) "+hql;
		
		int pageOffset = SystemContext.getPageOffset();
		int pageSize = SystemContext.getPageSize();
		List<Complaint> datas =  SearchPerPage(pageOffset,pageSize,hql);
		long count = count(chql);
		
		pager.setDatas(datas);
		pager.setPageOffset(pageOffset);
		pager.setPageSize(pageSize);
		pager.setTotalRecord(count);
		
		return pager;
	}

	public List<Complaint> getComByFrom_id(int from_id) {
		String hql = "from Complaint where from_id = ?";
		Session session = getSession();
		Query query = session.createQuery(hql);
		query.setInteger(0, from_id);
		List<Complaint> complaints = query.list();
		releaseSession(session);
		return complaints;
	}

}
