package com.zyc.crm.model;

import java.util.List;

public class Page<T>{

	//当前页面的集合
	private List<T> content;
	//一个页面的有几条记录
	private int pageSize=3;
	//当前的页码
	private int pageNo;
	//一共有多少条记录
	private int totalElements;
	//一共有多少页
	private int totalPages;
	
	//构造器 传入当前页码，一页多少个元素，一共多少元素
	public Page(String pageNoStr,int totalElements){
		this.totalElements = totalElements;
		
		//设置一共有多少页
		this.totalPages = (this.totalElements + this.pageSize-1)/this.pageSize;
		
		//约束当前页码
		this.pageNo = 1;
		
		try {
			this.pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {}
		
		if(pageNo>totalPages){
			this.pageNo=totalPages;
		}
		if(pageNo<1){
			this.pageNo=1;
		}
	}
	
	//判断是否有下一页
	public boolean isHasNextPage(){
		
		return pageNo<totalPages;
	}
	//判断是否有上一页
	public boolean isHasPrevPage(){
		
		return pageNo>1;
	}
	//获取下一页
	public int getNextPage(){

		return pageNo+1;
	}
	//获取上一页
	public int getPrevPage(){
		
		return pageNo-1;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public int getTotalElements() {
		return totalElements;
	}

	public int getTotalPages() {
		return totalPages;
	}
	
	
}
