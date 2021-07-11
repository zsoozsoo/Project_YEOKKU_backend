package com.yeokku.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yeokku.model.dao.CityDAO;
import com.yeokku.model.dao.CountryDAO;
import com.yeokku.model.dao.PointDAO;
import com.yeokku.model.dto.City;
import com.yeokku.model.dto.Country;
import com.yeokku.model.dto.Point;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/select")
public class SelectRestController {

	@Autowired
	CountryDAO countryDao;
	@Autowired
	CityDAO cityDao;
	@Autowired
	PointDAO pointDao;
	
	// 대륙 목록 반환
	@GetMapping("/continent")
	private List<Country> selectContinent() {
		return countryDao.selectContinentList();
	}
	
	// 국가 목록 반환
	@GetMapping("/country/{contName}")
	private List<Country> selectCountryByCont(@PathVariable String contName) {
		return countryDao.selectCountryListByCont(contName);
	}
	
	// 도시 목록 반환
	@GetMapping("/city/{countryName}")
	private List<City> selectCityByCountry(@PathVariable String countryName) {
		return cityDao.selectCityListByCountry(countryName);
	}
	
	// 관광지 목록 반환
	@GetMapping("/point/{cityName}")
	private List<Point> selectPointByCity(@PathVariable String cityName) {
		return pointDao.selectPointListByCity(cityName);
	}
}
