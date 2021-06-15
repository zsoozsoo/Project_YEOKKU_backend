package com.yeokku.model.dto;

public class ConfigPath {

	private int cpId;
	private int pointId;
	private String pointName;
	private int pointOrder;
	private String pointType;
	private int userpathId;
	private double pointLat;
	private double pointLng;
	private String description;
	
	
	public ConfigPath() {}
	
	public ConfigPath(int pointId, int pointOrder, int pathId, String pointName) {
		super();
		this.pointId = pointId;
		this.pointOrder = pointOrder;
		this.userpathId = pathId;
		this.pointName=pointName;
	}
	
	public ConfigPath(int cpId, int pointId, String pointName, int pointOrder, int userpathId,
			double pointLat, double pointLng, String description) {
		super();
		this.cpId = cpId;
		this.pointId = pointId;
		this.pointName = pointName;
		this.pointOrder = pointOrder;

		this.userpathId = userpathId;
		this.pointLat = pointLat;
		this.pointLng = pointLng;
		this.description = description;
	}

	public int getCpId() {
		return cpId;
	}
	public void setCpId(int cpId) {
		this.cpId = cpId;
	}
	public int getPointId() {
		return pointId;
	}
	public void setPointId(int pointId) {
		this.pointId = pointId;
	}
	public int getPointOrder() {
		return pointOrder;
	}
	public void setPointOrder(int pointOrder) {
		this.pointOrder = pointOrder;
	}
	public String getPointType() {
		return pointType;
	}
	public void setPointType(String pointType) {
		this.pointType = pointType;
	}
	public int getPathId() {
		return userpathId;
	}
	public void setPathId(int pathId) {
		this.userpathId = pathId;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	public double getPointLat() {
		return pointLat;
	}

	public void setPointLat(double pointLat) {
		this.pointLat = pointLat;
	}

	public double getPointLng() {
		return pointLng;
	}

	public void setPointLng(double pointLng) {
		this.pointLng = pointLng;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
