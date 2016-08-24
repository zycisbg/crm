package com.zyc.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zyc.crm.bean.CustomerActivity;
import com.zyc.crm.mapper.ActivityMapper;
import com.zyc.crm.model.Page;

@Service
@Transactional
public class ActivityService {

	@Autowired
	private ActivityMapper activityMapper;

	
	public Page<CustomerActivity> getPage(Integer customerId, String pageNoStr) {
		
		long totalElements = activityMapper.getTotalElement(customerId);
		
		Page<CustomerActivity> page = new Page<>(pageNoStr, (int)totalElements);
		
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		
		int endIndex = page.getPageSize() + fromIndex;
		
		List<CustomerActivity> content = activityMapper.getContent(customerId,fromIndex,endIndex);
		
		page.setContent(content);
		
		
		return page;
	}


	//添加活动
	public void save(CustomerActivity activity) {

		activityMapper.save(activity);
	}


	//更新活动
	public void update(CustomerActivity activity) {

		activityMapper.update(activity);
	}


	//通过id查看活动
	public CustomerActivity getActivity(Integer id) {
		
		
		return activityMapper.getActivity(id);
	}


	//删除活动记录
	public void delete(Integer activityId) {
		activityMapper.delete(activityId);
	}
	
}
