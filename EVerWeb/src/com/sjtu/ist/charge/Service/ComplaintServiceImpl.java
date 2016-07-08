package com.sjtu.ist.charge.Service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sjtu.ist.charge.Dao.ComplaintDao;
import com.sjtu.ist.charge.Model.Complaint;
import com.sjtu.ist.charge.Model.Pager;


@Service("complaintService")
public class ComplaintServiceImpl implements ComplaintService{
	private ComplaintDao complaintDao;

	public ComplaintDao getComplaintDao() {
		return complaintDao;
	}
	@Resource(name = "complaintDao")
	public void setComplaintDao(ComplaintDao complaintDao) {
		this.complaintDao = complaintDao;
	}
	public Pager<Complaint> getUnsolvedComplaint() {
		return complaintDao.showUnsolvedComplaint();
	}
	public Complaint getById(int id) {
		return complaintDao.load(id);
	}
	public boolean updateComplaint(Complaint complaint) {
		Complaint comp=complaintDao.load(complaint.getId());
		comp.setStatus(complaint.getStatus());
		return complaintDao.update(comp);
	}
	public List<Complaint> getCompByFromId(int from_id) {
		return complaintDao.getComByFrom_id(from_id);
	}
	public boolean addComp(Complaint complaint) {
		return complaintDao.add(complaint);
	}
}
