package com.wx.no_five_row_six.common.security;

import java.io.Serializable;

/**
 * 登录用户模具
 */
public class AdminUserModel implements Serializable {
	private Long id;
	private String name;
	private String showName;
	private String userHead;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShowName() { return showName; }
	public void setShowName(String showName) { this.showName = showName; }

	public String getUserHead() {
		return userHead;
	}

	public void setUserHead(String userHead) {
		this.userHead = userHead;
	}
}
