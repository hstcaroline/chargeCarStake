package com.sjtu.ist.charge.Service;

import java.util.List;

import com.sjtu.ist.charge.Model.Judge;


public interface JudgeService {

	/**
	 * 根据车桩id获取评价
	 * @param stake_id
	 * @return
	 */
	public List<Judge> getJudgeBystakeId(int stake_id);
	
	/**
	 * 添加评价
	 * @param judge
	 * @return
	 */
	public boolean addJudge(Judge judge);
}
