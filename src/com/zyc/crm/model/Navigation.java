package com.zyc.crm.model;

import java.util.ArrayList;
import java.util.List;

public class Navigation {

	private Long id;
	private String text;
	private String state;
	private String url;
	
	private List<Navigation> children = new ArrayList<>();
	public Navigation(Long id, String text) {
		super();
		this.id = id;
		this.text = text;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<Navigation> getChildren() {
		return children;
	}
	public void setChildren(List<Navigation> children) {
		this.children = children;
	}
	
	
	
	
}
