package com.sjtu.ist.charge.Test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sjtu.ist.charge.Dao.UserDao;
import com.sjtu.ist.charge.Model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class UserDaoTest {

	private UserDao userDao;
	
	@Resource(name="userDao")
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Test
	public void testAdd() {
		User user = new User("黄顺婷", "123", "1", 1, 1, 1.3);
		System.out.println(userDao.add(user));
	}
	
	@Test
	public void testLoad() {
		User user = userDao.load(1);
		System.out.println(user);
	}
	
	@Test
	public void testUpdate() {
		User user = userDao.load(1);
		user.setRemaining(0.0);
		System.out.println(userDao.update(user));
	}
	
	@Test
	public void testDelete() {
		User user = userDao.load(1);
		System.out.println(userDao.delete(user));
	}
	
	@Test
	public void testFindUserByNameAndPass() {
		System.out.println(userDao.findUserByNameAndPass("1", "123"));
	}
}
