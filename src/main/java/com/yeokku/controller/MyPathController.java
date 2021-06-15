package com.yeokku.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yeokku.model.dto.OptPath;
import com.yeokku.model.service.UserPathService;


@CrossOrigin
@RestController // 사용자와 연결하여 DB 저장 및 불러오기
@RequestMapping("/mypath")
public class MyPathController {
	
	@Autowired
	UserPathService userpathService;
	
	// 경로 저장하기
	@PostMapping("/save/{userId}")
	public String path_save(@PathVariable String userId, @RequestBody OptPath optpath)
			throws IOException {
		
		userpathService.saveUserPath(userId, optpath);
		return "success";
	}
	
	// 경로 불러오기 - 아이디
	@GetMapping("/load/{userId}")
	public List<OptPath> path_load(@PathVariable String userId) throws IOException {
		
		 return userpathService.loadUserPath(userId);
	}
	
	// 경로 불러오기 - 아이디 + 국가
	@GetMapping("/load/{userId}/{countryName}")
	public List<OptPath> path_load(@PathVariable String userId, @PathVariable String countryName) throws IOException {
		
		 return userpathService.loadUserPath(userId,countryName);
	}

}
