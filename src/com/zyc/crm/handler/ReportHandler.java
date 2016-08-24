package com.zyc.crm.handler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import com.zyc.crm.model.Page;
import com.zyc.crm.service.ReportService;
import com.zyc.crm.util.CRMUtils;
@Controller
public class ReportHandler {

	@Autowired
	private ReportService reportService;
	
	
	

	@RequestMapping("/report/pay")
	public String toPayUI(Map<String, Object> map,
			@RequestParam(value="pageNo",required=false) String pageNo, HttpServletRequest request) {

		Map<String, Object> params = WebUtils.getParametersStartingWith(
				request, "search_");

		// 把从前台接收到的请求参数值 转为可以用的加载url后边的值
		String queryString = CRMUtils.encodeParamsToQueryString(params);

		map.put("queryString", queryString);

		Page<Object[]> page = reportService.getPage(pageNo, params);
		map.put("page", page);

		return "report/pay";
	}
}
