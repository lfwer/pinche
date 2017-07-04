package com.lfwer.model;

import java.io.Serializable;

/**
 *  
 * 
 * @author Administrator
 *
 */
public class Image implements Serializable {
	private static final long serialVersionUID = 1L;
	 
	 
	private Integer id;
	private String largeName;
	private String smallName;
	private Integer width;
	private Integer height;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLargeName() {
		return largeName;
	}
	public void setLargeName(String largeName) {
		this.largeName = largeName;
	}
	public String getSmallName() {
		return smallName;
	}
	public void setSmallName(String smallName) {
		this.smallName = smallName;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	
 
}
