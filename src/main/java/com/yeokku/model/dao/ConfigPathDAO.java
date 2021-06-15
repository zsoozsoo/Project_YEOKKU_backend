package com.yeokku.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.yeokku.model.dto.ConfigPath;

@Mapper
@Repository
public interface ConfigPathDAO {
	
	public void insertConfigPath(ConfigPath configPath); // config_path 테이블에 경로 구성하는 관광지와 순서 저장하는 메서드
	
	public List<ConfigPath> selectConfigPathList(int userpathId);

}
