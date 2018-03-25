package com.pibigstar;

import com.pibigstar.bean.User;
import com.pibigstar.mapper.UserMapper;
import com.pibigstar.session.MySqlSession;

public class Test {
	
	public static void main(String[] args) {
		MySqlSession session = new MySqlSession();
		UserMapper userMapper = session.getMapper(UserMapper.class);
		User user = userMapper.getUserById("27");
		System.out.println(user);
		
	}

}
