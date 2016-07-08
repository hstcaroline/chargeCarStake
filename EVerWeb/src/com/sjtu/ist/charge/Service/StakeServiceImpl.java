package com.sjtu.ist.charge.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sjtu.ist.charge.Dao.OrderDao;
import com.sjtu.ist.charge.Dao.StakeDao;
import com.sjtu.ist.charge.Model.Pager;
import com.sjtu.ist.charge.Model.Stake;
import com.sjtu.ist.charge.Model.Order;


@Service("stakeService")
public class StakeServiceImpl implements StakeService{
	private StakeDao stakeDao;
	private OrderDao orderDao;
	
	@Resource(name="stakeDao")
	public void setStakeDao(StakeDao stakeDao) {
		this.stakeDao = stakeDao;
	}

	@Resource(name="orderDao")
	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}
	
	public Pager<Stake> getStakeByPage() {
		return stakeDao.getStakeByPage();
	}


	public Pager<Stake> getUncheckedStakeByPage() {
		return stakeDao.getUncheckedStakeByPage();
	}

	public Boolean update(Stake stake) {
		Stake s = stakeDao.load(stake.getId());
		
		s.setLongitude(stake.getLongitude());
		s.setLatitude(stake.getLatitude());
		s.setAvailableStime(stake.getAvailableStime());
		s.setAvailableEtime(stake.getAvailableEtime());
		s.setDescription(stake.getDescription());
		s.setQrCode(stake.getQrCode());
		s.setType(stake.getType());
		s.setStatus(stake.getStatus());
		
		return stakeDao.update(s);
	}

	public Stake load(int id) {
		return stakeDao.load(id);
	}

	public List<Stake> showStakeByStatus(int status) {
		return stakeDao.getStakeByStatus(status);
	}

	public List<Stake> showAllStakes() {
		return stakeDao.getAllStakes();
	}

	public boolean deleteStake(int id) {
		return stakeDao.delete(id);
	}

	public boolean deleteUncheckedStake(int id) {
		Stake stake = load(id);
		if (stake.getStatus() != 4) {
			return false;
		}
		return stakeDao.delete(stake);
	}

	public boolean stakeRegister(Stake stake) {
		return stakeDao.add(stake);
	}

	public List<Stake> getStakesByOwnerId(int userId) {
		return stakeDao.getStakeByOwnerId(userId);
	}

	public List<Stake> getByOwnerAndStatus(int ownerId, int status) {
		return stakeDao.getStakeByOwnerAndStatus(ownerId, status);
	}

	public boolean addStakeOrder(int stakeId) {
		boolean success = false;
		try {
			success = stakeDao.updateOrder(stakeId);
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		}
		return success;
	}
	
	public boolean addStakeOrder(String stime,String etime,int stakeId) {
		Stake stake = stakeDao.load(stakeId);
		if (stake.getStatus() == 0 || isAvailableInOrder(stime, etime, stakeId)) {
			
		}
		return false;
	}

	public List<Stake> getAvailableStake(String stime, String etime) {
		String hqla = "from Stake s where s.status = 0 and s.availableStime < ? and s.availableEtime > ?";
		String hqlb = "from Stake s where s.status > 5 and s.availableStime < ? and s.availableEtime > ?";
		try {
			String[] sts = stime.split("\\s+");
			String[] ets = etime.split("\\s+");
			Time st = Time.valueOf(sts[1]);
			Time et = Time.valueOf(ets[1]);
			Object[] object = {st,et};
			List<Stake> stakes = stakeDao.getAfterFilter(hqla, object);
			List<Stake> backup = stakeDao.getAfterFilter(hqlb, object);
			List<Stake> ostakes = getBeyondOrderStake(stime, etime,backup);
			stakes.addAll(ostakes);
			return stakes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private List<Stake> getBeyondOrderStake(String stime,String etime,List<Stake> backup) {
		return null;
	}
	
	private boolean isAvailableInOrder(String stime,String etime,int stakeId) {
		
		String hql = "from Order o where o.stake.id = ? and o.type = 0 order by o.startTime";
		Object[] object = {stakeId};
		List<Order> orders = orderDao.getAfterFilter(hql, object);
		Timestamp st = Timestamp.valueOf(stime);  //"2015-12-31 16:41:01.0"
		Timestamp et = Timestamp.valueOf(etime);  //"2015-12-31 16:51:01.0"
		System.out.println(st + " " + et);
		boolean isAvailable = false;		
		int size = orders.size();
		int i = 0;
		for (;i < size-1;i++) {
			Order first = orders.get(i);
			Order second = orders.get(i+1);
			if (first.getEndTime().before(st) && second.getStartTime().after(et)) {
				isAvailable = true;
				break;
			}
		}
		if (i == size-1) {
			Order last = orders.get(size-1);
			isAvailable = st.after(last.getEndTime()); 
		}
		System.out.println("isavailable: " + isAvailable);
		return size == 0 ? true : isAvailable;
	}
	
	public boolean isAvailable(String stime, String etime, int stakeId) {
		Stake stake = stakeDao.load(stakeId);
		int status = stake.getStatus();
		String[] stimes = stime.split("\\s+");
		String[] etimes = etime.split("\\s+");
		Time st = Time.valueOf(stimes[1]);  //"16:41:01.0"
		Time et = Time.valueOf(etimes[1]);  //"16:51:01.0"
		//判断是否在车桩可用的时间内
		if (status != 0 || st.before(stake.getAvailableStime()) || et.after(stake.getAvailableEtime())) {
			return false;
		}
		//判断当前车桩在时间内是否被预定
		//return status == 0 ? true : (status < 6 ? false : isAvailableInOrder(stime, etime, stakeId));
		return isAvailableInOrder(stime, etime, stakeId);
	}

	public Order getAvaiableOrder(Timestamp stime, Timestamp etime, int stakeId, int userId) {
		//查看当前用户的所有未进行的预约单
		List<Order> orders = orderDao.getByUserIdAndStatus(userId, 0);
		//如果当前用户无可用预约
		if(orders.size()==0){
			return null;
		}
		for (Order order : orders) {
			Timestamp orderStartTime = order.getStartTime();
			Timestamp orderEndTime = order.getEndTime();
			//如果想使用的开始时间在预约的时间段内，且该预约是当前用户的预约，说明当前用户可以使用
			if(!stime.before(orderStartTime) && stime.before(orderEndTime) && order.getUser().getId() == userId){
				return order;
			}
		}
		return null;
		
	}
	
}
