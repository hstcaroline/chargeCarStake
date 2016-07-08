package com.sjtu.ist.charge.Dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.sjtu.ist.charge.Model.Pager;
import com.sjtu.ist.charge.Model.SystemContext;
import com.sjtu.ist.charge.Model.UseStake;


@Repository(value="useStakeDao")
public class UseStakeDaoImpl extends BaseDaoImpl<UseStake> implements UseStakeDao {

	public Pager<UseStake> getUseStakeByUserId(int userId) {
		Pager<UseStake> pager = new Pager<UseStake>();
		String hql = "from UseStake where user_id = " + userId;
		String chql = "select count(*)" + hql;
		
		int pageOffset = SystemContext.getPageOffset();
		int pageSize = SystemContext.getPageSize();
		List<UseStake> datas = SearchPerPage(pageOffset, pageSize, hql);
		long count = count(chql);
		
		pager.setDatas(datas);
		pager.setPageOffset(pageOffset);
		pager.setPageSize(pageSize);
		pager.setTotalRecord(count);
		return pager;
	}

	public Pager<UseStake> getAllUsestakes() {
		Pager<UseStake> pager = new Pager<UseStake>();
		String hql = "from UseStake";
		String chql = "select count(*)" + hql;
		
		int pageOffset = SystemContext.getPageOffset();
		int pageSize = SystemContext.getPageSize();
		List<UseStake> datas = SearchPerPage(pageOffset, pageSize, hql);
		long count = count(chql);
		
		pager.setDatas(datas);
		pager.setPageOffset(pageOffset);
		pager.setPageSize(pageSize);
		pager.setTotalRecord(count);
		return pager;
	}

	public Pager<UseStake> getUseStakeByStakeOwnerId(int stakeOwnerId) {
		Pager<UseStake> pager = new Pager<UseStake>();
		String hql = "from UseStake where stake_owner_id = " + stakeOwnerId;
		String chql = "select count(*)" + hql;
		
		int pageOffset = SystemContext.getPageOffset();
		int pageSize = SystemContext.getPageSize();
		List<UseStake> datas = SearchPerPage(pageOffset, pageSize, hql);
		long count = count(chql);
		
		pager.setDatas(datas);
		pager.setPageOffset(pageOffset);
		pager.setPageSize(pageSize);
		pager.setTotalRecord(count);
		return pager;
	}

	public List<UseStake> getByUserId(int userId) {
		String hql = "from UseStake where user_id = "+userId;
		Session session = getSession();
		Query query = session.createQuery(hql);
		List<UseStake> list =  query.list();
		releaseSession(session);
		return list;
	}

}
