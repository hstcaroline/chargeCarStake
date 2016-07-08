package com.sjtu.ist.charge.Service;

import java.util.List;

import com.sjtu.ist.charge.Model.CarType;
import com.sjtu.ist.charge.Model.Pager;

public interface CarTypeService {

	//显示所有车型
	public List<CarType> showAllCarType();
	
	//添加车型
	public boolean addCarType(CarType carType);
	
	/**
	 * @return 分页后的车型数据
	 */
	public Pager<CarType> getCarTypeByPage();
	
	/**
	 * 根据id删除车型
	 * @param id
	 * @return
	 */
	public boolean deleteCarType(int id);
	
	public CarType load(int id);
	/**
	 * 更新carType
	 * @param carType
	 * @return
	 */
	public boolean update(CarType carType);
}
