package org.lovi.psdemo.models;

import java.util.ArrayList;

public class User {
	
	private String userId;
	private String password;
	private String firstName;
	private String phoneNumber;
	private int roleId;
	
	private ArrayList<Menu> arrayListMenu;
	private String defaultPageUrl;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phone) {
		this.phoneNumber = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public ArrayList<Menu> getArrayListMenu() {
		return arrayListMenu;
	}
	public void setArrayListMenu(ArrayList<Menu> arrayListMenu) {
		this.arrayListMenu = arrayListMenu;
	}
	public String getDefaultPageUrl() {
		return defaultPageUrl;
	}
	public void setDefaultPageUrl(String defaultPageUrl) {
		this.defaultPageUrl = defaultPageUrl;
	}
	
}
