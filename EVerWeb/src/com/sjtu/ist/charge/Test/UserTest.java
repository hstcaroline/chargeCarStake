package com.sjtu.ist.charge.Test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sjtu.ist.charge.Model.User;
import com.sjtu.ist.charge.Service.UserService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class UserTest {

	private UserService userService;
	
	@Resource(name="userService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Test
    public void testRegister() {
         User user = new User("黄顺婷", "123", "1", 1, 1, 1.3);
         userService.register(user);
    }

    @Test
    public void testLoad() {
        User user = userService.load(1);
        System.out.println("userName is :" + user.getName());
    }
    
    @Test
    public void testDelete() {
    	System.out.println(userService.deleteUser(1));
    }
    
    @Test
    public void testUpdate() {
    	User u = userService.load(1);
    	u.setTelephone("123");
    	System.out.println(userService.updateUser(u));
    	System.out.println(userService.updatepsw("123456"));
    }
    
    @Test
    public void testValidLogin() {
    	User u = userService.load(1);
    	System.out.println(userService.validLogin(u.getUsername(), u.getPassword()));
    	System.out.println(userService.validLogin(u.getUsername(), "1"));
    }
}
