package com.yeokku.model.dto;

public class City {

	private String cityId;
	private String cityName;
	private String countryCode;
	private String countryName;
	private double centerLat;
	private double centerLng;
	
	public City() {}
	
	public City(String cityName, String countryCode, String countryName) {	
		this.cityName = cityName;
		this.countryCode = countryCode;
		this.countryName = countryName;
	}

	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public double getCenterLat() {
		return centerLat;
	}
	public void setCenterLat(double centerLat) {
		this.centerLat = centerLat;
	}
	public double getCenterLng() {
		return centerLng;
	}
	public void setCenterLng(double centerLng) {
		this.centerLng = centerLng;
	}
}
