package com.yeokku.model.dto;

public class DetailDirection {

	private Point startPoint;
	private Point endPoint;
	private String totalDistance;
	private String totalDuration;
	private String instuction;
	
	public DetailDirection() {}
	
	public DetailDirection(Point startPoint, Point endPoint, String totalDistance, String totalDuration,
			String instuction) {
		super();
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.totalDistance = totalDistance;
		this.totalDuration = totalDuration;
		this.instuction = instuction;
	}

	public Point getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}

	public Point getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}

	public String getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(String totalDistance) {
		this.totalDistance = totalDistance;
	}

	public String getTotalDuration() {
		return totalDuration;
	}

	public void setTotalDuration(String totalDuration) {
		this.totalDuration = totalDuration;
	}

	public String getDescription() {
		return instuction;
	}

	public void setDescription(String instuction) {
		this.instuction = instuction;
	}
	
}
