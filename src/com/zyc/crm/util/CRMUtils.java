package com.zyc.crm.util;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import com.zyc.crm.model.PropertyFilter;
import com.zyc.crm.model.PropertyFilter.MathType;

public class CRMUtils {
	
	static{
		DateConverter dateConverter = new DateConverter();
		dateConverter.setPatterns(new String[]{"yyyy-MM-dd","yyyy-MM-dd hh:mm:ss"});
		ConvertUtils.register(dateConverter, Date.class);
	}
	
	// 把propertyFilter集合转为mybatis可以用的参数
	public static Map<String, Object> parsePropertyFiltersToMyBatisParmas(
			List<PropertyFilter> filters) {
		Map<String, Object> params = new HashMap<String, Object>();
		for(PropertyFilter filter: filters){
			String propertyName = filter.getPropertyName();
			
			Object propertyVal = filter.getPropertyVal();
			Class propertyType = filter.getPropertyType();
			if (propertyVal == null || propertyVal.toString().trim().length() == 0) {
				continue;
			}
			propertyVal = ConvertUtils.convert(propertyVal, propertyType);
			
			MathType mathType = filter.getMathType();
			if(mathType == MathType.LIKE){
				propertyVal = "%" + propertyVal + "%";
			}
			params.put(propertyName, propertyVal);
			
		}
		
		return params;
	}

	// 把handler传来的参数转为propertyFilter的集合
	public static List<PropertyFilter> parseHandlerParamsToPropertyFilters(
			Map<String, Object> params) {
		
		List<PropertyFilter> filters = new ArrayList<>();

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			String fieldName = entry.getKey();
			Object fieldVal = entry.getValue();

			PropertyFilter filter = new PropertyFilter(fieldName, fieldVal);
			filters.add(filter);
		}

		return filters;
	}

	// 把从前台接收到的请求参数值 转为可以用的加载url后边的值
	public static String encodeParamsToQueryString(Map<String, Object> params) {

		StringBuilder builder = new StringBuilder();
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();

			if (value == null || value.toString().trim().length() == 0) {
				continue;
			}
			builder.append("&").append("search_").append(key).append("=")
					.append(value);
		}

		return builder.toString();
	}
}
