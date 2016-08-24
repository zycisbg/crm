package com.zyc.crm.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zyc.crm.bean.Customer;
import com.zyc.crm.bean.CustomerService;
import com.zyc.crm.bean.User;
import com.zyc.crm.mapper.ServiceMapper;

@Service
@Transactional
public class ServiceService extends BaseService<CustomerService> {

	@Autowired
	private ServiceMapper serviceMapper;

	public List<String> getServiceType() {
		return serviceMapper.getgetServiceType();
	}

	public List<Customer> getCustomers() {
		// TODO Auto-generated method stub
		return serviceMapper.getCusomers();
	}

	public void save(CustomerService customerService) {

		serviceMapper.save(customerService);
	}

	public List<User> getUsers() {
		return serviceMapper.getUsers();
	}

	public void delete(Integer id) {

		serviceMapper.delete(id);
	}

	public void allot(long serviceId, long allotId) {

		Date date = new Date();
		
		serviceMapper.allot(date,serviceId,allotId);
	}

	public CustomerService getServiceById(long id) {
		// TODO Auto-generated method stub
		return serviceMapper.getServiceById(id);
	}

	public void updateDeal(CustomerService customerService) {
		// TODO Auto-generated method stub
		serviceMapper.updateDeal(customerService);
	}

	public void updatFeedbacke(CustomerService customerService) {

		serviceMapper.updateFeedbacke(customerService);
	}
}
