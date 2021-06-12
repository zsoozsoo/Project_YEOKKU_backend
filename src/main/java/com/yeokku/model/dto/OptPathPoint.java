package com.yeokku.model.dto;

public class OptPathPoint {

	long legsDistance;
	long legsDuration;
	String startName;
	double startLat;
	double startLng;
	String endName;
	double endLat;
	double endLng;
	
	public OptPathPoint() {
		super();
		
	}
	
	
	
	public OptPathPoint(long legsDistance, long legsDuration, String startName, double startLat, double startLng,
			String endName, double endLat, double endLng) {
		super();
		this.legsDistance = legsDistance;
		this.legsDuration = legsDuration;
		this.startName = startName;
		this.startLat = startLat;
		this.startLng = startLng;
		this.endName = endName;
		this.endLat = endLat;
		this.endLng = endLng;
	}


	public long getLegsDistance() {
		return legsDistance;
	}
	public void setLegsDistance(long legsDistance) {
		this.legsDistance = legsDistance;
	}
	public long getLegsDuration() {
		return legsDuration;
	}
	public void setLegsDuration(long legsDuration) {
		this.legsDuration = legsDuration;
	}
	public String getStartName() {
		return startName;
	}
	public void setStartName(String startName) {
		this.startName = startName;
	}
	public String getEndName() {
		return endName;
	}
	public void setEndName(String endName) {
		this.endName = endName;
	}
	public double getStartLat() {
		return startLat;
	}
	public void setStartLat(double startLat) {
		this.startLat = startLat;
	}
	public double getStartLng() {
		return startLng;
	}
	public void setStartLng(double startLng) {
		this.startLng = startLng;
	}
	public double getEndLat() {
		return endLat;
	}
	public void setEndLat(double endLat) {
		this.endLat = endLat;
	}
	public double getEndLng() {
		return endLng;
	}
	public void setEndLng(double endLng) {
		this.endLng = endLng;
	}
	
	
	
}
