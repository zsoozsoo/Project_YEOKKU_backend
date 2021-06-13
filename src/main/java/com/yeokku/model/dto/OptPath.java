package com.yeokku.model.dto;

import java.util.List;

public class OptPath {

	private String selectCriteria; // 선택한 기준 (거리 or 시간)
	private List<Point> pointsInOrder; // 순서대로 저장된 관광지
	private String selectedMinCost; // 선택한 기준의 총 비용
	private String anotherMinCost; // 다른 기준의 총 비용
	private String mode; // 선택한 이동모드 (자동차 or 자전거 or 도보)
	private String message; // 대체경로 여부
	private List<DetailDirection> detailDirections;
	
	public OptPath() {}
	
	public OptPath(String selectCriteria, List<Point> pointsInOrder, String selectedMinCost,
			String anotherMinCost, String mode, String message) {
		super();
		this.selectCriteria = selectCriteria;	
		this.pointsInOrder = pointsInOrder;
		this.selectedMinCost = selectedMinCost;
		this.anotherMinCost = anotherMinCost;
		this.mode = mode;
		this.message = message;
	}

	public String getSelectCriteria() {
		return selectCriteria;
	}

	public void setSelectCriteria(String selectCriteria) {
		this.selectCriteria = selectCriteria;
	}

	public List<Point> getPointsInOrder() {
		return pointsInOrder;
	}

	public void setPointsInOrder(List<Point> pointsInOrder) {
		this.pointsInOrder = pointsInOrder;
	}

	public String getSelectedMinCost() {
		return selectedMinCost;
	}

	public void setSelectedMinCost(String selectedMinCost) {
		this.selectedMinCost = selectedMinCost;
	}

	public String getAnotherMinCost() {
		return anotherMinCost;
	}

	public void setAnotherMinCost(String anotherMinCost) {
		this.anotherMinCost = anotherMinCost;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<DetailDirection> getDetailDirections() {
		return detailDirections;
	}

	public void setDetailDirections(List<DetailDirection> detailDirections) {
		this.detailDirections = detailDirections;
	}
	
}
