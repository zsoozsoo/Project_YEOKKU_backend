package com.yeokku.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.yeokku.model.dao.CityDAO;
import com.yeokku.model.dao.CountryDAO;
import com.yeokku.model.dao.PointDAO;
import com.yeokku.model.dto.City;
import com.yeokku.model.dto.Country;
import com.yeokku.model.dto.Point;

@CrossOrigin
@RestController
public class DBInsertRestController {

	@Autowired
	CountryDAO countryDao;
	@Autowired
	CityDAO cityDao;
	@Autowired
	PointDAO pointDao;
	

	// 도시 DB 저장
	@GetMapping("/insertCities")
	private void insertCities() throws SQLException, IOException {
		
		// 1. 국가 리스트 받아오기
		List<Country> countryList = countryDao.selectCountryList();
		
		System.out.println("selectCountryList 결과 : " + countryList.size());
		
		// 2. 국가 이름으로 검색한 결과 페이지에서 관광도시 이름 크롤링
		List<City> cityList = new ArrayList<>();
		for(Country c : countryList) {

			String countryCode = c.getCountryCode();
			String countryName = c.getCountryName();
			String url = "https://ko.wikipedia.org/wiki/" + URLEncoder.encode("분류", "utf-8") +":"+ URLEncoder.encode(countryName, "utf-8") + URLEncoder.encode("의_도시별_관광지", "utf-8");	
//			url =  URLDecoder.decode(url, "euk-8");
//			System.out.println("url: "+url);
			Document doc = null;

			try {
				doc = Jsoup.connect(url)
						 //Jsoup.connect(url.decodeURL()).get();
						//.header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.82 Safari/537.36").get();
						//.userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
						//.ignoreHttpErrors(true)
						.get();
			} catch(IOException e) {
				//e.printStackTrace();
				continue;
			}
			
			if(doc != null) {
//				System.out.println("doc not null.");
				Elements element = doc.select("div.mw-content-ltr");
				Iterator<Element> cityGroups = element.select("h3").iterator();
				
				System.out.println("===========" + c.getCountryName() + "===========");
				
				while(cityGroups.hasNext()) {
					Element ecityGroups = cityGroups.next();
					String title = ecityGroups.text();
					if(title.equals("+")) continue;
					
					Iterator<Element> cities = ecityGroups.nextElementSibling().select("li").iterator();
					while(cities.hasNext()) {
						Element ecity = cities.next();
						String cityName = ecity.text().substring(2, ecity.text().indexOf('의'));
						
						if(cityName.equals(c.getCountryName())) continue;
						
						System.out.println(cityName);
						cityList.add(new City(cityName, countryCode, countryName));
					}
				}	
			}
			
		}
		System.out.println("도시 개수 : " + cityList.size());
		
		// 3. 구글 지오코딩 API로 도시의 중심 위도, 중심경도 구해서 세팅 (지도에 표시하기 위함)
		for(int i = 0; i < cityList.size(); i++) {
			City c = cityList.get(i);
			String address = c.getCountryName()+"+"+c.getCityName();
			address = address.replace(" ", "+");
			
			final String SERVICE_KEY = "AIzaSyAmrzwR0Tl9B8SI0dIProLdTJBjWtKilAc이부분지워주세요";
			final String urlStr = "https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&key="
					+ SERVICE_KEY;
			
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
				JSONArray positionArray = (JSONArray) jsonObj.get("results");
	
				JSONObject posObject = ((JSONObject) positionArray.get(0));
				JSONObject posObject2 = (JSONObject) posObject.get("geometry");
				JSONObject posObject3 = (JSONObject) posObject2.get("location");
				
				double centerLat = (double) posObject3.get("lat");
				double centerLng = (double) posObject3.get("lng");
				c.setCenterLat(centerLat);
				c.setCenterLng(centerLng);
				
				System.out.println(address+" : " + centerLat + "," + centerLng);
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
				

		}
				
		//주석추가
		//cityDao.insertAllCities(cityList);
		System.out.println("city insert completed.");
 	}
	
