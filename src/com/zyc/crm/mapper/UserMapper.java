package com.zyc.crm.mapper;

import org.apache.ibatis.annotations.Param;

import com.zyc.crm.bean.User;

public interface UserMapper {

	User getUserByNameAndPassword(@Param("name") String name,
			@Param("password") String password);

	User getUserByName(@Param("name")String name);

}
