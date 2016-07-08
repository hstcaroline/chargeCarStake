package com.sjtu.ist.charge.Dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;



public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {

	@Resource(name="hibernateTemplate")
	public void setSuperHibernateTemplate(HibernateTemplate hibernateTemplate) {
		super.setHibernateTemplate(hibernateTemplate);
	}
	
	private Class<T> clz;
	
	@SuppressWarnings("unchecked")
	public Class<T> getClz() {
		if(clz==null) {
			clz = ((Class<T>)(((ParameterizedType)(this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]));
		}
		return clz;
	}
	
	public boolean add(T t) {
		System.out.println(t);
		try {
			getHibernateTemplate().save(t);
			return true;
		} catch (Exception e) {
			// TODO: handle exception\
			e.printStackTrace();
			return false;
		}
	}

	public boolean delete(T t) {
		try {
			getHibernateTemplate().delete(t);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean delete(int id) {
		T t = load(id);
		if (t == null) {
			return false;
		}
		try {
			getHibernateTemplate().delete(t);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean update(T t) {
		try {
			getHibernateTemplate().update(t);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		
	}

	public T load(int id) {
		//return getHibernateTemplate().load(getClz(), id);
		return getHibernateTemplate().get(getClz(), id);
	}

	public List<T> getAfterFilter(String hql, Object[] object) {
		Session session = getSession();
		Query q = session.createQuery(hql);
		if (object != null) {
			int i = 0;
			for (Object obj : object) {
				q.setParameter(i++, obj);
			}
		}
		return q.list();
	}
	public List<T> SearchPerPage(int pageOffset,int pageSize,String hql) {
		Session session = getSession();
		Query q = session.createQuery(hql);
		
		List<T> datas = q.setFirstResult(pageOffset).setMaxResults(pageSize).list();
		releaseSession(session);
		return datas;
	}
	
	public Long count(String hql) {
		Session session = getSession();
		Query cq = session.createQuery(hql);
		long totalRecord = 0; 
		totalRecord = (Long) cq.uniqueResult();
		releaseSession(session);
		System.out.println("total is : "+totalRecord);
		return totalRecord;
	}
}
