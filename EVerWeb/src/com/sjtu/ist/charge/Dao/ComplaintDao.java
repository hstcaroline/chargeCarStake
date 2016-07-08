package com.sjtu.ist.charge.Dao;

import java.util.List;

import com.sjtu.ist.charge.Model.Complaint;
import com.sjtu.ist.charge.Model.Pager;


public interface ComplaintDao  extends BaseDao<Complaint>{

	/**
	 * 分页显示所有投诉列表
	 * @return
	 */
	public Pager<Complaint> showUnsolvedComplaint();
	
	/**
	 * 根据投诉者查询投诉信息
	 * @param from_id
	 * @return
	 */
	public List<Complaint> getComByFrom_id(int from_id);
}
