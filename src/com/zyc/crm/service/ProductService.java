package com.zyc.crm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zyc.crm.bean.Product;
import com.zyc.crm.bean.SalesChance;
import com.zyc.crm.mapper.ProductMapper;
import com.zyc.crm.model.Page;

@Service
@Transactional
public class ProductService {

	@Autowired
	private ProductMapper productMapper;

	public Page<SalesChance> getPage(String pageNo,
			Map<String, Object> mybatisParams) {
		// 通过mabtaisParams 查询 带条件的总的记录数
		long totalElements = productMapper.getTotalElementsByMap(mybatisParams);

		// 获取总个数后创建page对象(为了约束pageNo)
		Page<SalesChance> page = new Page<>(pageNo, (int) totalElements);

		// 2.获取当前页面的List
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = page.getPageSize() + fromIndex;

		// 把分页所需要的参数传入mabatisparams中查询当前页面的list
		mybatisParams.put("fromIndex", fromIndex);
		mybatisParams.put("endIndex", endIndex);

		List<SalesChance> content = productMapper.getContentByMap(mybatisParams);
		page.setContent(content);

		return page;
	}

	public void save(Product product) {

		productMapper.save(product);
	}

	public Product getProductById(Integer id) {
		
		
		return productMapper.getProductById(id);
	}

	public void update(Product product) {

		productMapper.update(product);
	}

	public void delete(Integer id) {

		productMapper.delete(id);
	}
}
