package com.sjtu.ist.charge.Dao;

import java.util.List;

import com.sjtu.ist.charge.Model.Pager;
import com.sjtu.ist.charge.Model.User;


public interface UserDao extends BaseDao<User>{

	//根据用户名和密码登录
	public User findUserByNameAndPass(String userName,String passWord);
	//更新密码
	public boolean updatepsw(String password);
	
	/**
	 * 根据姓名模糊查找后分页显示user list
	 * @param name
	 * @return
	 */
	public Pager<User> searchByName(String name);
	/**
	 * 分页查询所有非管理员用户
	 * @return
	 */
	public Pager<User> AllUserBypage();

	//查询语句查询出的记录数量
	public Long count(String hql) ;
	
	//根据hql查询对应页的userlist
	public List<User> SearchPerPage(int pageOffset,int pageSize,String hql);
	
	/**
	 * 根据手机号更新用户密码
	 * @param telephone
	 * @param password
	 * @return
	 */
	public boolean updatePswByTel(String telephone,String password);
	
	/**
	 * 根据返回
	 * @param telephong
	 * @return
	 */
	public User getUserByTel(String telephone);
}
