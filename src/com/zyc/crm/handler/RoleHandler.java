package com.zyc.crm.handler;

import java.util.List;
import java.util.Map;






import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zyc.crm.bean.Authority;
import com.zyc.crm.bean.Role;
import com.zyc.crm.bean.SalesChance;
import com.zyc.crm.model.Page;
import com.zyc.crm.service.RoleService;

@Controller
public class RoleHandler {

	@Autowired
	private RoleService roleService;
	
	@RequestMapping("/role/assign")
	public String updateAuthorities(Role role){
		
		
		roleService.update(role);
		
		return "redirect:/role/list";
	}
	
	@RequestMapping("/role/assign/{id}")
	public String toAssignUI(@PathVariable("id") Integer id,Map<String,Object> map){
		
		//通过id获取角色的所有属性
		Role role = roleService.getRole(id);
		
		//获取所有的父权限包括其中的子权限
		List<Authority> parentAuthorities = roleService.getParentAuthorities();
		
		//role.setAuthorities(parentAuthorities);
		
		map.put("role", role);
		map.put("parentAuthorities",parentAuthorities );
		//获取所有的子权限
		/*List<Authority> subAuthorities = roleService.getSubAuthorities();
		
		//获取所有的父权限
		List<Authority> parentAuthorities = roleService.getParentAuthorities();
		for (Authority authority : parentAuthorities) {
			authority.setSubAuthorities(subAuthorities);
		}*/
		
		return "role/assign";
	}
	
	@RequestMapping("/role/delete/{id}")
	public String delete(@PathVariable("id") Integer id){
		roleService.delete(id);
		
		return "redirect:/role/list";
	}
	
	@RequestMapping("/role/save")
	public String save(Role role){
		
		roleService.save(role);
		
		return "redirect:/role/list";
	}
	
	@RequestMapping("/role/input")
	public String toSaveUI(Map<String,Object> map){
		
		map.put("role", new Role());
		
		return "role/input";
	}
	
	@RequestMapping("/role/list")
	public String list(
			@RequestParam(value = "pageNo", required = false) String pageNo,
			Map<String, Object> map) {

		Page<SalesChance> page = roleService.getPage(pageNo);

		map.put("page", page);

		
		
		return "role/list";
	}
}
