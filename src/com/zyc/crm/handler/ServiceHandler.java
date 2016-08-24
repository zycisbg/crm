package com.zyc.crm.handler;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.zyc.crm.bean.Customer;
import com.zyc.crm.bean.CustomerService;
import com.zyc.crm.bean.User;
import com.zyc.crm.model.Page;
import com.zyc.crm.service.ServiceService;
import com.zyc.crm.util.CRMUtils;

@Controller
public class ServiceHandler {

	@Autowired
	private ServiceService serviceService;
	
	//查看归档详细
	@RequestMapping("/service/archive")
	public String toArchiveUI(@RequestParam("id") long id,HttpSession session,
			Map<String, Object> map){
		CustomerService service = serviceService.getServiceById(id);
		map.put("service", service);
		User user = (User) session.getAttribute("user");
		map.put("user", user);
		
		return "service/archive/archive";
	}
	
	/**
	 * 服务归档分页
	 */
	@RequestMapping("/service/archive/list")
	public String archiveList(@RequestParam(value = "pageNo", required = false) String pageNo,
			Map<String, Object> map, HttpServletRequest request){
		
		Map<String, Object> params = WebUtils.getParametersStartingWith(
				request, "search_");

		// 把从前台接收到的请求参数值 转为可以用的加载url后边的值
		String queryString = CRMUtils.encodeParamsToQueryString(params);

		map.put("queryString", queryString);

		params.put("EQS_serviceState", "已归档");
		Page<CustomerService> page = serviceService.getPage(pageNo, params);
		map.put("page", page);
		
		return "service/archive/list";
	}

	/**
	 *完成反馈    更新处理结果和满意度 和状态
	 */
	@RequestMapping("/service/updateFeedback")
	public String updateFeedback(CustomerService customerService){
		
		serviceService.updatFeedbacke(customerService);
		
		return "redirect:/service/feedback/list";
	}
	
	/**
	 * 前往服务反馈页面
	 * @param id
	 * @param session
	 * @param map
	 * @return
	 */
	@RequestMapping("service/feedback")
	public String tofeedback(@RequestParam("id") long id,HttpSession session,
			Map<String, Object> map) {

		CustomerService service = serviceService.getServiceById(id);
		map.put("service", service);
		User user = (User) session.getAttribute("user");
		map.put("user", user);
		
		return "service/feedback/feedback";
	}
	/**
	 * 服务反馈分页列表
	 * @param pageNo
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping("/service/feedback/list")
	public String feedbackList(
			@RequestParam(value = "pageNo", required = false) String pageNo,
			Map<String, Object> map, HttpServletRequest request) {

		Map<String, Object> params = WebUtils.getParametersStartingWith(
				request, "search_");

		// 把从前台接收到的请求参数值 转为可以用的加载url后边的值
		String queryString = CRMUtils.encodeParamsToQueryString(params);

		map.put("queryString", queryString);

		params.put("EQS_serviceState", "已处理");
		Page<CustomerService> page = serviceService.getPage(pageNo, params);
		map.put("page", page);

		return "service/feedback/list";
	}

	// 处理服务
	@RequestMapping("/service/updateDeal")
	public String updateDeal(CustomerService customerService) {

		serviceService.updateDeal(customerService);
		return "redirect:/service/deal/list";
	}

	// 前往处理服务的页面
	@RequestMapping("/service/deal")
	public String deal(@RequestParam("id") long id, HttpSession session,
			Map<String, Object> map) {

		CustomerService service = serviceService.getServiceById(id);
		map.put("service", service);

		User user = (User) session.getAttribute("user");
		map.put("user", user);

		return "service/deal/deal";
	}

	/**
	 * 分配服务
	 */
	@RequestMapping("/service/allot")
	@ResponseBody
	public String allot(@RequestParam("id") long serviceId,
			@RequestParam("allotId") long allotId) {

		serviceService.allot(serviceId, allotId);

		return "1";
	}

	/**
	 * 获取已经分配的服务分页
	 * 
	 * @return
	 */
	@RequestMapping("/service/deal/list")
	public String dealList(
			@RequestParam(value = "pageNo", required = false) String pageNo,
			Map<String, Object> map, HttpServletRequest request,
			HttpSession session) {
		Map<String, Object> params = WebUtils.getParametersStartingWith(
				request, "search_");

		// 把从前台接收到的请求参数值 转为可以用的加载url后边的值
		String queryString = CRMUtils.encodeParamsToQueryString(params);

		map.put("queryString", queryString);

		// 获取当前用户，看当前用户是否是被指派人
		User user = (User) session.getAttribute("user");

		params.put("EQS_serviceState", "已分配");
		params.put("EQS_allotId", user.getId() + "");
		Page<CustomerService> page = serviceService.getPage(pageNo, params);
		map.put("page", page);

		return "service/deal/list";
	}

	@RequestMapping("/service/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {
		serviceService.delete(id);

		return "redirect:/service/allot/list";
	}

	@RequestMapping("service/allot/list")
	public String list(
			@RequestParam(value = "pageNo", required = false) String pageNo,
			Map<String, Object> map, HttpServletRequest request) {

		Map<String, Object> params = WebUtils.getParametersStartingWith(
				request, "search_");

		// 把从前台接收到的请求参数值 转为可以用的加载url后边的值
		String queryString = CRMUtils.encodeParamsToQueryString(params);

		map.put("queryString", queryString);

		params.put("EQS_serviceState", "新创建");
		Page<CustomerService> page = serviceService.getPage(pageNo, params);
		map.put("page", page);

		// 获取所有的user
		List<User> users = serviceService.getUsers();
		map.put("users", users);

		return "service/allot/list";
	}

	/**
	 * 保存服务并且跳转到服务的分页集合。 条件为状态为新创建 && 当前登录用户为 被指派的用户
	 * 
	 */
	@RequestMapping("/service/save")
	public String newList(HttpSession session, Map<String, Object> map,
			CustomerService customerService, HttpServletRequest request,
			@RequestParam(value = "pageNo", required = false) String pageNo) {

		// 保存服务
		User user = (User) session.getAttribute("user");
		customerService.setCreatedby(user);

		serviceService.save(customerService);

		return "redirect:/service/allot/list";
	}

	// 创建服务
	@RequestMapping("/service/create")
	public String createService(Map<String, Object> map, HttpSession session) {
		// 从dict表中查出所有的服务类型
		List<String> serviceTypes = serviceService.getServiceType();
		map.put("serviceTypes", serviceTypes);

		// 从customers表中查出所有的客户名称
		List<Customer> customers = serviceService.getCustomers();
		map.put("customers", customers);

		// 获取当前用户
		User user = (User) session.getAttribute("user");
		map.put("user", user);

		return "service/input";
	}
}
