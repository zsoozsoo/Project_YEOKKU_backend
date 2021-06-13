package com.yeokku.model.dto;

public class Point {

	private int pointId;
	private String pointName;
	private int cityId;
	private String cityName;
	private String desc;
	private String img;
	private double lat;
	private double lng;
	private String countryName;
	
	public Point() {}
	
	public Point(String pointName, String cityName) {
		this.pointName = pointName;
		this.cityName = cityName;
	}
	
	public Point(String pointName, double lat, double lng) {

		this.pointName = pointName;
		this.lat = lat;
		this.lng = lng;
	}

	public Point(String pointName, String cityName, double lat, double lng) {
		super();
		this.pointName = pointName;
		this.cityName = cityName;
		this.lat = lat;
		this.lng = lng;
	}
	

	public Point(int pointId, String countryName) {
		super();
		this.pointId = pointId;
		this.countryName = countryName;
	}

	public int getPointId() {
		return pointId;
	}

	public void setPointId(int pointId) {
		this.pointId = pointId;
	}

	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public String getPointName() {
		return pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
		


}

