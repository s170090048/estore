package com.jason.estore.dao;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;

import com.jason.estore.domain.User;
import com.jason.estore.utils.ActiveCodeUtils;
import com.jason.estore.utils.DataSourceUtils;

public class UserDao {
	//注册操作
	//注册的用户默认设置为user。userstatus默认为0，代表未激活
	//激活码用uuid生成
	public void addUser(User user) throws SQLException{
		String sql="insert into users values(null,?,?,?,?,?,?,?,null)";
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());

			runner.update(sql,user.getUsername(),user.getPassword(),user.getNickname(),user.getEmail(),"user",0,user.getActivecode());
	
		
	}

}
