package com.zyc.crm.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zyc.crm.bean.CustomerDrain;
import com.zyc.crm.bean.SalesChance;
import com.zyc.crm.mapper.CustomerMapper;
import com.zyc.crm.mapper.DrainMapper;
import com.zyc.crm.model.Page;
import com.zyc.crm.model.PropertyFilter;
import com.zyc.crm.util.CRMUtils;

@Service
@Transactional
public class DrainService {

	@Autowired
	private DrainMapper drainMapper;
	
	@Autowired
	private CustomerMapper customerMapper;

	public Page<SalesChance> getPage(String pageNo, Map<String, Object> params) {
		// 把handler传来的参数转为propertyFilter的集合
		List<PropertyFilter> filters = CRMUtils
				.parseHandlerParamsToPropertyFilters(params);
		// 把propertyFilter集合转为mybatis可以用的参数
		Map<String, Object> mybatisParams = CRMUtils
				.parsePropertyFiltersToMyBatisParmas(filters);

		// 通过mabtaisParams 查询 带条件的总的记录数
		long totalElements = drainMapper
				.getTotalElementsByMap(mybatisParams);

		// 获取总个数后创建page对象(为了约束pageNo)
		Page<SalesChance> page = new Page<>(pageNo, (int) totalElements);

		// 2.获取当前页面的List
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = page.getPageSize() + fromIndex;

		// 把分页所需要的参数传入mabatisparams中查询当前页面的list
		mybatisParams.put("fromIndex", fromIndex);
		mybatisParams.put("endIndex", endIndex);

		List<SalesChance> content = drainMapper
				.getContentByMap(mybatisParams);
		page.setContent(content);

		return page;
	}

	public CustomerDrain getCustomerDrainById(Integer id) {
		return drainMapper.getCustomerDrainById(id);
	}

	public void save(Integer drainId,String delay) {
		drainMapper.save(drainId,delay);
		
	}

	//确认流失
	public void confirmDrain(CustomerDrain customerDrain) {
		customerDrain.setDrainDate(new Date());
		drainMapper.updateStatus(customerDrain);
		customerMapper.updateStatus(customerDrain);
	}
}
