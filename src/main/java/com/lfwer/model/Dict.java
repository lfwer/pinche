package com.lfwer.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 字典表
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "DICT")
public class Dict implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private String type;
	@Id
	private String id;
	private String name;
	private String pid;
	private Integer orders;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}

}
