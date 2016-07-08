package com.sjtu.ist.charge.Dao;

import java.util.List;

import com.sjtu.ist.charge.Model.CarType;
import com.sjtu.ist.charge.Model.Pager;


public interface CarTypeDao extends BaseDao<CarType>{
	//查看所有车型
	public List<CarType> allCarType();
	
	/**
	 * @return  获取分页后的车型
	 */
	public Pager<CarType> getCarTypeByPage();
}
