package com.gzx.carmodel.bean;

import cn.bmob.v3.BmobObject;

public class UserInfo extends BmobObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId;      //�û�ID
	private String userName;    //�û���
	private String phone; 		// �绰
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
