package com.zyc.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zyc.crm.bean.Contact;
import com.zyc.crm.bean.Customer;
import com.zyc.crm.bean.CustomerDrain;
import com.zyc.crm.bean.SalesChance;

public interface CustomerMapper {

	//获取带查询条件的总记录数
	long getTotalElementsByMap(Map<String, Object> mybatisParams);

	//获取带查询条件的当前页面的对象集合
	List<SalesChance> getContentByMap(Map<String, Object> mybatisParams);

	//获取全部的地区
	List<String> getAllRegions();

	//获取全部的等级
	List<String> getAllLevels();

	//通过customer id来获取对象的所有属性
	Customer getCustomerById(@Param("id") Integer id);

	List<String> getSatisfies();

	List<String> getCredits();

	//在contacts表中通过外键获取所有的managers
	List<Contact> getAllContact(@Param("id")Integer id);

	//更新customer
	void update(Customer customer);

	//删除
	void delete(@Param("id")Integer customerId);

	//更新状态
	void updateStatus(CustomerDrain customerDrain);

}
