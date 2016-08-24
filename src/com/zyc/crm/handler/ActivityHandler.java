package com.zyc.crm.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zyc.crm.bean.Customer;
import com.zyc.crm.bean.CustomerActivity;
import com.zyc.crm.model.Page;
import com.zyc.crm.service.ActivityService;
import com.zyc.crm.service.CustomerServiceImpl;

@Controller
public class ActivityHandler {

	@Autowired
	private ActivityService activityService;

	@Autowired
	private CustomerServiceImpl customerServiceImpl;

	// 删除活动记录
	@RequestMapping("/activity/delete/{activityId}/{customerId}")
	public String delete(@PathVariable("activityId") Integer activityId,
			@PathVariable("customerId") Integer customerId,
		@RequestParam(value = "pageNo", required = false) String pageNo) {
		
		activityService.delete(activityId);

		return "redirect:/activity/list/" + customerId + "?pageNo=" + pageNo;
	}

	// 新建或者更新活动
	@RequestMapping("/activity/create")
	public String saveOrUpdate(CustomerActivity activity,
			@RequestParam("pageNo") String pageNo) {

		// 如果没有id就添加活动，如果有id就更新活动
		if (activity.getId() == null) {
			activityService.save(activity);
			return "redirect:/activity/list/" + activity.getCustomer().getId();
		}
		activityService.update(activity);
		return "redirect:/activity/list/" + activity.getCustomer().getId()
				+ "?pageNo=" + pageNo;
	}

	// 跳转到更新页面
	@RequestMapping("/activity/toUpdateUI/{id}")
	public String toUpdateUI(@PathVariable("id") Integer id,
			Map<String, Object> map,
			@RequestParam(value = "pageNo", required = false) String pageNo) {

		CustomerActivity activity = activityService.getActivity(id);

		map.put("activity", activity);
		map.put("pageNo", pageNo);

		return "activity/input";
	}

	// 跳转到新建活动页面
	@RequestMapping("/activity/create/{customerId}")
	public String toSaveUI(@PathVariable("customerId") Integer customerId,
			Map<String, Object> map) {

		Customer customer = new Customer();
		long cid = (long) customerId;
		customer.setId(cid);

		CustomerActivity activity = new CustomerActivity();
		activity.setCustomer(customer);

		map.put("activity", activity);

		return "activity/input";
	}

	// 分页显示所有的与客户的活动
	@RequestMapping("/activity/list/{customerId}")
	public String list(
			@RequestParam(value = "pageNo", required = false) String pageNoStr,
			Map<String, Object> map,
			@PathVariable("customerId") Integer customerId) {

		Customer customer = customerServiceImpl.getCustomerById(customerId);
		map.put("customer", customer);

		Page<CustomerActivity> page = activityService.getPage(customerId,
				pageNoStr);

		map.put("page", page);

		return "activity/list";
	}
}
