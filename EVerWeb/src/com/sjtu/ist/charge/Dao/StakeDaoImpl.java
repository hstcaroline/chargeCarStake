package com.sjtu.ist.charge.Dao;

import java.util.List;


import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.sjtu.ist.charge.Model.Pager;
import com.sjtu.ist.charge.Model.Stake;
import com.sjtu.ist.charge.Model.SystemContext;


@Repository(value="stakeDao")
public class StakeDaoImpl extends BaseDaoImpl<Stake> implements StakeDao {


	public Pager<Stake> getStakeByPage() {
		Pager<Stake> pager = new Pager<Stake>();
		String hql = "from Stake ";
		String chql = "select count(*)" + hql;
		
		int pageOffset = SystemContext.getPageOffset();
		int pageSize = SystemContext.getPageSize();
		List<Stake> datas = SearchPerPage(pageOffset, pageSize, hql);
		long count = count(chql);
		
		pager.setDatas(datas);
		pager.setPageOffset(pageOffset);
		pager.setPageSize(pageSize);
		pager.setTotalRecord(count);
		return pager;
	}

	public Pager<Stake> getUncheckedStakeByPage() {
		Pager<Stake> pager = new Pager<Stake>();
		String hql = "from Stake where status = 4";
		String chql = "select count(*) from Stake where status = 4";
		
		int pageOffset = SystemContext.getPageOffset();
		int pageSize = SystemContext.getPageSize();
		List<Stake> datas = SearchPerPage(pageOffset, pageSize, hql);
		long count = count(chql);
		
		pager.setDatas(datas);
		pager.setPageOffset(pageOffset);
		pager.setPageSize(pageSize);
		pager.setTotalRecord(count);
		return pager;
	}

	public List<Stake> getStakeByStatus(int status) {
		String hql = "from Stake where status = ?";
		Session session = getSession();
		Query query = session.createQuery(hql);
		query.setInteger(0, status);
		List<Stake> stakes = query.list();
		releaseSession(session);
		return stakes;
	}

	public List<Stake> getAllStakes() {
		String hql = "from Stake ";
		Session session = getSession();
		Query query = session.createQuery(hql);
		List<Stake> stakes = query.list();
		releaseSession(session);
		return stakes;
	}

	public List<Stake> getStakeByOwnerId(int userId) {
		String hql = "from Stake where owner_id = ?";
		Session session = getSession();
		Query query = session.createQuery(hql);
		query.setInteger(0, userId);
		List<Stake> stakes = query.list();
		releaseSession(session);
		return stakes;
	}

	public List<Stake> getStakeByOwnerAndStatus(int ownerId, int status) {
		String hql = "from Stake where owner_id = ? and status=?";
		Session session = getSession();
		Query query = session.createQuery(hql);
		query.setInteger(0, ownerId);
		query.setInteger(1, status);
		List<Stake> stakes = query.list();
		releaseSession(session);
		return stakes;
	}

	public boolean updateOrder(int id) throws Exception {
		Stake stake = load(id);
		if (stake == null || (stake.getStatus() != 0 && stake.getStatus() < 6)) {
		//if (stake == null || (stake.getStatus() != 0)) {
			return false;
		}
		int newStatus = stake.getStatus() + 1;
		newStatus = Math.max(6, newStatus);
		stake.setStatus(newStatus);
		update(stake);
		return true;
	}
}
