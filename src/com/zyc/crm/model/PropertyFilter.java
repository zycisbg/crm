package com.zyc.crm.model;


import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * 属性过滤器
 * @author Administrator
 *
 */
public class PropertyFilter {

	//属性名
	private String propertyName;
	//属性值
	private Object propertyVal;
	//目标属性的类型
	private Class propertyType;
	//比较的方式(大于，等于，小于，like-----利用枚举创建的比较方式)
	private MathType mathType;
	
	//传入属性名和属性值的构造器
	public PropertyFilter(String propertyName,Object propertyVal){
		//前段传入的参数为这样LIKES_xxx
		
		//获取下划线之前的字段 --LIKES
		String str1 = StringUtils.substringBefore(propertyName, "_");
		//获取下划线之前的排除最后一个字母的字段  -- LIKE --比较方式
		String mathTypeCode = StringUtils.substring(str1,0,str1.length()-1);
		//获取剩余str最后一个字母的字符串 --S --
		String propertyTypeCode = StringUtils.substring(str1, str1.length()-1);
		
		//把比较方式转为对应的枚举类型
		this.mathType = Enum.valueOf(MathType.class, mathTypeCode);
		
		//把目标对象的类型传入得到对应的枚举类型
		PropertyType propertyType = Enum.valueOf(PropertyType.class, propertyTypeCode);
		this.propertyType = propertyType.getPropertyType();
		
		this.propertyName = StringUtils.substringAfterLast(propertyName, "_");
		
		this.propertyVal = propertyVal;
		
	}
	
	//比较方式的枚举
	public enum MathType{
		EQ, GT, GE, LT, LE, LIKE;
	}
	
	//目标对象类型的枚举
	public enum PropertyType{
		I(Integer.class), F(Float.class), S(String.class), D(Date.class), O(Object.class);
		
		private Class propertyType;
		
		private PropertyType(Class propertyType){
			this.propertyType = propertyType;
		}
		
		public Class getPropertyType() {
			return propertyType;
		}
	}

	public String getPropertyName() {
		return propertyName;
	}

	public Object getPropertyVal() {
		return propertyVal;
	}

	public Class getPropertyType() {
		return propertyType;
	}

	public MathType getMathType() {
		return mathType;
	}
	
	
}
