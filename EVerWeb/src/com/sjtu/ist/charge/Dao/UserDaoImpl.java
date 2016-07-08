package com.sjtu.ist.charge.Dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.opensymphony.xwork2.ActionContext;
import com.sjtu.ist.charge.Model.Pager;
import com.sjtu.ist.charge.Model.SystemContext;
import com.sjtu.ist.charge.Model.User;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
	
	public User findUserByNameAndPass(String userName, String passWord) {
		String hql = "from User u where u.username = ?";
		Session session=getSession();
		List<User> us = session.createQuery(hql).setParameter(0, userName).list();
		releaseSession(session);
		//遍历所有用户名为userName的用户，找到密码对应的用户
		for(Iterator<User> iterator=us.iterator();iterator.hasNext();)
		{
			User user=iterator.next();
			if(user.getPassword().equals(passWord))
			{
				return user;
			}
		}
		
		return null;
	}

	public Pager<User> AllUserBypage() {
		Pager<User> pager = new Pager<User>();
		int pageOffset = SystemContext.getPageOffset();
		int pageSize = SystemContext.getPageSize();
		String hql="from User where type=1";
		//获取当前页的数据列表
		List<User> datas = SearchPerPage(pageOffset, pageSize,hql);
		//获得查询出的总的记录条数
		Long totalRecord = count("select count(*) from User where type=1");
		pager.setDatas(datas);
		pager.setPageOffset(pageOffset);
		pager.setPageSize(pageSize);
		pager.setTotalRecord(totalRecord);
		return pager;
	}

	public Pager<User> searchByName(String name) {
		Pager<User> pager = new Pager<User>();
		int pageOffset = SystemContext.getPageOffset();
		int pageSize = SystemContext.getPageSize();
		String hql1="from User u where type=1 and name like "+"'"+"%"+name+"%"+"'";
		System.err.println("hql1: "+ hql1);
		String hql2="select count(*) "+hql1;
		//获取当前页的数据列表
		List<User> datas = SearchPerPage(pageOffset, pageSize,hql1);
		//获得查询出的总的记录条数
		Long totalRecord = count(hql2);
		if (!datas.isEmpty()) {
			pager.setDatas(datas);
			pager.setPageOffset(pageOffset);
			pager.setPageSize(pageSize);
			pager.setTotalRecord(totalRecord);
			return pager;
		}
		return null;
	}

	public boolean updatepsw(String password) {
		//get current login userName
		Session session=getSession();
		String loginUser=(String) ActionContext.getContext().getSession().get("loginUser");
		String hql = "update User u set u.password=? where u.username=?";
		Query query=session.createQuery(hql);
		query.setString(0,password);
		query.setString(1,loginUser);
		int count=query.executeUpdate();
		releaseSession(session);
		if(count>0)
			return true;
		return false;
	}
	
	public boolean updatePswByTel(String telephone, String password) {
		String hql = "from User u where u.telephone = :tel";
		Session session = getSession();
		User u = (User)session.createQuery(hql).setParameter("tel", telephone).uniqueResult();
		if (u == null) {
			releaseSession(session);
			return false;
		}
		u.setPassword(password);
		update(u);
		releaseSession(session);
		return true;
	}

	public User getUserByTel(String telephone) {
		String hql = "from User u where u.telephone = :tel";
		Session session = getSession();
		User user = (User)session.createQuery(hql).setParameter("tel", telephone).uniqueResult();
		releaseSession(session);
		return user;
	}
}
