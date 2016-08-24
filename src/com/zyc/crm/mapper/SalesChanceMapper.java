package com.zyc.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zyc.crm.bean.SalesChance;
import com.zyc.crm.bean.User;

public interface SalesChanceMapper {

	// 获取总元素
	long getTotalElements(@Param("createBy") User createBy,
			@Param("status") int status);

	// 获取分页的List
	List<SalesChance> getContent(@Param("createBy") User createBy,
			@Param("status") int status, @Param("fromIndex") int fromIndex,
			@Param("endIndex") int endIndex);

	// 添加销售机会
	void save(SalesChance salesChance);

	// 删除销售机会
	void delete(Integer id);

	// 通过id来获取销售机会
	SalesChance getChance(Integer id);

	// 修改销售机会
	void update(SalesChance chance);

	// 获取带查询条件的总个数
	long getTotalElementsByQuery(@Param("createBy") User careateBy,
			@Param("status") int status, @Param("custName") String custName,
			@Param("title") String title, @Param("contact") String contact);

	// 获取带查询条件的当前页面集合
	List<SalesChance> getContentByQuery(@Param("createBy") User createBy,
			@Param("status") int status, @Param("fromIndex") int fromIndex,
			@Param("endIndex") int endIndex,
			@Param("custName") String custName, @Param("title") String title,
			@Param("contact") String contact);

	// 通过传入map来查询中的记录数
	List<SalesChance> getContentByMap(Map<String, Object> mybatisParams);

	// 通过传入map来查询当前页面的List
	long getTotalElementsByMap(Map<String, Object> mybatisParams);

	// 通过创建人的id来查询创建人的信息
	User getCreateBy(@Param("id") Long id);

	// 为了表单回显指派给谁。查询所有的user对象
	List<User> getAllUser();

	// 更新指派时间和 指派给谁,销售状态直接在salesChanceMapper中直接设置
	void updateDesigneeDateAndDesignee(SalesChance salesChance);

	// 更新销售机会的状态
	void updateChanceStatus(@Param("id") Integer id,@Param("status") Integer status);

	//完成销售计划后 向customers表中添加三个字段
	void insertIntoCustomers(@Param("custName")String custName, @Param("uuid")String uuid, @Param("state")String state);
	
	//通过customer表中的NO来获取id
	long getCustomerIdByNo(@Param("uuid")String uuid);

	//完成销售计划后 向contacts表中添加三个字段
	void insertIntoContacts(@Param("contactName")String contactName,@Param("contactTel") String contactTel,
			@Param("customerId")long customerId);

}