	// 관광지 DB저장
	@GetMapping("/insertPlaces")
	private void insertPlaces() throws SQLException, IOException {
		
		// 1. 도시 리스트 받아오기
		List<City> cityList = cityDao.selectCityList();
		
		System.out.println("selectCityList 결과 : " + cityList.size());
		
		// 2. 도시 이름으로 검색한 결과 페이지에서 관광지 이름 크롤링
		List<Point> pointList = new ArrayList<>();
		for(City c : cityList) {

			String cityName = c.getCityName();
			String url = "https://ko.wikipedia.org/wiki/" + URLEncoder.encode("분류", "utf-8") +":"+ URLEncoder.encode(cityName, "utf-8") + URLEncoder.encode("의_관광지", "utf-8");	

			Document doc = null;

			try {
				doc = Jsoup.connect(url).get();
			} catch(IOException e) {
				//e.printStackTrace();
				continue;
			}
			
			if(doc != null) {

				Elements element = doc.select("div#mw-pages");
				Elements element2 = element.select("div.mw-content-ltr");
				Iterator<Element> points = element2.select("li").iterator();
				
//				System.out.println("===========" + cityName + "===========");
				
				while(points.hasNext()) {
					Element point = points.next();
					String pointName = point.text();
					
//					System.out.println(pointName);
					pointList.add(new Point(pointName, cityName));
					
				}	
			}
			
		}
		
		System.out.println("관광지 개수 : " + pointList.size());
		
		for(int i = 0; i < pointList.size(); i++) {
			
			// 3. 구글 지오코딩 API로 관광지의 중심 위도, 중심경도 구해서 세팅 (지도에 표시하기 위함)
			Point p = pointList.get(i);
			String address = p.getCityName() + "+" + p.getPointName();
			
			address = address.replace(" ", "+");
			
			final String SERVICE_KEY = "AIzaSyAmrzwR0Tl9B8SI0dIProLdTJBjWtKilAc";
			String urlStr = "https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&key="
					+ SERVICE_KEY;
			
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
				JSONArray positionArray = (JSONArray) jsonObj.get("results");
	
				if(positionArray.size() == 0) continue;
				
				JSONObject posObject = ((JSONObject) positionArray.get(0));
				JSONObject posObject2 = (JSONObject) posObject.get("geometry");
				JSONObject posObject3 = (JSONObject) posObject2.get("location");
				
				double centerLat = (double) posObject3.get("lat");
				double centerLng = (double) posObject3.get("lng");
				
				// 위도경도로 주소 받아서 저장

				urlStr = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + centerLat +"," + centerLng + "&key="
						+ SERVICE_KEY;
				
				try { // json 파싱
//					System.out.println(p.getPointName()+"get address by lat lng start ==========================");
					url = new URL(urlStr);
					line = "";
		            response = "";
					
					br = new BufferedReader(new InputStreamReader(url.openStream()));
		            while ((line = br.readLine()) != null) {
		            	response = response.concat(line);               
		            }      
					
					jsonParse = new JSONParser();
					jsonObj = (JSONObject) jsonParse.parse(response);
					positionArray = (JSONArray) jsonObj.get("results");
		
					if(positionArray.size() == 0) continue;
					
					posObject = ((JSONObject) positionArray.get(0));
					String address_res =  (String) posObject.get("formatted_address");
					System.out.println(address_res);
					p.setLat(centerLat);
					p.setLng(centerLng);
					
					p.setAddress(address_res);
					
					System.out.println(address+" : " + centerLat + "," + centerLng+ ": "+address_res);
					
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			
			// 4. 상세정보
			
			String pName = p.getPointName();
			pName = pName.replace(" ", "_");
			
			String url = "https://ko.wikipedia.org/wiki/" + URLEncoder.encode(pName, "utf-8");	

			Document doc = null;

			try {
				doc = Jsoup.connect(url).get();
			} catch(IOException e) {
				//e.printStackTrace();
				continue;
			}
			String desc = null;
			if(doc != null) {

				Elements element = doc.select("div.mw-parser-output");
				Element element2 = element.select("p").first();
				if(element2 != null )desc = element2.text();
				System.out.println(pName);
				System.out.println(desc);
			}
			
			
			p.setDesc(desc);

		}

		pointDao.insertAllPlaces(pointList);
		System.out.println("points insert completed.");
	}
	
}
