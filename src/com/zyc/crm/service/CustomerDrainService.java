package com.zyc.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zyc.crm.mapper.CustomerDrainMapper;

@Service
public class CustomerDrainService {

	@Autowired
	private CustomerDrainMapper customerDrainMapper;
	
	@Transactional
	public void callCheckDrainProcedure(){
		customerDrainMapper.callCheckDrainProcedure();
	}
}
