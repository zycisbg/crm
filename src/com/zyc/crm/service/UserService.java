package com.zyc.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zyc.crm.bean.User;
import com.zyc.crm.mapper.UserMapper;

@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserMapper userMapper;

	public User getUserByNameAndPassword(String name, String password) {
		
		User user = userMapper.getUserByNameAndPassword(name,password);
		
		if(user!=null &&user.getEnabled()==1){
			return user;
		}
		
		return null;
	}

	public User getUserByName(String username) {
		return userMapper.getUserByName(username);
	}

}
