package com.sjtu.ist.charge.Service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sjtu.ist.charge.Dao.CarTypeDao;
import com.sjtu.ist.charge.Model.CarType;
import com.sjtu.ist.charge.Model.Pager;

@Service("carTypeService")
public class CarTypeServiceImpl implements CarTypeService{
	private CarTypeDao carTypeDao;
	
	public CarTypeDao getCarTypeDao() {
		return carTypeDao;
	}

	@Resource(name="carTypeDao")
	public void setCarTypeDao(CarTypeDao carTypeDao) {
		this.carTypeDao = carTypeDao;
	}
	//显示所有的车型描述
	public List<CarType> showAllCarType() {
		List<CarType> carTypes=carTypeDao.allCarType();
		if(carTypes!=null)
			return carTypes;
		return null;
	}

	public boolean addCarType(CarType carType) {
		
		return carTypeDao.add(carType);
	}

	public Pager<CarType> getCarTypeByPage() {
		return carTypeDao.getCarTypeByPage();
	}

	public boolean deleteCarType(int id) {
		return carTypeDao.delete(id);
	}

	public CarType load(int id)
	{
		return carTypeDao.load(id);
	}

	public boolean update(CarType carType) {
		
		return carTypeDao.update(carType);
	}

}
