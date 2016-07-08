package com.sjtu.ist.charge.Dao;

import java.util.List;

import com.sjtu.ist.charge.Model.Judge;


public interface JudgeDao extends BaseDao<Judge>{

	public List<Judge> getJudgeByStakeId(int stake_id);
}
