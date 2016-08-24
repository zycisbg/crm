package com.zyc.crm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zyc.crm.bean.Contact;
import com.zyc.crm.bean.Customer;
import com.zyc.crm.bean.SalesChance;
import com.zyc.crm.mapper.CustomerMapper;
import com.zyc.crm.model.Page;
import com.zyc.crm.model.PropertyFilter;
import com.zyc.crm.util.CRMUtils;
@Transactional
@Service
public class CustomerServiceImpl {

	@Autowired
	private CustomerMapper customerMapper;


	public Page<SalesChance> getPage(String pageNoStr,
			Map<String, Object> params) {

		// 把handler传来的参数转为propertyFilter的集合
		List<PropertyFilter> filters = CRMUtils
				.parseHandlerParamsToPropertyFilters(params);
		// 把propertyFilter集合转为mybatis可以用的参数
		Map<String, Object> mybatisParams = CRMUtils
				.parsePropertyFiltersToMyBatisParmas(filters);
		

		// 通过mabtaisParams 查询 带条件的总的记录数
		long totalElements = customerMapper
				.getTotalElementsByMap(mybatisParams);

		// 获取总个数后创建page对象(为了约束pageNo)
		Page<SalesChance> page = new Page<>(pageNoStr, (int) totalElements);

		// 2.获取当前页面的List
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = page.getPageSize() + fromIndex;

		// 把分页所需要的参数传入mabatisparams中查询当前页面的list
		mybatisParams.put("fromIndex", fromIndex);
		mybatisParams.put("endIndex", endIndex);
		
		

		List<SalesChance> content = customerMapper
				.getContentByMap(mybatisParams);
		page.setContent(content);

		return page;
	}

	//获取全部的地区
	public List<String> getAllregions() {
		return customerMapper.getAllRegions();
	}

	//获取全部的等级
	public List<String> getAlllevels() {
		return customerMapper.getAllLevels();
	}

	//通过id来获取Customer对象的所有属性
	public Customer getCustomerById(Integer id) {
		return customerMapper.getCustomerById(id);
	}

	public List<String> getSatisfies() {
		return customerMapper.getSatisfies();
	}

	public List<String> getCredits() {
		// TODO Auto-generated method stub
		return customerMapper.getCredits();
	}

	//在contacts表中通过外键获取所有的managers
	public List<Contact> getAllContact(Integer id) {
		return customerMapper.getAllContact(id);
	}

	//更新customer
	public void update(Customer customer) {

		customerMapper.update(customer);
	}

	//删除customer
	public void delete(Integer customerId) {

		customerMapper.delete(customerId);
	}
}
