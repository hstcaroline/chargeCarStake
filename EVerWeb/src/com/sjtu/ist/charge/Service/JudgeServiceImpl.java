package com.sjtu.ist.charge.Service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sjtu.ist.charge.Dao.JudgeDao;
import com.sjtu.ist.charge.Model.Judge;


@Service("judgeService")
public class JudgeServiceImpl implements JudgeService {

	private JudgeDao judgeDao;


	public JudgeDao getJudgeDao() {
		return judgeDao;
	}
	@Resource(name="judgeDao")
	public void setJudgeDao(JudgeDao judgeDao) {
		this.judgeDao = judgeDao;
	}

	public List<Judge> getJudgeBystakeId(int stake_id) {
		return judgeDao.getJudgeByStakeId(stake_id);
	}

	public boolean addJudge(Judge judge) {
		return judgeDao.add(judge);
	}
}
