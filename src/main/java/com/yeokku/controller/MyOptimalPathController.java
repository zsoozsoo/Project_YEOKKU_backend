package com.yeokku.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.yeokku.model.dto.OptPath;
import com.yeokku.model.dto.Point;
import com.yeokku.model.dto.UserPath;
import com.yeokku.model.service.UserPathService;


@CrossOrigin
@RestController // 사용자와 연결하여 DB 저장 및 불러오기
@RequestMapping("/mypath")
public class MyOptimalPathController {
	
	@Autowired
	UserPathService userpathService;
	
	// 경로 저장하기
	@PostMapping("/save/{userId}")
	public String path_save(@PathVariable String userId, @RequestBody OptPath optpath)
			throws IOException {
		
		// =========================== 테스트용 ===========================
		
		List<Point> pointsInOrder = new ArrayList<>();
		pointsInOrder.add(new Point(1631,"독일"));
		pointsInOrder.add(new Point(1634,"독일"));
		pointsInOrder.add(new Point(1633,"독일"));
		pointsInOrder.add(new Point(1635,"독일"));
		pointsInOrder.add(new Point(1632,"독일"));
		
		optpath = new OptPath(pointsInOrder);
		//==============================================================
		
		
		if(userpathService.saveUserPath(userId, optpath)) return "success";
		else return "fail";

	}
	

	// 경로 불러오기 - 아이디
//	@GetMapping("/load/{userId}")
//	public List<OptPath> path_save(@PathVariable String userId)throws IOException {
//		
//		 List<OptPath> res = userpathService.loadUserPath(userId);
//
//	}

}
