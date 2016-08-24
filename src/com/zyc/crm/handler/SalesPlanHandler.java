package com.zyc.crm.handler;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.zyc.crm.bean.SalesChance;
import com.zyc.crm.bean.SalesPlan;
import com.zyc.crm.bean.User;
import com.zyc.crm.model.Page;
import com.zyc.crm.service.SalesChanceService;
import com.zyc.crm.service.SalesPlanService;
import com.zyc.crm.util.CRMUtils;

@Controller
public class SalesPlanHandler {

	@Autowired
	private SalesPlanService salesPlanService;

	@Autowired
	private SalesChanceService salesChanceService;
	
	

	/**
	 * 保存执行计划效果
	 */
	@RequestMapping("/plan/execute")
	public String saveResult(@RequestParam("id") Integer id,
			@RequestParam("result") String result,
			@RequestParam("chanceId") Integer chanceId) {

		if(result==null){
			result="";
		}
		salesPlanService.updateResult(id, result);

		
		return "redirect:/plan/execution/"+chanceId;
	}

	/**
	 * 执行计划
	 */
	@RequestMapping("/plan/execution/{id}")
	public String executionPlan(@PathVariable("id") Integer id,
			Map<String, Object> map) {
		SalesChance chance = salesChanceService.getChance(id);

		// 因为没有连表，所以只能获取用户的id，不想连表，所以通过用户id获取用户的name！！
		User createBy = salesChanceService.getCreateBy(chance.getCreateBy()
				.getId());
		chance.setCreateBy(createBy);

		User designee = salesChanceService.getCreateBy(chance.getDesignee()
				.getId());
		chance.setDesignee(designee);

		// 通过外键来查询当前销售机会的销售计划
		Set<SalesPlan> salesPlans = salesPlanService.getPlanByChanceId(id);
		chance.setSalesPlans(salesPlans);

		map.put("chance", chance);

		return "plan/exec";
	}

	/**
	 * 更新计划,只更新todo字段
	 */
	@RequestMapping("/plan/make-ajax")
	@ResponseBody
	public String updatePlan(@RequestParam("id") Integer id,
			@RequestParam("todo") String todo) {

		salesPlanService.update(id, todo);
		return "1";
	}

	/**
	 * 删除计划
	 */
	@RequestMapping("/plan/delete-ajax")
	@ResponseBody
	public String deletePlan(@RequestParam("id") Integer id) {

		salesPlanService.delete(id);
		return "1";
	}

	/**
	 * 新建计划并且在当前页面显示
	 * 
	 * @param salesPlan
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/plan/make", method = RequestMethod.POST)
	public String newPlan(SalesPlan salesPlan,
			RedirectAttributes redirectAttributes) {

		if (salesPlan.getTodo() == null) {
			salesPlan.setTodo("");
		}
		if (salesPlan.getDate() == null) {
			salesPlanService.saveNoDate(salesPlan);
			redirectAttributes.addFlashAttribute("message", "新建成功");

			return "redirect:/plan/make/" + salesPlan.getChance().getId();
		}
		salesPlanService.save(salesPlan);

		redirectAttributes.addFlashAttribute("message", "新建成功");

		return "redirect:/plan/make/" + salesPlan.getChance().getId();
	}

	/**
	 * 显示制定计划的页面
	 * 
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping("/plan/make/{id}")
	public String makePlan(@PathVariable Integer id, Map<String, Object> map) {

		SalesChance chance = salesChanceService.getChance(id);

		// 因为没有连表，所以只能获取用户的id，不想连表，所以通过用户id获取用户的name！！
		User createBy = salesChanceService.getCreateBy(chance.getCreateBy()
				.getId());
		chance.setCreateBy(createBy);

		User designee = salesChanceService.getCreateBy(chance.getDesignee()
				.getId());
		chance.setDesignee(designee);

		// 通过外键来查询当前销售机会的销售计划
		Set<SalesPlan> salesPlans = salesPlanService.getPlanByChanceId(id);
		chance.setSalesPlans(salesPlans);

		map.put("chance", chance);

		return "plan/make";
	}

	/**
	 * 显示所有当前用户 被指定的计划
	 * 
	 * @param pageNoStr
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/plan/showList")
	public String showList(
			@RequestParam(value = "pageNo", required = false) String pageNoStr,
			HttpServletRequest request, Map<String, Object> map) {

		Map<String, Object> params = WebUtils.getParametersStartingWith(
				request, "search_");

		// 把从前台接收到的请求参数值 转为可以用的加载url后边的值
		String queryString = CRMUtils.encodeParamsToQueryString(params);

		map.put("queryString", queryString);

		// 获取当前登录用户的信息
		User designee = (User) request.getSession().getAttribute("user");

		// 把创建人和销售机会的状态传进paramsMap中
		params.put("EQO_designee", designee);
		params.put("EQI_status", 1);
		// 要查询销售状态不为1(即salesChance.status !=1)销售机会

		Page<SalesChance> page = salesPlanService.getPage(pageNoStr, params);

		map.put("page", page);

		return "plan/list";
	}
}
