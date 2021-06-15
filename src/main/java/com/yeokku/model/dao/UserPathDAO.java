package com.yeokku.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.yeokku.model.dto.ConfigPath;
import com.yeokku.model.dto.OptPath;
import com.yeokku.model.dto.UserPath;
@Mapper
@Repository
public interface UserPathDAO {

	public void insertUserPath(OptPath optpath); // 경로와 회원을 연결하는 메서드
	
	public int selectLastUserPathId(); // user_path 테이블의 upid (auto-increment 기본키) 마지막 값 가져오는 메서드

	public List<OptPath> selectUserPathListById(String userId);

	public List<OptPath> selectUserPathListByIdCountry(Map<String, String> params);


}
