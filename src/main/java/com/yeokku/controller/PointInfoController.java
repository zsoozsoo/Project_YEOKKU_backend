package com.yeokku.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pointinfo")
@CrossOrigin("*")
public class PointInfoController {
	

			
	@GetMapping("/movie/{nation}")
	public void PointMovie(@PathVariable String nation) {
		
	        try {
	        	String API_KEY = "7Z770626Z954T1WCK808";
	        	StringBuilder urlBuilder = new StringBuilder("http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2");
	        	//서비스 키
	        	urlBuilder.append("&"+URLEncoder.encode("ServiceKey","UTF-8")+"="+API_KEY);
	        	//상영 국가
	        	urlBuilder.append("&nation="+nation); 
//	        	//상영년도
//	        	urlBuilder.append("&" + URLEncoder.encode("val001","UTF-8") + "=" + URLEncoder.encode("2018", "UTF-8")); 
//	        	//상영 월
//	        	urlBuilder.append("&" + URLEncoder.encode("val002","UTF-8") + "=" + URLEncoder.encode("01", "UTF-8")); 
	      
	        	System.out.println(urlBuilder);
	        	URL url = new URL(urlBuilder.toString());
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.setRequestMethod("GET");
	            conn.setRequestProperty("Content-type", "application/json");
	            
	            System.out.println("Response code: " + conn.getResponseCode()); 
	            BufferedReader rd; 
	            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) { 
	            	rd = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
	            } else { 
	            	rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	            } 
	            
	            StringBuilder sb = new StringBuilder(); 
	            String line; 
	            String returnLine;
	            while ((line = rd.readLine()) != null) {
	            	sb.append(line);
	            };
	            rd.close(); 
	            conn.disconnect(); 
	            
	            //최종 json
	            returnLine = sb.toString();
	            
	            // ----------- Json 파싱부분 -----------
	            JSONParser jsonParse = new JSONParser();
				JSONObject jsonObj = (JSONObject) jsonParse.parse(returnLine);
				JSONArray dataArray = (JSONArray) jsonObj.get("Data");
				JSONObject resultObject = (JSONObject) dataArray.get(0);
				
				//총 결과 수
				long totalResult = (long) resultObject.get("TotalCount");
				
				System.out.println(totalResult);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		
	}
	
	@GetMapping("/book/{nation}")
	public void PointBook(@PathVariable String nation) {
		
	        try {
	        	String API_KEY = "";
	        	StringBuilder urlBuilder = new StringBuilder("https://www.nl.go.kr/NL/search/openApi/search.do?");
	        	//서비스 키
	        	urlBuilder.append(URLEncoder.encode("key","UTF-8")+"="+API_KEY);
	        	//상영 국가
	        	urlBuilder.append("&nation="+nation); 
//	        	//상영년도
//	        	urlBuilder.append("&" + URLEncoder.encode("val001","UTF-8") + "=" + URLEncoder.encode("2018", "UTF-8")); 
//	        	//상영 월
//	        	urlBuilder.append("&" + URLEncoder.encode("val002","UTF-8") + "=" + URLEncoder.encode("01", "UTF-8")); 
	      
	        	System.out.println(urlBuilder);
	        	URL url = new URL(urlBuilder.toString());
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.setRequestMethod("GET");
	            conn.setRequestProperty("Content-type", "application/json");
	            
	            System.out.println("Response code: " + conn.getResponseCode()); 
	            BufferedReader rd; 
	            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) { 
	            	rd = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
	            } else { 
	            	rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	            } 
	            
	            StringBuilder sb = new StringBuilder(); 
	            String line; 
	            String returnLine;
	            while ((line = rd.readLine()) != null) {
	            	sb.append(line);
	            };
	            rd.close(); 
	            conn.disconnect(); 
	            
	            //최종 json
	            returnLine = sb.toString();
	            
	            // ----------- Json 파싱부분 -----------
	            JSONParser jsonParse = new JSONParser();
				JSONObject jsonObj = (JSONObject) jsonParse.parse(returnLine);
				JSONArray dataArray = (JSONArray) jsonObj.get("Data");
				JSONObject resultObject = (JSONObject) dataArray.get(0);
				
				//총 결과 수
				long totalResult = (long) resultObject.get("TotalCount");
				
				System.out.println(totalResult);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		
	}
	
	
}

