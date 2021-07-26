package com.yeokku.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.yeokku.model.dto.City;

@Mapper
@Repository
public interface CityDAO {

	public void insertAllCities(List<City> cityList);
	public List<City> selectCityList();
	public List<City> selectCityListByCountry(String country);
}
