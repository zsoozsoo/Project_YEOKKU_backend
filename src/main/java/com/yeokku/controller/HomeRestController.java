package com.yeokku.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
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
import org.springframework.web.bind.annotation.RestController;

import com.yeokku.model.dao.CityDAO;
import com.yeokku.model.dao.CountryDAO;
import com.yeokku.model.dto.City;
import com.yeokku.model.dto.Country;

@CrossOrigin
@RestController
public class HomeRestController {

	@Autowired
	CountryDAO countryDao;
	@Autowired
	CityDAO cityDao;
	
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
			
			final String SERVICE_KEY = "AIzaSyAmrzwR0Tl9B8SI0dIProLdTJBjWtKilAc";
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
				
		cityDao.insertAllCities(cityList);
		System.out.println("city insert completed.");
 	}
}
