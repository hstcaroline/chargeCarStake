package com.sjtu.ist.charge.Dao;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.sjtu.ist.charge.Dto.AdminInfoDto;


@Repository(value = "adminInfoDao")
public class AdminInfoDaoImpl extends BaseDaoImpl<AdminInfoDto> implements
		AdminInfoDao {
	public AdminInfoDto getAdminInfo() {
		long userAccount = 0;
		long stakeAccount = 0;
		long stakeUsingCount = 0;
		long stakeExcepCount = 0;
		long unsolvedCompMsgCount = 0;
		long unsolvedCheckMsgCount = 0;
		long solvedMsgCount = 0;
		Session session = getSession();
		String hql1 = "select count(*) from User where type=1 ";
		String hql2 = "select count(*) from Stake ";
		String hql3 = "select count(*) from Stake where status=1 ";
		String hql4 = "select count (*) from Stake where status=3";
		String hql5 = "select count(*) from Complaint where status=0";
		String hql6 = "select count(*) from Stake where status=4";
		String hql7 = "select count(*) from Complaint where status=1";

		userAccount = (Long) session.createQuery(hql1).list().get(0);
		stakeAccount = (Long) session.createQuery(hql2).list().get(0);
		stakeUsingCount = (Long) session.createQuery(hql3).list().get(0);
		stakeExcepCount = (Long) session.createQuery(hql4).list().get(0);
		unsolvedCompMsgCount = (Long) session.createQuery(hql5).list().get(0);
		unsolvedCheckMsgCount = (Long) session.createQuery(hql6).list().get(0);
		solvedMsgCount = (Long) session.createQuery(hql7).list().get(0);

		releaseSession(session);
		AdminInfoDto adminInfoDto = new AdminInfoDto(userAccount, stakeAccount,
				stakeUsingCount, stakeExcepCount, unsolvedCompMsgCount,
				unsolvedCheckMsgCount, solvedMsgCount);
		return adminInfoDto;
	}

}
