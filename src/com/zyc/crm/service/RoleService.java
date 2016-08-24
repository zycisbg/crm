package com.zyc.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zyc.crm.bean.Authority;
import com.zyc.crm.bean.Role;
import com.zyc.crm.bean.SalesChance;
import com.zyc.crm.mapper.RoleMapper;
import com.zyc.crm.model.Page;

@Transactional
@Service
public class RoleService {

	@Autowired
	private RoleMapper roleMapper;

	public Page<SalesChance> getPage(String pageNo) {
		long totalElements = roleMapper.getTotalElements();

		// 获取总个数后创建page对象(为了约束pageNo)
		Page<SalesChance> page = new Page<>(pageNo, (int) totalElements);

		// 2.获取当前页面的List
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = page.getPageSize() + fromIndex;

		List<SalesChance> content = roleMapper.getContent(fromIndex,endIndex);
		page.setContent(content);

		return page;
	}

	public void save(Role role) {

		roleMapper.save(role);
	}

	public void delete(Integer id) {

		roleMapper.delete(id);
	}

	public List<Authority> getParentAuthorities() {
		return roleMapper.getParentAuthorities();
	}

	public List<Authority> getSubAuthorities() {
		// TODO Auto-generated method stub
		return roleMapper.getSubAuthorities();
	}

	public Role getRole(Integer id) {
		// TODO Auto-generated method stub
		return roleMapper.getRole(id);
	}

	//更新权限
	//1.删除所有的权限
	//2.批量插入权限
	public void update(Role role) {

		roleMapper.deleteRoleAuth(role);
		roleMapper.updateRole(role);
	}
}
