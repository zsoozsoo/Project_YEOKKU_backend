package com.yeokku.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yeokku.model.dao.UserPathDAO;
import com.yeokku.model.dto.ConfigPath;
import com.yeokku.model.dto.OptPath;
import com.yeokku.model.dto.Point;
import com.yeokku.model.dto.UserPath;

@Service
public class UserPathService {

	@Autowired
	UserPathDAO userpathDao;
	
	@Transactional
	public boolean saveUserPath(String userId, OptPath optpath) {

		String country = optpath.getPointsInOrder().get(0).getCountryName();
		
		// 1. 사용자 아이디와 경로 연결할 데이터 생성
		userpathDao.insertUserPath(new UserPath(userId, country));
		
		// 2.사용자 아이디와 연결된 경로아이디 얻어옴
		int userpathId = userpathDao.selectLastUserPathId();
		
		// 3. 경로구성 테이블에 인서트
		List<Point> pointList = optpath.getPointsInOrder();
		for (int i = 0; i < pointList.size(); i++) {
			userpathDao.insertConfigPath(new ConfigPath(pointList.get(i).getPointId(), i, userpathId));
		}
 
		// 리턴
		return true;
		
	}

//	public List<OptPath> loadUserPath(String userId) {
//		List<OptPath> res = new ArrayList<>();
//		
//		List<Integer> userpathIdList = userpathDao.selectUserPathList(userId);
//		
//		for (int i = 0; i < userpathIdList.size(); i++) {
//			int userpathId = userpathIdList.get(i);
//			
//			ConfigPath cp = userpathDao.selectPoint(userpathId);
//		}
//		
//		return res;
//	}

}
