package com.jason.estore.service;

import java.sql.SQLException;

import com.jason.estore.dao.UserDao;
import com.jason.estore.domain.User;
import com.jason.estore.exception.RegistException;

public class UserService {
	public void regist(User user) throws RegistException    {
		UserDao dao=new UserDao();
		try {
			dao.addUser(user);
			//向注册成功的的用户发送一封激活邮件
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//抛一个自定义异常
			throw new RegistException("注册失败");
		}
	
		
	}

		
		

}
