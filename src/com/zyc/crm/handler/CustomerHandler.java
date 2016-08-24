package com.zyc.crm.handler;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.zyc.crm.bean.Contact;
import com.zyc.crm.bean.Customer;
import com.zyc.crm.bean.SalesChance;
import com.zyc.crm.model.Page;
import com.zyc.crm.service.CustomerServiceImpl;
import com.zyc.crm.util.CRMUtils;

@Controller
public class CustomerHandler {

	@Autowired
	private CustomerServiceImpl customerSerciceImpl;
	
	@RequestMapping("/customer/delete-ajax")
	@ResponseBody
	public String deleteCustomer(@RequestParam("id") Integer customerId){
		
		try {
			customerSerciceImpl.delete(customerId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "2";
		}
		return "1";
	}

	//更新客户信息
	@RequestMapping("/customer/update")
	public String updateCustomer(Customer customer) {

		customerSerciceImpl.update(customer);
		
		return "redirect:/customer/showList";
	}

	// 跳转到更新页面
	@RequestMapping("/customer/create/{id}")
	public String toUpdateUI(@PathVariable("id") Integer id,
			Map<String, Object> map) {
		// 获取customer对象
		Customer customer = customerSerciceImpl.getCustomerById(id);
		map.put("customer", customer);

		// 通过customer的id(contact的外键)获取所有的contact
		List<Contact> managers = customerSerciceImpl.getAllContact(id);
		map.put("managers", managers);

		// 获取地区，客户等级，满意度，信用度
		List<String> regions = customerSerciceImpl.getAllregions();
		List<String> levels = customerSerciceImpl.getAlllevels();

		List<String> satisfies = customerSerciceImpl.getSatisfies();
		List<String> credits = customerSerciceImpl.getCredits();

		map.put("regions", regions);
		map.put("levels", levels);
		map.put("satisfies", satisfies);
		map.put("credits", credits);

		return "customer/input";
	}

	/**
	 * 查询所有的客户，并且连表查询客户经理
	 */
	@RequestMapping("/customer/showList")
	public String showList(
			@RequestParam(value = "pageNo", required = false) String pageNoStr,
			Map<String, Object> map, HttpServletRequest request) {

		Map<String, Object> params = WebUtils.getParametersStartingWith(
				request, "search_");

		String queryString = CRMUtils.encodeParamsToQueryString(params);

		map.put("queryString", queryString);

		Page<SalesChance> page = customerSerciceImpl.getPage(pageNoStr, params);

		map.put("page", page);

		// 获取地区，客户等级，和状态
		List<String> regions = customerSerciceImpl.getAllregions();
		List<String> levels = customerSerciceImpl.getAlllevels();

		map.put("regions", regions);
		map.put("levels", levels);

		return "customer/list";
	}
}
