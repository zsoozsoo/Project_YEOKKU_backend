package com.yeokku.model.dto;

import java.util.List;

public class DetailDirection {

	private Point startPoint;
	private Point endPoint;
	private String totalDistance;
	private String totalDuration;
	private List<Step> instuctionList;
	private String message;
	private String polyline;
	
	public DetailDirection() {}
	
	public DetailDirection(Point startPoint, Point endPoint, String totalDistance, String totalDuration,
			List<Step> instuctionList, String message, String polyline) {
		super();
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.totalDistance = totalDistance;
		this.totalDuration = totalDuration;
		this.instuctionList = instuctionList;
		this.message = message;
		this.polyline = polyline;
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

	public List<Step> getInstuctionList() {
		return instuctionList;
	}

	public void setInstuctionList(List<Step> instuctionList) {
		this.instuctionList = instuctionList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPolyline() {
		return polyline;
	}

	public void setPolyline(String polyline) {
		this.polyline = polyline;
	}
	
}
