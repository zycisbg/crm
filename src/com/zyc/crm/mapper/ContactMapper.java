package com.zyc.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zyc.crm.bean.Contact;
import com.zyc.crm.bean.Customer;

public interface ContactMapper {

	//获取当前页面的集合
	List<Contact> getContent(@Param("id") Integer id,
			@Param("fromIndex") int fromIndex, @Param("endIndex") int endIndex);

	//获取总的个数
	long getTotalElements(@Param("id") Integer id);

	//获取当前联系人的客户对象
	Customer getManager(@Param("id") Integer id);

	//添加联系人
	void save(Contact contact);

	//通过id获取单个联系人
	Contact getContactById(@Param("id")Integer id);

	//更新对象
	void update(Contact contact);

	//删除联系人
	void detele(@Param("contactId") Integer contactId);

}
