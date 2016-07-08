package com.sjtu.ist.charge.Dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.sjtu.ist.charge.Model.Collection;


@Repository("collectionDao")
public class CollectionDaoImpl extends BaseDaoImpl<Collection> implements CollectionDao {

	public List<Collection> getColByUId(int uid) {
		String hql = "from Collection col where col.user.id = :uid";
		//getHibernateTemplate().find(hql).set(0, uid);
		Session session = getSession();
		Query query = session.createQuery(hql);
		List<Collection> list =  query.setParameter("uid", uid).list();
		releaseSession(session);
		return list;
	}

	public Collection getColByUserIdAndStakeId(int user_id, int stake_id) {
		String hql = "from Collection col where col.user.id = :uid and col.stake.id = :sid";
		//getHibernateTemplate().find(hql).set(0, uid);
		Session session = getSession();
		Query query = session.createQuery(hql);
		Collection collection =  (Collection) query.setParameter("uid", user_id).setParameter("sid", stake_id).uniqueResult();
		releaseSession(session);
		return collection;
	}

}
