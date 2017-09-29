package com.misa.logging.entity;

import org.bson.types.ObjectId;

public class UserAccess {
	private ObjectId _id;
	private String nickName;
	private int count;
	private String loginDate;
	private String loginHour;
	
	public String getLoginHour() {
		return loginHour;
	}
	public void setLoginHour(String loginHour) {
		this.loginHour = loginHour;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	
	
	
	
}
