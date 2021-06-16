package com.yeokku.model.dto;

import java.util.List;

public class OptPath {

	private int userPathId;
	private String userId; //유저 아이디
	private String pathType; // 순환 or 비순환  
	private String pathCriteria; // 선택한 기준 (거리 or 시간)
	private List<Point> pointsInOrder; // 순서대로 저장된 관광지
	private String totalDistance; // 해당 경로의 총 거리
	private String totalDuration; // 해당 경로의 총 시간
	private String travelMode; // 선택한 이동모드 (자동차 or 자전거 or 도보 or 대중교통)
	private String message; // 대체경로 여부
	private String countryName; //국가
	private String cityName; //도시
	private String saveDate; //저장날짜
	
	public OptPath() {}
	
	public OptPath(String pathType, List<Point> pointsInOrder, String totalDistance, String totalDuration, String travelMode,
			String message, String countryName) {
		super();
		this.pathType = pathType;
		this.pointsInOrder = pointsInOrder;
		this.totalDistance = totalDistance;
		this.totalDuration = totalDuration;
		this.travelMode = travelMode;
		this.message = message;
		this.countryName = countryName;
	}

	public OptPath(String pathType, String roundPathCriteria, List<Point> pointsInOrder, String totalDistance,
			String totalDuration, String travelMode, String message, String countryName) {
		super();
		this.pathType = pathType;
		this.pathCriteria = roundPathCriteria;	
		this.pointsInOrder = pointsInOrder;
		this.totalDistance = totalDistance;
		this.totalDuration = totalDuration;
		this.travelMode = travelMode;
		this.message = message;
		this.countryName = countryName;
	}

	public String getPathType() {
		return pathType;
	}

	public void setPathType(String pathType) {
		this.pathType = pathType;
	}

	public String getPathCriteria() {
		return pathCriteria;
	}

	public void setPathCriteria(String roundPathCriteria) {
		this.pathCriteria = roundPathCriteria;
	}

	public List<Point> getPointsInOrder() {
		return pointsInOrder;
	}

	public void setPointsInOrder(List<Point> pointsInOrder) {
		this.pointsInOrder = pointsInOrder;
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

	public String getTravelMode() {
		return travelMode;
	}

	public void setTravelMode(String travelMode) {
		this.travelMode = travelMode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getUserPathId() {
		return userPathId;
	}

	public void setUserPathId(int userPathId) {
		this.userPathId = userPathId;
	}

	public String getSaveDate() {
		return saveDate;
	}

	public void setSaveDate(String saveDate) {
		this.saveDate = saveDate;
	}

	
	
	
	
}
