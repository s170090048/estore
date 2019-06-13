package com.jason.estore.service;

import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.jason.estore.dao.UserDao;
import com.jason.estore.domain.User;
import com.jason.estore.exception.ActiveCodeException;
import com.jason.estore.exception.RegistException;
import com.jason.estore.utils.MailUtils;

public class UserService {
	public void regist(User user) throws RegistException    {
		UserDao dao=new UserDao();
		try {
			dao.addUser(user);
			String emailMsg="注册成功，请<a href='http://www.estore.com/activeUser?activeCode="+user.getActivecode()+"'>激活</a>，激活码为："+user.getActivecode();
			//向注册成功的的用户发送一封激活邮件
			MailUtils.sendMail(user.getEmail(), emailMsg);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//抛一个自定义异常
			throw new RegistException("注册失败");
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}

	public void activeUser(String activecode) throws ActiveCodeException, SQLException {
		// TODO Auto-generated method stub
		//根据激活码查找用户，判断激活码是否过期
		UserDao userDao=new UserDao();

			User user=userDao.findUserByActiveCode(activecode);
			if(user!=null){
				//进行激活操作
				long time=System.currentTimeMillis()-user.getUpdatetime().getTime();
				if(time<=1000*60){
					//激活
					userDao.activeUser(activecode);
					return;
				}
				else{
                 throw new ActiveCodeException("激活码过期");
				}}
			else{
			 throw new ActiveCodeException("用户不存在");
			}
		
		
	}

		
		

}
