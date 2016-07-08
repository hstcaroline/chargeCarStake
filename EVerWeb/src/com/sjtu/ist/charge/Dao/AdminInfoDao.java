package com.sjtu.ist.charge.Dao;

import com.sjtu.ist.charge.Dto.AdminInfoDto;


public interface AdminInfoDao extends BaseDao<AdminInfoDto>{
	/**
	 * 获得管理员要管理的所有信息
	 * @return
	 */
	public AdminInfoDto getAdminInfo();

}
