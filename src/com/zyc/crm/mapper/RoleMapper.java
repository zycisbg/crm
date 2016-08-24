package com.zyc.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zyc.crm.bean.Authority;
import com.zyc.crm.bean.Role;
import com.zyc.crm.bean.SalesChance;

public interface RoleMapper {

	long getTotalElements();

	List<SalesChance> getContent(@Param("fromIndex")int fromIndex,@Param("endIndex") int endIndex);

	void save(Role role);

	void delete(@Param("id")Integer id);

	List<Authority> getParentAuthorities();

	List<Authority> getSubAuthorities();

	Role getRole(@Param("id")Integer id);

	void updateRole(Role role);

	void deleteRoleAuth(Role role);

}
