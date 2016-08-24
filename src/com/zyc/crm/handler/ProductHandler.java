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

import com.zyc.crm.bean.Product;
import com.zyc.crm.bean.SalesChance;
import com.zyc.crm.model.Page;
import com.zyc.crm.model.PropertyFilter;
import com.zyc.crm.service.ProductService;
import com.zyc.crm.util.CRMUtils;

@Controller
public class ProductHandler {

	@Autowired
	private ProductService productService;

	@RequestMapping("/product/delete/{id}")
	public String delete(@PathVariable("id") Integer id,
			@RequestParam(value = "pageNo", required = false) String pageNo) {

		productService.delete(id);
		
		return "redirect:/product/list?pageNo=" + pageNo;
	}

	@RequestMapping(value = "/product/create", method = RequestMethod.PUT)
	public String update(Product product,
			@RequestParam(value = "pageNo", required = false) String pageNo) {

		productService.update(product);

		return "redirect:/product/list?pageNo=" + pageNo;
	}

	@RequestMapping("/product/create/{id}")
	public String toUpdateUI(@PathVariable("id") Integer id,
			Map<String, Object> map,
			@RequestParam(value = "pageNo", required = false) String pageNo) {

		Product product = productService.getProductById(id);
		map.put("product", product);
		map.put("pageNo", pageNo);

		return "/product/input";
	}

	@RequestMapping(value = "/product/create", method = RequestMethod.POST)
	public String save(Product product) {

		productService.save(product);

		return "redirect:/product/list?pageNo=" + Integer.MAX_VALUE;
	}

	@RequestMapping(value = "/product/create", method = RequestMethod.GET)
	public String toSaveUI(Map<String, Object> map) {

		map.put("product", new Product());

		return "/product/input";
	}

	@RequestMapping("/product/list")
	public String list(
			@RequestParam(value = "pageNo", required = false) String pageNo,
			HttpServletRequest request, Map<String, Object> map) {

		Map<String, Object> params = WebUtils.getParametersStartingWith(
				request, "search_");

		// 把从前台接收到的请求参数值 转为可以用的加载url后边的值
		String queryString = CRMUtils.encodeParamsToQueryString(params);

		map.put("queryString", queryString);

		// 把handler传来的参数转为propertyFilter的集合
		List<PropertyFilter> filters = CRMUtils
				.parseHandlerParamsToPropertyFilters(params);
		// 把propertyFilter集合转为mybatis可以用的参数
		Map<String, Object> mybatisParams = CRMUtils
				.parsePropertyFiltersToMyBatisParmas(filters);

		Page<SalesChance> page = productService.getPage(pageNo, mybatisParams);

		map.put("page", page);

		return "product/list";
	}
}
