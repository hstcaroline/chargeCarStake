package com.sjtu.ist.charge.Dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.sjtu.ist.charge.Model.Judge;


@Repository("judgeDao")
public class JudgeDaoImpl extends BaseDaoImpl<Judge> implements JudgeDao {

	public List<Judge> getJudgeByStakeId(int stake_id) {
		String hql = "from Judge  where stake_id = :uid";
		Session session = getSession();
		Query query = session.createQuery(hql);
		List<Judge> list =  query.setParameter("uid", stake_id).list();
		releaseSession(session);
		return list;
	}
}
