package com.zyc.crm.handler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.zyc.crm.bean.CustomerDrain;
import com.zyc.crm.bean.SalesChance;
import com.zyc.crm.model.Page;
import com.zyc.crm.service.DrainService;
import com.zyc.crm.util.CRMUtils;

@Controller
public class DrainHandler {

	@Autowired
	private DrainService drainService;
	//确认流失
	@RequestMapping("/drain/confirmDrain")
	public String confirmDrain(CustomerDrain customerDrain){
		System.out.println(customerDrain.getCustomer().getId());
		drainService.confirmDrain(customerDrain);
		
		return "redirect:/drain/list";
	}
	
	//前往确定流失的页面
	@RequestMapping("/drain/confirm/{id}")
	public String confirm(@PathVariable("id")Integer id,Map<String,Object> map){
		CustomerDrain drain = drainService.getCustomerDrainById(id);
		map.put("drain", drain);
		
		return "drain/confirm";
	}

	// 保存暂缓流失措施
	@RequestMapping("/drain/delay-ajax")
	@ResponseBody
	public String saveDelay(@RequestParam("drainId") Integer drainId,
			@RequestParam("delay") String delay) {
		
		if(delay=="" ||delay==null || delay.trim()==""){
			return "";
		}
		
		StringBuilder builder = new StringBuilder();
		String[] split =null;
		
		CustomerDrain drain = drainService.getCustomerDrainById(drainId);
		
		if(drain.getDelay()==null){
			drainService.save(drainId,delay);
			return "1";
		}else{
			String oldDelay = drain.getDelay();
			StringBuilder newBuilder = builder.append(oldDelay).append("`").append(delay);
			String newdelay = newBuilder.toString();
			drainService.save(drainId,newdelay);
			split = newdelay.split("`");
		}
		return split.length+"";
	}

	// 前往暂缓流失的页面
	@RequestMapping("/drain/delay/{id}")
	public String toDelayUI(@PathVariable("id") Integer id,
			Map<String, Object> map) {

		CustomerDrain drain = drainService.getCustomerDrainById(id);
		map.put("drain", drain);

		return "drain/delay";
	}

	// 展示快要流失的客户
	@RequestMapping("/drain/list")
	public String showList(Map<String, Object> map,
			@RequestParam(value = "pageNo", required = false) String pageNo,
			HttpServletRequest request) {

		Map<String, Object> params = WebUtils.getParametersStartingWith(
				request, "search_");

		String queryString = CRMUtils.encodeParamsToQueryString(params);

		map.put("queryString", queryString);

		Page<SalesChance> page = drainService.getPage(pageNo, params);

		map.put("page", page);
		return "drain/list";
	}
}
