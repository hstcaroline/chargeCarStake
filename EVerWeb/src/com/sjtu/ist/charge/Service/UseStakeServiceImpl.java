package com.sjtu.ist.charge.Service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sjtu.ist.charge.Dao.BaseDaoImpl;
import com.sjtu.ist.charge.Dao.UseStakeDao;
import com.sjtu.ist.charge.Model.Pager;
import com.sjtu.ist.charge.Model.UseStake;


@Service("useStakeService")
public class UseStakeServiceImpl extends BaseDaoImpl<UseStake> implements UseStakeService {
	private UseStakeDao useStakeDao;

	@Resource(name = "useStakeDao")
	public void setUseStakeDao(UseStakeDao useStakeDao) {
		this.useStakeDao = useStakeDao;
	}

	public Pager<UseStake> showUserStakeByUserId(int userId) {

		return useStakeDao.getUseStakeByUserId(userId);
	}

	public Pager<UseStake> showAllUseStake() {
		return useStakeDao.getAllUsestakes();
	}

	public Pager<UseStake> showUseStakeByStakeOwnerId(int stakeOwnerId) {
		return useStakeDao.getUseStakeByStakeOwnerId(stakeOwnerId);
	}

	public UseStake load(int id) {
		return useStakeDao.load(id);
	}

	public boolean updateUS(UseStake useStake) {
		UseStake uStake = useStakeDao.load(useStake.getId());

		uStake.setEndTime(useStake.getEndTime());
		uStake.setStartTime(useStake.getStartTime());
		uStake.setStatus(useStake.getStatus());

		return update(uStake);
	}

	public boolean deleteUseStake(int id) {
		return useStakeDao.delete(id);
	}

	public List<UseStake> getByUserId(int userId) {
		return useStakeDao.getByUserId(userId);
	}

	public boolean addUseStake(UseStake useStake) {
		return useStakeDao.add(useStake);
	}

}
