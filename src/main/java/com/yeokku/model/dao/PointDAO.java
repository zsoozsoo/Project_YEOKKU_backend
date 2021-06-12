package com.yeokku.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.yeokku.model.dto.Point;

@Mapper
@Repository
public interface PointDAO {
	
	public void insertAllPlaces(List<Point> pointList);
	public List<Point> selectPointList();
	public List<Point> selectPointListByCity(String cityId);
	public void updatePointList(List<Point> pointList);
}
