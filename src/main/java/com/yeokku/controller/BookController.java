package com.yeokku.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yeokku.model.dto.Book;

@RestController
@RequestMapping("/pointinfo")
@CrossOrigin("*")
public class BookController {

	private final String apiKey = "c747ae14f1a86c0cb8501feeeadfac48aac79fe0bf861740785adff5b72703e8";
	
	@GetMapping("/book/{country}")
	public JSONArray getBookList(@PathVariable String country)
			throws IOException {

		String urlStr = "https://www.nl.go.kr/NL/search/openApi/cip.do?key=" + apiKey +"&kwd=" + URLEncoder.encode(country, "utf-8") + "&apiType=json";
		JSONArray resultArray = null;
		System.out.println(urlStr);
		
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
			resultArray = (JSONArray) jsonObj.get("result");

//			int size = resultArray.size();
//			for(int i = 0; i< size ; i++) {
//				JSONObject book = ((JSONObject) resultArray.get(i));
//				
//			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return resultArray;
	}
	
	@GetMapping("/book/detail")
	public JSONObject getBookByIsbn(@RequestBody Book bookInput)
			throws IOException {

		String urlStr = "https://www.nl.go.kr/NL/search/openApi/cip.do?key=" + apiKey +"&kwd=" + URLEncoder.encode(bookInput.getTitle_info(), "utf-8") + "&apiType=json";
		JSONArray resultArray = null;
		System.out.println(urlStr);
		
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
			resultArray = (JSONArray) jsonObj.get("result");

		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return (JSONObject) resultArray.get(0);
	}
	
}
