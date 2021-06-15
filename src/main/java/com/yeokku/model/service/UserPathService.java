package com.yeokku.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yeokku.model.dao.ConfigPathDAO;
import com.yeokku.model.dao.UserPathDAO;
import com.yeokku.model.dto.ConfigPath;
import com.yeokku.model.dto.OptPath;
import com.yeokku.model.dto.Point;
import com.yeokku.model.dto.UserPath;

@Service
public class UserPathService {

	@Autowired
	UserPathDAO userpathDao;
	@Autowired
	ConfigPathDAO configpathDao;
	
	@Transactional
	public boolean saveUserPath(String userId, OptPath optpath) {
		
		// 1. 사용자 아이디와 경로 연결할 데이터 생성
		optpath.setUserId(userId);
		userpathDao.insertUserPath(optpath);
		
		// 2.사용자 아이디와 연결된 경로아이디 얻어옴
		int userpathId = userpathDao.selectLastUserPathId();
		
		// 3. 경로구성 테이블에 인서트
		List<Point> pointList = optpath.getPointsInOrder();
		for (int i = 0; i < pointList.size(); i++) {
			Point p = pointList.get(i);
			configpathDao.insertConfigPath(new ConfigPath(p.getPointId(), i, userpathId, p.getPointName()));
		}
 
		// 리턴
		return true;	
	}

	public List<OptPath> loadUserPath(String userId) {
		
		List<OptPath> result = new ArrayList<>();
		List<OptPath> userpathList = userpathDao.selectUserPathListById(userId);

		for (int i = 0; i < userpathList.size(); i++) {
			OptPath op = userpathList.get(i);
			op.setPointsInOrder(makePath(configpathDao.selectConfigPathList(op.getUserPathId())));
			result.add(op);
		}
		
		return result;
	}

	public List<OptPath> loadUserPath(String userId, String countryName) {
		
		List<OptPath> result = new ArrayList<>();
		Map<String,String> params = new HashMap<>();
		
		params.put("userId",userId);
		params.put("countryName",countryName);
		
		List<OptPath> userpathList = userpathDao.selectUserPathListByIdCountry(params);

		for (int i = 0; i < userpathList.size(); i++) {
			OptPath op = userpathList.get(i);
			op.setPointsInOrder(makePath(configpathDao.selectConfigPathList(op.getUserPathId())));
			result.add(op);
		}
		
		return result;
	}

	private List<Point> makePath(List<ConfigPath> cpList) {
		
		List<Point> pointsInOrder = new ArrayList<>();
		
		for (int i = 0; i < cpList.size(); i++) {
			ConfigPath cp = cpList.get(i);
			pointsInOrder.add(new Point(cp.getPointName(),cp.getPointLat(),cp.getPointLng(),cp.getDescription()));
		}
		
		return pointsInOrder;
	}
}
