package com.test.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserActivity implements Serializable{
	
	/**
	 * 
	 */
	@Id
	@GeneratedValue
	private Long id;
	private static final long serialVersionUID = 1L;

	@Column
	@ElementCollection(targetClass=String.class)
	private List<String> items;

	public List<String> getItems() {
		return items;
	}

	public void setItems(List<String> items) {
		this.items = items;
	}

	public Date getLastActivityOfBuying() {
		return lastActivityOfBuying;
	}

	public void setLastActivityOfBuying(Date lastActivityOfBuying) {
		this.lastActivityOfBuying = lastActivityOfBuying;
	}

	private Date lastActivityOfBuying;
	
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
