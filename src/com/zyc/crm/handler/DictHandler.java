package com.zyc.crm.handler;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import com.zyc.crm.bean.Dict;
import com.zyc.crm.bean.SalesChance;
import com.zyc.crm.model.Page;
import com.zyc.crm.model.PropertyFilter;
import com.zyc.crm.service.DictService;
import com.zyc.crm.util.CRMUtils;

@Controller
public class DictHandler {

	@Autowired
	private DictService dictService;

	// 删除字典
	@RequestMapping("/dict/delete/{id}")
	public String delete(@PathVariable("id") Integer id,
			@RequestParam(value = "pageNo", required = false) String pageNo) {

		dictService.delete(id);
		
		return "redirect:/dict/list?pageNo=" + pageNo;
	}

	// 更新字典
	@RequestMapping(value = "/dict/create", method = RequestMethod.PUT)
	public String update(Dict dict,
			@RequestParam(value = "pageNo", required = false) String pageNo) {

		dictService.update(dict);
		return "redirect:/dict/list?pageNo=" + pageNo;
	}

	// 前往编辑页面
	@RequestMapping(value = "/dict/create/{id}", method = RequestMethod.GET)
	public String toUpdateUI(@PathVariable("id") Integer id,
			Map<String, Object> map,
			@RequestParam(value = "pageNo", required = false) String pageNo) {

		Dict dict = dictService.getDictById(id);
		map.put("dict", dict);
		map.put("pageNo", pageNo);

		return "dict/input";
	}

	// 添加字典
	@RequestMapping(value = "/dict/create", method = RequestMethod.POST)
	public String save(Dict dict) {

		dictService.save(dict);
		return "redirect:/dict/list";
	}

	@RequestMapping(value = "/dict/create", method = RequestMethod.GET)
	public String toSaveUI(Map<String, Object> map) {

		map.put("dict", new Dict());

		return "dict/input";
	}

	@RequestMapping("/dict/list")
	public String list(
			@RequestParam(value = "pageNo", required = false) String pageNoStr,
			HttpServletRequest request, Map<String, Object> map) {

		Map<String, Object> params = WebUtils.getParametersStartingWith(
				request, "search_");

		// 把从前台接收到的请求参数值 转为可以用的加载url后边的值
		String queryString = CRMUtils.encodeParamsToQueryString(params);

		map.put("queryString", queryString);

		/*// 把handler传来的参数转为propertyFilter的集合
		List<PropertyFilter> filters = CRMUtils
				.parseHandlerParamsToPropertyFilters(params);
		// 把propertyFilter集合转为mybatis可以用的参数
		Map<String, Object> mybatisParams = CRMUtils
				.parsePropertyFiltersToMyBatisParmas(filters);*/

		Page<Dict> page = dictService.getPage(pageNoStr, params);

		map.put("page", page);

		return "dict/list";
	}
}
