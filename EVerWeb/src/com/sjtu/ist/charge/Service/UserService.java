package com.sjtu.ist.charge.Service;

import com.sjtu.ist.charge.Model.Pager;
import com.sjtu.ist.charge.Model.User;


public interface UserService {
	public void register(User user);

	public User load(int id);

	// 验证登录信息
	public User validLogin(String userName, String passWord);

	// 修改用户密码
	public boolean updatepsw(String password);

	/**
	 * 分页查询用户列表
	 * 
	 * @return 分页后的数据
	 */
	public Pager<User> pageCarUser();

	// 根据用户姓名查询用户
	public Pager<User> searchByName(String name);

	/**
	 * 根据id删除用户对象
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteUser(int id);

	/**
	 * 更新car User信息
	 * 
	 * @return
	 */
	public boolean updateUser(User u);
	
	/**
	 * 根据手机号更新用户密码
	 * @param telephone
	 * @param password
	 * @return
	 */
	public boolean updatePswByTel(String telephone,String password);
	
	/**
	 * 根据手机号查找用户
	 * @param telephone
	 * @return
	 */
	public User getUserByTel(String telephone);

    /**
     * 用户通过手机号和密码登录
     * 
     * @param telephone
     * @param psw
     * @return
     */
    public User getUserByTelAndPsw(String telephone, String psw);
}
