package com.yeokku.model.dto;

public class ConfigPath {

	private int cpId;
	private int pointId;
	private int pointOrder;
	private String pointType;
	private int pathId;
	
	public ConfigPath() {}
	
	public ConfigPath(int pointId, int pointOrder, int pathId) {
		super();
		this.pointId = pointId;
		this.pointOrder = pointOrder;
		this.pathId = pathId;
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
		return pathId;
	}
	public void setPathId(int pathId) {
		this.pathId = pathId;
	}
	
}
