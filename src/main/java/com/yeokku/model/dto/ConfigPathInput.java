package com.yeokku.model.dto;

import java.util.List;

public class ConfigPathInput {

	private Point start;
	private Point end;
	private List<Point> pointList;
	private String countryName;
	
	public ConfigPathInput() {}
	
	public ConfigPathInput(Point start, Point end, List<Point> pointList) {
		super();
		this.start = start;
		this.end = end;
		this.pointList = pointList;
	}
	public Point getStart() {
		return start;
	}
	public void setStart(Point start) {
		this.start = start;
	}
	public Point getEnd() {
		return end;
	}
	public void setEnd(Point end) {
		this.end = end;
	}
	public List<Point> getPointList() {
		return pointList;
	}
	public void setPointList(List<Point> pointList) {
		this.pointList = pointList;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

}
