package com.yeokku.model.dto;

public class UserPath {

	private int userpathId; //auto increment
	private String userId;
	private String saveDate;
	private String countryName;
	
	public UserPath() {}
	
	public UserPath(String userId, String countryName) {
		super();
		this.userId = userId;
		this.countryName = countryName;
	}
	public int getUserpathId() {
		return userpathId;
	}
	public void setUserpathId(int userpathId) {
		this.userpathId = userpathId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSaveDate() {
		return saveDate;
	}
	public void setSaveDate(String saveDate) {
		this.saveDate = saveDate;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	

}
