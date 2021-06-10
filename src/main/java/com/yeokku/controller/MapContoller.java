package com.yeokku.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

<<<<<<< HEAD
//@Controller
//public class MapContoller {
//	// 임시로 지도 표시
//	@GetMapping("/googlemap")
//	private String googlemap(){
//		
//		return "googlemap";
//	}
//}
=======
@Controller
public class MapContoller {
	// 임시로 지도 표시
	@RequestMapping(value = "/googlemap", method = RequestMethod.GET)
	private String googlemap(){
		return "googlemap";
	}
}
>>>>>>> branch 'main' of https://github.com/4COLORPENS/YEOKKU-backend.git
