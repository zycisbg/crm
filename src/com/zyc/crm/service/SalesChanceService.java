package com.zyc.crm.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zyc.crm.bean.SalesChance;
import com.zyc.crm.bean.User;
import com.zyc.crm.mapper.SalesChanceMapper;
import com.zyc.crm.model.Page;
import com.zyc.crm.model.PropertyFilter;
import com.zyc.crm.model.PropertyFilter.MathType;
import com.zyc.crm.util.CRMUtils;

@Service
public class SalesChanceService {

	@Autowired
	private SalesChanceMapper salesChanceMapper;

	/**
	 * 根据当前页码查询当前页码的List 需要从数据库中查询所有记录的个数 和分页的当页集合
	 * 
	 * @param pageNoStr
	 * @param careateBy
	 * @param status
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<SalesChance> getPage(String pageNoStr, User careateBy,
			int status) {
		// 获取总的记录数
		long totalElements = salesChanceMapper.getTotalElements(careateBy,
				status);

		Page<SalesChance> page = new Page<SalesChance>(pageNoStr,
				(int) totalElements);

		// 获取当前页面的List
		// 当前页面开始的索引
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		// 当前页面结束的索引
		int endIndex = page.getPageSize() + fromIndex;

		List<SalesChance> content = salesChanceMapper.getContent(careateBy,
				status, fromIndex, endIndex);

		page.setContent(content);

		return page;
	}

	// 添加销售机会
	@Transactional
	public void save(SalesChance salesChance) {

		salesChanceMapper.save(salesChance);

	}

	// 删除销售机会
	@Transactional
	public void delete(Integer id) {

		salesChanceMapper.delete(id);
	}

	// 通过id来获取机会对象
	@Transactional(readOnly = true)
	public SalesChance getChance(Integer id) {

		return salesChanceMapper.getChance(id);
	}

	// 修改销售机会
	@Transactional
	public void update(SalesChance chance) {

		salesChanceMapper.update(chance);
	}

	// 带查询条件的获取页面
	@Transactional
	public Page<SalesChance> getPageByQuery(String pageNoStr, User careateBy,
			int status, String custName, String title, String contact) {
		// 获取带查询条件的总的记录数
		long totalElements = salesChanceMapper.getTotalElementsByQuery(
				careateBy, status, custName, title, contact);

		Page<SalesChance> page = new Page<SalesChance>(pageNoStr,
				(int) totalElements);

		// 获取当前页面的List
		// 当前页面开始的索引
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		// 当前页面结束的索引
		int endIndex = page.getPageSize() + fromIndex;

		// 为了使用like语句，在这里拼串
		if (custName != null) {
			custName = "'%" + custName + "%'";
		}
		if (title != null) {
			title = "'%" + title + "%'";
		}
		if (contact != null) {
			contact = "'%" + contact + "%'";
		}

		// 获取带查询条件当前页面的集合
		List<SalesChance> content = salesChanceMapper.getContentByQuery(
				careateBy, status, fromIndex, endIndex, custName, title,
				contact);

		page.setContent(content);

		return page;
	}

	/**
	 * 查询带条件的分页。 分页的参数在paramsMap中
	 * 整体思路：先把handler传来的params通过属性过滤器转为PropertyFilter的集合 在把这个集合转为
	 * mabatis可以用的参数map
	 * 
	 * @param pageNoStr
	 * @param params
	 * @return
	 */
	@Transactional
	public Page<SalesChance> getPage(String pageNoStr,
			Map<String, Object> params) {
		// 1查询总的记录数，需要先转换handler传来的参数

		// 把handler传来的参数转为propertyFilter的集合
		List<PropertyFilter> filters = CRMUtils
				.parseHandlerParamsToPropertyFilters(params);
		// 把propertyFilter集合转为mybatis可以用的参数
		Map<String, Object> mybatisParams = CRMUtils
				.parsePropertyFiltersToMyBatisParmas(filters);

		// 通过mabtaisParams 查询 带条件的总的记录数
		long totalElements = salesChanceMapper
				.getTotalElementsByMap(mybatisParams);

		// 获取总个数后创建page对象(为了约束pageNo)
		Page<SalesChance> page = new Page<>(pageNoStr, (int) totalElements);

		// 2.获取当前页面的List
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = page.getPageSize() + fromIndex;

		// 把分页所需要的参数传入mabatisparams中查询当前页面的list
		mybatisParams.put("fromIndex", fromIndex);
		mybatisParams.put("endIndex", endIndex);

		List<SalesChance> content = salesChanceMapper
				.getContentByMap(mybatisParams);
		page.setContent(content);

		return page;
	}

	// 通过创建人的id来查询创建人的信息
	@Transactional
	public User getCreateBy(Long id) {
		return salesChanceMapper.getCreateBy(id);
	}

	// 为了表单回显指派给谁。查询所有的user对象
	@Transactional
	public List<User> getAllUser() {
		return salesChanceMapper.getAllUser();
	}

	// 更新指派时间和 指派给谁,销售状态直接在salesChanceMapper中直接设置
	@Transactional
	public void updateDesigneeDateAndDesignee(SalesChance salesChance) {
		salesChanceMapper.updateDesigneeDateAndDesignee(salesChance);
	}

	// 更新销售机会的状态
	@Transactional
	public void updateChanceStatus(Integer id, Integer status) {

		salesChanceMapper.updateChanceStatus(id, status);
	}

	// 开发完成
	@Transactional
	public void finish(Integer id, Integer status) {
		// 开发成功后，需要先把销售机会的状态设置为3
		
		salesChanceMapper.updateChanceStatus(id, status);

		// 查询当前销售机会的所有值
		SalesChance chance = salesChanceMapper.getChance(id);

		// 向数据库中customers插入一条数据(name，no（随机字符串） 和 state（正常）)
		String custName = chance.getCustName();
		String uuid = UUID.randomUUID().toString();
		String state = "正常";
		salesChanceMapper.insertIntoCustomers(custName,uuid,state);

		// 向数据库中contacts中插入一条数据(name、tel、customer_id)
		String contactName = chance.getContact();
		String contactTel = chance.getContactTel();
		//通过uuid(NO)来查询customer的id
		long customerId = salesChanceMapper.getCustomerIdByNo(uuid);
		
		salesChanceMapper.insertIntoContacts(contactName,contactTel,customerId);

	}
}
