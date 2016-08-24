package com.zyc.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zyc.crm.bean.CustomerActivity;

public interface ActivityMapper {

	long getTotalElement(@Param("customerId") Integer customerId);

	List<CustomerActivity> getContent(@Param("customerId") Integer customerId,
			@Param("fromIndex") int fromIndex, @Param("endIndex") int endIndex);

	void save(CustomerActivity activity);

	void update(CustomerActivity activity);

	CustomerActivity getActivity(@Param("id") Integer id);

	void delete(@Param("activityId") Integer activityId);

}
