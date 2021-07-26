package com.yeokku.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.yeokku.model.dto.Book;

@Mapper
@Repository
public interface BookDAO {

	public void insertAllCities(List<Book> bookList);
	public List<Book> selectCityList();
	public List<Book> selectCityListByCountry(String country);
}
