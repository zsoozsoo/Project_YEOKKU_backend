package com.yeokku.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MapContoller {
	// 임시로 지도 표시
	@RequestMapping(value = "/googlemap", method = RequestMethod.GET)
	private String googlemap(){
		return "googlemap";
	}
}
