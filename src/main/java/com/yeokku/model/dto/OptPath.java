package com.yeokku.model.dto;

import java.util.List;

public class OptPath {

	private String selectCriteria; // 선택한 기준 (거리 or 시간)
	private List<Point> pointsInOrder; // 순서대로 저장된 관광지
	private int selectedMinCost; // 선택한 기준의 총 비용
	private int anotherMinCost; // 다른 기준의 총 비용
	private String mode; // 선택한 이동모드 (자동차 or 자전거 or 도보)
	
	public OptPath() {}
	
	public OptPath(String selectCriteria, List<Point> pointsInOrder, int selectedMinCost,
			int anotherMinCost, String mode) {
		super();
		this.selectCriteria = selectCriteria;	
		this.pointsInOrder = pointsInOrder;
		this.selectedMinCost = selectedMinCost;
		this.anotherMinCost = anotherMinCost;
		this.mode = mode;
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

	public int getSelectedMinCost() {
		return selectedMinCost;
	}

	public void setSelectedMinCost(int selectedMinCost) {
		this.selectedMinCost = selectedMinCost;
	}

	public int getAnotherMinCost() {
		return anotherMinCost;
	}

	public void setAnotherMinCost(int anotherMinCost) {
		this.anotherMinCost = anotherMinCost;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
	
	
}
