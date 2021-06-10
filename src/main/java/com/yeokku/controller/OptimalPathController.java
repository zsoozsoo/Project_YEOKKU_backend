package com.yeokku.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yeokku.model.dto.Point;

@CrossOrigin
@RestController
public class OptimalPathController {

	// 관광지 배열(시작점) 받아서 최적경로(순환)를 구성하는 순서대로 배열 반환
	@GetMapping("/optpath_round/{type}/{mode}")
	private List<Point> optpath_round(@PathVariable String type,@PathVariable String mode/*, @RequestBody List<Point> input*/) throws IOException {
		
		// ======================================= 테스트 후 주석처리해야 할 부분 시작
		
		List<Point> input = new ArrayList<>();
		
		input.add(new Point("관광지1",48.2038016,16.3617874));
		input.add(new Point("관광지2",48.2033369,16.3586166));
		input.add(new Point("관광지3",48.2404153,16.3868931));
		input.add(new Point("관광지4",48.2167398,16.3980327));
		input.add(new Point("관광지5",48.0927289,15.9518237));
		
		// ======================================== 테스트 후 주석처리해야 할 부분 끝
		
		Point start = input.get(0); //시작점
		List<Point> output = new ArrayList<>();
		
		// 1. ================== 거리 계산해서 인접행렬 구성 - google distance api =============================	
		
		double[][] W = new double[10][10];
		
		for(int i = 0; i < input.size(); i++) {
			Point origin = input.get(i);
			Point dest = input.get((i + 1) % input.size());
			final String SERVICE_KEY = "AIzaSyC6HRafHB4tQDc-GSCPbwTnybYJLNybxDw";
			final String urlStr = "https://maps.googleapis.com/maps/api/distancematrix/json?mode="+mode+"&origins=" + origin.getLat() +"," + origin.getLng() 
				+"&destinations=" + dest.getLat() + "," + dest.getLng()
				+ "&key=" + SERVICE_KEY;
			
//			final String urlStr = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=Washington,DC&destinations=New+York+City,NY&key="+SERVICE_KEY;
			
			try { // json 파싱
				URL url = new URL(urlStr);
				String line = "";
	            String response = "";
				
				BufferedReader br;
	            br = new BufferedReader(new InputStreamReader(url.openStream()));
	            while ((line = br.readLine()) != null) {
	            	response = response.concat(line);               
	            }      
				
				JSONParser jsonParse = new JSONParser();
				JSONObject jsonObj = (JSONObject) jsonParse.parse(response);
				JSONArray positionArray = (JSONArray) jsonObj.get("rows");
	
				JSONObject posObject = ((JSONObject) positionArray.get(0));
				JSONArray posObject2 = (JSONArray) posObject.get("elements");
				JSONObject posObject3 =  ((JSONObject) posObject2.get(0));
				if(posObject3.get("status").equals("ZERO_RESULTS")) {
					System.out.println("경로가 존재하지 않습니다.");
					continue;
					// throw new Exception("연결되지 않은 관광지 예외 발생.");
				}
				JSONObject posObject4;
				
				if(type.equals("distance")) posObject4 = (JSONObject) posObject3.get("distance");
				else posObject4 = (JSONObject) posObject3.get("duration");
				
				long res = (long) posObject4.get("value");
				
				W[i][i+1] = res;
				
				if(type.equals("distance")) System.out.println("distance between " + origin.getPointName()+" and " + dest.getPointName()+ " :" + res);
				else System.out.println("duration between " + origin.getPointName()+" and " + dest.getPointName()+ " :" + res);
				
			} catch (ParseException e) {
				e.printStackTrace();
				continue;
			}
				
		}
		
		// ========================== 2. 외판원순회 알고리즘 활용 ============================
		
		
		
		
		
		return output;
	}
	
	// 관광지 배열(시작점,끝점) 받아서 최적경로(비순환)를 구성하는 순서대로 배열 반환
//	@GetMapping("/optpath_oneway/{type}")
//	private List<Point> optpath_oneway(@PathVariable String type, @RequestBody List<Point> input) {
//		
//		
//	}
}
