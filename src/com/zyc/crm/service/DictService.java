package com.zyc.crm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zyc.crm.bean.Dict;
import com.zyc.crm.bean.Dict;
import com.zyc.crm.mapper.DictMapper;
import com.zyc.crm.model.Page;

@Service
@Transactional
public class DictService extends BaseService<Dict>{

	@Autowired
	private DictMapper dictMapper;

	/*public Page<Dict> getPage(String pageNoStr,
			Map<String, Object> mybatisParams) {

		// 通过mabtaisParams 查询 带条件的总的记录数
		long totalElements = dictMapper
				.getTotalElements(mybatisParams);

		// 获取总个数后创建page对象(为了约束pageNo)
		Page<Dict> page = new Page<>(pageNoStr, (int) totalElements);

		// 2.获取当前页面的List
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = page.getPageSize() + fromIndex;

		// 把分页所需要的参数传入mabatisparams中查询当前页面的list
		mybatisParams.put("fromIndex", fromIndex);
		mybatisParams.put("endIndex", endIndex);

		List<Dict> content = dictMapper
				.getContent(mybatisParams);
		page.setContent(content);

		return page;
	}
*/
	//添加字典项
	public void save(Dict dict) {

		dictMapper.save(dict);
	}

	public Dict getDictById(Integer id) {
		return dictMapper.getDictById(id);
	}

	public void update(Dict dict) {

		dictMapper.update(dict);
	}

	public void delete(Integer id) {

		dictMapper.delete(id);
	}
}
