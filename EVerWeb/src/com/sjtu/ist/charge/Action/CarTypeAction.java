package com.sjtu.ist.charge.Action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sjtu.ist.charge.Model.CarType;
import com.sjtu.ist.charge.Model.Pager;
import com.sjtu.ist.charge.Service.CarTypeService;

@Controller("carTypeAction")
@Scope(value = "prototype")
public class CarTypeAction extends ActionSupport {
	private List<CarType> carTypes;
	private CarTypeService carTypeService;
	private String typeName;
	private Pager<CarType> pageCarType;
	private int currentId;
	private CarType carType;

	public CarType getCarType() {
		return carType;
	}

	public void setCarType(CarType carType) {
		this.carType = carType;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public List<CarType> getCarTypes() {
		return carTypes;
	}

	public void setCarTypes(List<CarType> carTypes) {
		this.carTypes = carTypes;
	}

	public CarTypeService getCarTypeService() {
		return carTypeService;
	}

	public Pager<CarType> getPageCarType() {
		return pageCarType;
	}

	public void setPageCarType(Pager<CarType> pageCarType) {
		this.pageCarType = pageCarType;
	}

	public int getCurrentId() {
		return this.currentId;
	}
	
	public void setCurrentId(int currentId) {
		this.currentId = currentId;
	}
	
	@Resource(name = "carTypeService")
	public void setCarTypeService(CarTypeService carTypeService) {
		this.carTypeService = carTypeService;
	}

	public String showAllCarType() {
		setPageCarType(carTypeService.getCarTypeByPage());
		setCarTypes(getPageCarType().getDatas());
		return "showAllCarTypeSuccess";
	}

	public String loadAddCarType() {
		return "loadAddSuccess";
	}

	public String add() {
		CarType catType = new CarType();
		catType.setTypeName(typeName);
		if (carTypeService.addCarType(catType)) {
			setCarTypes(carTypeService.showAllCarType());
			return "addSuccess";
		}
		return "login";
	}

	public String deleteCarType() {
		if (!carTypeService.deleteCarType(getCurrentId())) {
			return "login";
		}
		ActionContext.getContext().put("url", "CarTypeAction_showAllCarType");
		return "redirect";
	}
	public String loadUpdate()
	{
		carType = carTypeService.load(currentId);
		return "loadUpdateSuccess";
	}
	public String update()
	{
		carType = carTypeService.load(currentId);
		carType.setTypeName(typeName);
		if (carTypeService.update(carType)) {
			setPageCarType(carTypeService.getCarTypeByPage());
			setCarTypes(getPageCarType().getDatas());
			return "updateSuccess";
		}
		return "login";
	}
}
