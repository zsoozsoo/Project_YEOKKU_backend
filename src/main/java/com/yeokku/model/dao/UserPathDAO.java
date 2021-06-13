package com.yeokku.model.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.yeokku.model.dto.ConfigPath;
import com.yeokku.model.dto.UserPath;
@Mapper
@Repository
public interface UserPathDAO {

	public void insertUserPath(UserPath userPath); // 경로와 회원을 연결하는 메서드
	
	public int selectLastUserPathId(); // user_path 테이블의 upid (auto-increment 기본키) 마지막 값 가져오는 메서드
	
	public void insertConfigPath(ConfigPath configPath); // config_path 테이블에 경로 구성하는 관광지와 순서 저장하는 메서드

}
