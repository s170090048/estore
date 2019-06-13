package com.jason.estore.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.jason.estore.domain.User;
import com.jason.estore.utils.ActiveCodeUtils;
import com.jason.estore.utils.DataSourceUtils;
import com.jason.estore.utils.Md5Utils;

public class UserDao {
	//注册操作
	//注册的用户默认设置为user。userstatus默认为0，代表未激活
	//激活码用uuid生成
	public void addUser(User user) throws SQLException{
		String sql="insert into users values(null,?,?,?,?,?,?,?,null)";
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
              //密码用MD5加密
			runner.update(sql,user.getUsername(),Md5Utils.md5(user.getPassword()),user.getNickname(),user.getEmail(),"user",0,user.getActivecode());
	
		
	}
        //根据激活码查找用户
	public User findUserByActiveCode(String activecode) throws SQLException {
		// TODO Auto-generated method stub
		String sql="select * from users where activecode=?";
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		return runner.query(sql, new BeanHandler<User>(User.class), activecode);

	}
	public void activeUser(String activecode) throws SQLException {
		String sql="update users set state=1 where activecode=?";
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		runner.update(sql,activecode);
		
		
	}

}
