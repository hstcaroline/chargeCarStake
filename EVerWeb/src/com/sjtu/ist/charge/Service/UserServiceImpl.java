package com.sjtu.ist.charge.Service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sjtu.ist.charge.Dao.UserDao;
import com.sjtu.ist.charge.Model.Pager;
import com.sjtu.ist.charge.Model.User;


@Service("userService")
public class UserServiceImpl implements UserService {

	private UserDao userDao;

	public UserDao getUserDao() {
		return userDao;
	}

	@Resource(name = "userDao")
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void register(User user) {
		userDao.add(user);
	}

	public User load(int id) {
		return userDao.load(id);
	}

	public User validLogin(String userName, String passWord) {
		User u = userDao.findUserByNameAndPass(userName, passWord);
		if (u != null) {
			return u;
		}
		return null;
	}

	public boolean updatepsw(String password) {
		if (userDao.updatepsw(password))
			return true;
		return false;
	}

	public Pager<User> pageCarUser() {
		Pager<User> users = userDao.AllUserBypage();
		return users;
	}

	public Pager<User> searchByName(String name) {
		return userDao.searchByName(name);
	}

	public boolean deleteUser(int id) {
		return userDao.delete(id);
	}

	public boolean updateUser(User user) {
		User u = userDao.load(user.getId());
		u.setName(user.getName());
		u.setTelephone(user.getTelephone());
		u.setFaith(user.getFaith());
		u.setRemaining(user.getRemaining());
		u.setHaveStake(user.getHaveStake());
		return userDao.update(u);
	}

	public boolean updatePswByTel(String telephone, String password) {
		return userDao.updatePswByTel(telephone, password);
	}

	public User getUserByTel(String telephone) {
		return userDao.getUserByTel(telephone);
	}

    public User getUserByTelAndPsw(String telephone, String psw) {
        User user = userDao.getUserByTel(telephone);
        if (user.getPassword().equals(psw)) {
            return user;
        }
        return null;
    }
}
