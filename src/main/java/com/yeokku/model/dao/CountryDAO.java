package com.yeokku.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.yeokku.model.dto.Country;
@Mapper
@Repository
public interface CountryDAO {

	public List<Country> selectCountryList();

	public List<Country> selectContinentList();

	public List<Country> selectCountryListByCont(String cont);

}
