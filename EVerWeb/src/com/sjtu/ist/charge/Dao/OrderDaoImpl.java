package com.sjtu.ist.charge.Dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.sjtu.ist.charge.Model.Order;
import com.sjtu.ist.charge.Model.Pager;
import com.sjtu.ist.charge.Model.SystemContext;


@Repository(value = "orderDao")
public class OrderDaoImpl extends BaseDaoImpl<Order> implements OrderDao {

    public Pager<Order> allOrders() {
    	Pager<Order> pager = new Pager<Order>();
		int pageOffset = SystemContext.getPageOffset();
		int pageSize = SystemContext.getPageSize();
		String hql1 = "from Order order";
		String hql2 = "select count(*) from Order order";
		//获取当前页的数据列表
		List<Order> datas = SearchPerPage(pageOffset, pageSize,hql1);
		//获得查询出的总的记录条数
		Long totalRecord = count(hql2);
		pager.setDatas(datas);
		pager.setPageOffset(pageOffset);
		pager.setPageSize(pageSize);
		pager.setTotalRecord(totalRecord);
        return pager;
    }

	public Pager<Order> searchByUserId(int userId) {
		Pager<Order> pager = new Pager<Order>();
		int pageOffset = SystemContext.getPageOffset();
		int pageSize = SystemContext.getPageSize();
		String hql1 = "from Order order where user_id="+userId;
		String hql2 = "select count(*)"+hql1;
		//获取当前页的数据列表
		List<Order> datas = SearchPerPage(pageOffset, pageSize,hql1);
		//获得查询出的总的记录条数
		Long totalRecord = count(hql2);
		pager.setDatas(datas);
		pager.setPageOffset(pageOffset);
		pager.setPageSize(pageSize);
		pager.setTotalRecord(totalRecord);
        return pager;
	}

	public List<Order> getByUserId(int userId) {
		String hql = "from Order order where user_id="+userId;
		Session session = getSession();
		Query query = session.createQuery(hql);
		List<Order> orders = query.list();
		releaseSession(session);
		return orders;
	}

	public List<Order> getByUserIdAndStatus(int userId, int type) {
		String hql = "from Order order where user_id = ? and type = ? ";
		Session session = getSession();
		Query query = session.createQuery(hql).setParameter(0, userId).setParameter(1, type);
		List<Order> orders = query.list();
		releaseSession(session);
		return orders;
	}

}
