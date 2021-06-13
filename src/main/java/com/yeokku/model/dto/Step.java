package com.yeokku.model.dto;

import java.util.List;

public class Step {

	String distance;
	String duration;
	String travelMode;
	List<Step> detailStepList;
	
	public Step() {}
	
	public Step(String distance, String duration, String travelMode) {
		super();
		this.distance = distance;
		this.duration = duration;
		this.travelMode = travelMode;
	}
	
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getTravelMode() {
		return travelMode;
	}
	public void setTravelMode(String travelMode) {
		this.travelMode = travelMode;
	}
	public List<Step> getDetailStepList() {
		return detailStepList;
	}
	public void setDetailStepList(List<Step> detailStepList) {
		this.detailStepList = detailStepList;
	}
	
	
}
