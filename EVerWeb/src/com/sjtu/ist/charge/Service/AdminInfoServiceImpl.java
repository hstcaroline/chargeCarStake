package com.sjtu.ist.charge.Service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sjtu.ist.charge.Dao.AdminInfoDao;
import com.sjtu.ist.charge.Dto.AdminInfoDto;


@Service("adminInfoService")
public class AdminInfoServiceImpl implements AdminInfoService{
	private AdminInfoDao adminInfoDao;


	public AdminInfoDao getAdminInfoDao() {
		return adminInfoDao;
	}
	
	@Resource(name="adminInfoDao")
	public void setAdminInfoDao(AdminInfoDao adminInfoDao) {
		this.adminInfoDao = adminInfoDao;
	}
	public AdminInfoDto showAdminInfo() {
		AdminInfoDto adminInfoDto=adminInfoDao.getAdminInfo();
		if (adminInfoDto!=null) {
			return adminInfoDto;
		}
		return null;
	}


}
