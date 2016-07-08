package com.sjtu.ist.charge.Dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.sjtu.ist.charge.Model.CarType;
import com.sjtu.ist.charge.Model.Pager;
import com.sjtu.ist.charge.Model.SystemContext;

@Repository(value = "carTypeDao")
public class CarTypeDaoImpl extends BaseDaoImpl<CarType> implements CarTypeDao {

	public List<CarType> allCarType() {
		String hql = "from CarType cartype";
		Session session = getSession();
		List<CarType> carTypes = session.createQuery(hql).list();
		releaseSession(session);
		if (!carTypes.isEmpty()) {
			return carTypes;
		}
		return null;
	}

	public Pager<CarType> getCarTypeByPage() {
		Pager<CarType> pager = new Pager<CarType>();
		String hql = "from CarType";
		String cqhl = "select count(*) from CarType";
		
		int pageOffset = SystemContext.getPageOffset();
		int pageSize = SystemContext.getPageSize();
		List<CarType> datas = SearchPerPage(pageOffset, pageSize, hql);
		long count = count(cqhl);
		
		pager.setDatas(datas);
		pager.setPageOffset(pageOffset);
		pager.setPageSize(pageSize);
		pager.setTotalRecord(count);
		return pager;
	}

}
