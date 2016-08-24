package com.zyc.crm.handler;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry.Entry;
import com.zyc.crm.bean.SalesChance;
import com.zyc.crm.bean.SalesPlan;
import com.zyc.crm.bean.User;
import com.zyc.crm.model.Page;
import com.zyc.crm.service.SalesChanceService;
import com.zyc.crm.service.SalesPlanService;
import com.zyc.crm.util.CRMUtils;

@Controller
@RequestMapping("/chance")
public class SalesChanceHandler {

	@Autowired
	private SalesChanceService salesChanceService;
	
	@Autowired
	private SalesPlanService salesPlanService;
	
	/**
	 * 开发成功
	 */
	
	@RequestMapping("/finish/{id}")
	public String finish(@PathVariable("id") Integer id){
		
		/*
		 * 开发成功后，需要先把销售机会的状态设置为3
		 * 向数据库中customers插入一条数据(name，no（随机字符串） 和 state（正常）)
		 * 向数据库中contacts中插入一条数据(name、tel、customer_id)
		 */
		salesChanceService.finish(id,3);
		
		return "redirect:/chance/showList";
	}
	
	/**
	 * 查看失败的执行计划
	 */
	@RequestMapping("/detail/{id}")
	public String detail(@PathVariable("id") Integer id,Map<String,Object> map){
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
		
		return "plan/detail";
	}
	
	/**
	 * 终止开发
	 */
	@RequestMapping("/stop/{id}")
	public String stop(@PathVariable("id") Integer id){
		
		//把销售机会的状态改为4
		salesChanceService.updateChanceStatus(id,4);
		return "redirect:/chance/showList";
	}
	
	/**
	 * 完成指派
	 * @param salesChance
	 * @return
	 */
	@RequestMapping("/finishDispatch")
	public String finishDispatch(SalesChance salesChance){
		
		//更新指派时间和 指派给谁,销售状态直接在salesChanceMapper中直接设置
		salesChanceService.updateDesigneeDateAndDesignee(salesChance);
		
		return "redirect:/chance/showList";
	}
	
	/**
	 * 跳转到指定销售机会的页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value="dispatch/{id}",method=RequestMethod.GET)
	public String toDispatchUI(@PathVariable("id") Integer id,Map<String,Object> map){
		
		SalesChance chance = salesChanceService.getChance(id);
		
		//为了显示创建人姓名 ，通过创建人Id查询创建人的姓名
		
		User createBy = salesChanceService.getCreateBy(chance.getCreateBy().getId());
		
		//把查询到的对象放进销售机会中
		chance.setCreateBy(createBy);
		
		map.put("chance", chance);
		
		//为了显示指派给谁，所以要查询所有的User
		List<User> users = salesChanceService.getAllUser();
		
		map.put("users", users);
		return "chance/dispatch";
	}

	// 修改销售机会
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String update(
			@RequestParam(value = "pageNo", required = false, defaultValue = "1") String pageNo,
			@PathVariable("id") Integer id, SalesChance chance,
			RedirectAttributes redirectAttributes) {

		salesChanceService.update(chance);
		redirectAttributes.addFlashAttribute("message", "修改成功");

		return "redirect:/chance/showList?pageNo=" + pageNo;
	}

	// 前往修改的页面，
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String toUpdateUI(@PathVariable("id") Integer id,
			Map<String, Object> map) {
		SalesChance chance = salesChanceService.getChance(id);

		map.put("chance", chance);
		return "chance/input";
	}

	// 删除销售机会
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String delete(
			@PathVariable("id") Integer id,
			@RequestParam(value = "pageNo", required = false, defaultValue = "1") String pageNo,
			RedirectAttributes redirectAttributes) {

		salesChanceService.delete(id);
		redirectAttributes.addFlashAttribute("message", "删除成功!!");

		return "redirect:/chance/showList?pageNo=" + pageNo;
	}

	// 添加销售机会
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String save(SalesChance salesChance,
			RedirectAttributes redirectAttributes) {

		salesChance.setStatus(1);

		salesChanceService.save(salesChance);

		redirectAttributes.addFlashAttribute("message", "操作成功啊。");
		return "redirect:/chance/showList?pageNo=" + Integer.MAX_VALUE;
	}

	// 跳转到添加页面
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String input(Map<String, Object> map) {
		map.put("chance", new SalesChance());

		return "chance/input";
	}

	/*
	 * // 显示当前的销售机会
	 * 
	 * @RequestMapping(value = "/showList", method = RequestMethod.GET) public
	 * String showSalesChanceList(
	 * 
	 * @RequestParam(value = "pageNo", required = false) String pageNoStr,
	 * HttpSession session, Map<String, Object> map) {
	 * 
	 * // 要查询当前用户刚刚创建的(即salesChance.status==1)销售机会 User careateBy = (User)
	 * session.getAttribute("user");
	 * 
	 * Page<SalesChance> page = salesChanceService.getPage(pageNoStr, careateBy,
	 * 1);
	 * 
	 * map.put("page", page);
	 * 
	 * return "chance/list"; }
	 */

	// 显示当前的销售机会
	// 显示带查询条件的销售机会
	/*@RequestMapping(value = "/showList")
	public String showSalesChanceList(
			@RequestParam(value = "pageNo", required = false) String pageNoStr,
			HttpSession session,
			Map<String, Object> map,
			@RequestParam(value = "search_LIKE_custName", required = false) String custName,
			@RequestParam(value = "search_LIKE_title", required = false) String title,
			@RequestParam(value = "search_LIKE_contact", required = false) String contact) {

		// 获取创建人的信息
		User careateBy = (User) session.getAttribute("user");

		if (custName != null || title != null || contact != null) {
			if(custName ==""){
				custName=null;
			}
			if(title==""){
				title=null;
			}
			if(contact==""){
				contact=null;
			}
			
			Page<SalesChance> queryPage = salesChanceService.getPageByQuery(pageNoStr, careateBy, 1,custName,title,contact);
			map.put("page", queryPage);
			return "chance/list";
		}

		// 要查询当前用户刚刚创建的(即salesChance.status==1)销售机会

		Page<SalesChance> page = salesChanceService.getPage(pageNoStr,
				careateBy, 1);

		map.put("page", page);

		return "chance/list";
	}*/
	
	
	@RequestMapping(value = "/showList")
	public String showSalesChanceList(
			@RequestParam(value = "pageNo", required = false) String pageNoStr,
			HttpServletRequest request,
			Map<String, Object> map) {
		
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");
		
		//把从前台接收到的请求参数值  转为可以用的加载url后边的值
		String queryString = CRMUtils.encodeParamsToQueryString(params);
		
		map.put("queryString", queryString);

		// 获取创建人的信息
		User createBy = (User) request.getSession().getAttribute("user");
		
		//把创建人和销售机会的状态传进paramsMap中
		params.put("EQO_createBy", createBy);
		params.put("EQI_status", 1);
		// 要查询当前用户刚刚创建的(即salesChance.status==1)销售机会

		Page<SalesChance> page = salesChanceService.getPage(pageNoStr,params);

		map.put("page", page);

		return "chance/list";
	}
	
}
