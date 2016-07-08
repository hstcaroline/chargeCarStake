package com.sjtu.ist.charge.Service;

import java.util.List;

import com.sjtu.ist.charge.Model.Complaint;
import com.sjtu.ist.charge.Model.Pager;


public interface ComplaintService {
	/**
	 * 获得未处理的投诉消息分页显示
	 * @return
	 */
	public Pager<Complaint> getUnsolvedComplaint();
	
	/**
	 * 通过id获取相应complaint
	 * @param id
	 * @return
	 */
	public Complaint getById(int id);
	
	/**
	 * 更新complaint
	 * @param complaint
	 * @return
	 */
	public boolean updateComplaint(Complaint complaint);
	
	/**
	 * 根据投诉者查看所有投诉列表
	 * @param from_id
	 * @return
	 */
	public List<Complaint> getCompByFromId(int from_id);
	
	/**
	 * 添加投诉
	 * @param complaint
	 * @return
	 */
	public boolean addComp(Complaint complaint);

}
