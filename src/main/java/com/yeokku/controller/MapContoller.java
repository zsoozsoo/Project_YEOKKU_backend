package com.yeokku.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONArray;


@RestController
@RequestMapping("/map")
@CrossOrigin("*")
public class MapContoller {
	// 임시로 지도 표시
	
	@GetMapping(value="/googlemap")
	private ModelAndView googlemap(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("googlemap");
		return mav;
	}
	
	@PostMapping(value="/getLocation")
	@ResponseBody
	private Map<String,Object> getLocation(@RequestParam String data){
		Map<String,Object> result = new HashMap<>();
		
		try {
		    /*JSONArray jsonArray = JSONArray.fromObject(paramData);*/
		    List<Map<String,Object>> positionList = new ArrayList<Map<String,Object>>();
		    positionList = JSONArray.fromObject(data);

		    for (Map<String, Object> position : positionList) {
		        System.out.println(position.get("lat") + " : " + position.get("lng"));
		    }  
		      result.put("result", true);
		  } catch (Exception e) {
		      result.put("result", false);
		  }
		  return result;
	}
}

