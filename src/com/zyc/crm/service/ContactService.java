package com.zyc.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zyc.crm.bean.Contact;
import com.zyc.crm.bean.Customer;
import com.zyc.crm.bean.SalesChance;
import com.zyc.crm.mapper.ContactMapper;
import com.zyc.crm.model.Page;

@Service
@Transactional
public class ContactService {

	@Autowired
	private ContactMapper contactMapper;

	public Page<Contact> getPage(String pageNoStr, Integer id) {

		// 通过外键的id去查询当前客户的联系人
		long totalElements = contactMapper.getTotalElements(id);

		Page<Contact> page = new Page<>(pageNoStr, (int) totalElements);

		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		// 当前页面结束的索引
		int endIndex = page.getPageSize() + fromIndex;

		//通过外键去查询当前客户的联系人
		List<Contact> content = contactMapper.getContent(id,fromIndex, endIndex);

		
		page.setContent(content);

		return page;
	}

	//获取当前customer
	public Customer getManager(Integer id) {
		return contactMapper.getManager(id);
	}

	//添加联系人
	public void save(Contact contact) {

		contactMapper.save(contact);
	}

	//
	public Contact getContactById(Integer id) {
		
		
		return contactMapper.getContactById(id);
	}

	//如果有id则更新
	public void update(Contact contact) {

		contactMapper.update(contact);
	}

	//删除联系人
	public void delete(Integer contactId) {
		contactMapper.detele(contactId);
	}

	
}
