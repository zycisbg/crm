package com.zyc.crm.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zyc.crm.bean.Customer;
import com.zyc.crm.bean.CustomerService;
import com.zyc.crm.bean.User;

public interface ServiceMapper extends BaseMapper<CustomerService>{

	List<String> getgetServiceType();

	List<Customer> getCusomers();

	void save(CustomerService customerService);

	List<User> getUsers();

	void delete(@Param("id") Integer id);

	void allot(@Param("date") Date date,@Param("serviceId")long serviceId,@Param("allotId") long allotId);

	CustomerService getServiceById(long id);

	void updateDeal(CustomerService customerService);

	void updateFeedbacke(CustomerService customerService);

}
