package com.yeokku.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yeokku.model.dto.City;
import com.yeokku.model.dto.Country;
import com.yeokku.model.dto.ScrapMovie;
import com.yeokku.model.dto.ScrapMusic;

import io.swagger.annotations.ApiOperation;


//http://localhost:9999/vue/swagger-ui.html
@RestController
@RequestMapping("/pointinfo")
@CrossOrigin("*")

public class PointInfoController {
	
	//영화 검색 후 , 영화목록 들어올 영화리스트 전역변수
	List<ScrapMovie> movieList;
	
	//영화 ( KMDB API 사용 )
	@GetMapping("/movie/{nation}")
	@ApiOperation("나라별 영화 종류 출력")
	public List<ScrapMovie> PointMovie(@PathVariable String nation) {
		
		movieList = new ArrayList<ScrapMovie>();
		
	        try {
	        	String API_KEY = "7Z770626Z954T1WCK808";
	        	StringBuilder urlBuilder = new StringBuilder("http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2");
	        	urlBuilder.append("&listCount"+"="+10);
	        	//서비스 키
	        	urlBuilder.append("&"+URLEncoder.encode("ServiceKey","UTF-8")+"="+API_KEY);
	        	//상영 국가
	        	urlBuilder.append("&detail="+"N"); 
	        	urlBuilder.append("&nation"+"="+nation); 
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
				
				
				JSONArray resultArray = (JSONArray) resultObject.get("Result");
				
				for (int i = 0; i < resultArray.size(); i++) {
					JSONObject movieObject = (JSONObject) resultArray.get(i);
					//순서대로 영화아이디, 영화제목, 개봉년도, 제작국가, 러닝타임, 관람등급, 장르, 상세정보 연결 url
					String movieID = (String) movieObject.get("DOCID");
					String movieTitle = (String) movieObject.get("title");
					String prodYear = (String) movieObject.get("prodYear");
					String movieNation = (String) movieObject.get("nation");
					String runtime = (String) movieObject.get("runtime");
					String rating = (String) movieObject.get("rating");
					String genre = (String) movieObject.get("genre");
					String infoUrl = (String) movieObject.get("kmdbUrl");
					//감독
					JSONObject directorsObject = (JSONObject) movieObject.get("directors");
					JSONArray directorList = (JSONArray) directorsObject.get("director");
					JSONObject director = (JSONObject) directorList.get(0);
					String directorName = (String) director.get("directorNm");
					
					// 배우 목록 리스트
					JSONObject actorObject = (JSONObject) movieObject.get("actors");
					JSONArray actorsArr = (JSONArray) actorObject.get("actor");
					List<String> actors = new ArrayList<>();
					for (int j = 0; j < actorsArr.size(); j++) {
						JSONObject actor = (JSONObject) actorsArr.get(j);
						String actorName = (String) actor.get("actorNm");
						actors.add(actorName);
					}
					
					String company = (String) movieObject.get("company");
					//영화 플롯
					JSONObject plotsObject = (JSONObject) movieObject.get("plots");
					JSONArray plotArr = (JSONArray) plotsObject.get("plot");
					JSONObject plotObject = (JSONObject) plotArr.get(0);
					String plot = (String) plotObject.get("plotText");

					ScrapMovie scrapMovie = new ScrapMovie(movieID,movieTitle,prodYear,movieNation,runtime,rating,genre,infoUrl,directorName,actors,company,plot);
					movieList.add(scrapMovie);
;				}
				
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	        
			return movieList;
	        
		
	}
	
	//영화 상세정보 ( movie detail )
		@GetMapping("/movie/detail/{movieID}")
		@ApiOperation("나라별 영화 종류 출력")
		public ScrapMovie PointMovieDetail(@PathVariable String movieID) {
			
			boolean check = false; // movieList에 검색한 영화가 있는지 없는지 확인하기 위한 변수
			
			if(movieList.size()==0) System.out.println("데이터가 들어오지 않았습니다.");
			else {
				for (int i = 0; i < movieList.size(); i++) {
					
					if(movieList.get(i).getMovieID().equals(movieID)) {
						check = true;
						ScrapMovie scrapMovie = movieList.get(i);
						String infoUrl = scrapMovie.getInfoUrl();
						
						// 상세정보 페이지 크롤링 ( 이미지 얻기 위해서 ) -----------------------
						String url = infoUrl ;
						Document doc = null;
						try {
							doc = Jsoup.connect(url).get();
						} catch(IOException e) {
							e.printStackTrace();
						}
						
						if(doc != null) {
							//기본값이 이미지 없을때로
							String imageUrl = null;
							
							Elements element = doc.select("div.mImg1");
							Elements elements = element.select("span");
							Element image = elements.get(0);
							String attr = image.attr("style");
							
							//이미지가 있을 때 ( http://로 시작하는 이미지 주소를 갖고있을 때 )
							if(attr.indexOf("http://")>0) {
								imageUrl = attr.substring( attr.indexOf("http://"), attr.indexOf("')") );
							}
							
							//이미지 없는 경우 기본값으로 set!
							scrapMovie.setImageUrl(imageUrl);
							
						}
						// 크롤링 끝 ----------------------------------------------
						
						return scrapMovie;
					}
										
				}
			}
			
			return null;
	
		}
	
	
	// 음악 ( SPOTIFY CHART 크롤링 )
	@GetMapping("/music/{nation}")
	@ApiOperation("나라별 음악 데일리 TOP100 차트 출력")
	private List<ScrapMusic> PointMusic(@PathVariable String nation) throws SQLException, IOException {
		
			String url = "https://spotifycharts.com/regional/" +nation + "/daily/latest"	;
//			url =  URLDecoder.decode(url, "euk-8");
//			System.out.println("url: "+url);
			Document doc = null;
			// 1. 노래정보들 넣어줄 "ScrapMusic"객체의 리스트 생성
			List<ScrapMusic> musicList = new ArrayList<>();

			try {
				// 2. spotify chart 크롤링
				doc = Jsoup.connect(url).get();
			} catch(IOException e) {
				e.printStackTrace();
			}
			
			//System.out.println(doc);
			if(doc != null) {
//				System.out.println("doc not null.");
				Elements element = doc.select("tbody");
				// 3. 노래 한곡의 정보들의 리스트 뽑아내기
				Iterator<Element> musicGroups = element.select("tr").iterator();
				
				// 4. TOP 100개만 뽑아내기
				for (int i = 0; i < 100; i++) {
					
					// 5. 1개의 곡 정보
					Element songInfo = musicGroups.next();
					
					// 6. 노래제목, 가수 뽑기 ( Elements에 노래제목과 가수가 들어가 있음 )
					Elements song = songInfo.select("td.chart-table-track");
						// 노래제목, 가수
					String songTitle = song.get(0).getElementsByTag("strong").text();
					String singer = song.get(0).getElementsByTag("span").text();
					 
					// 7. 앨범 자켓 사진 링크 , 앨범 정보 링크 뽑기 ( Elements에 앨범 이미지 링크 앨범 정보 링크가 들어가 있음 )
					Elements album = songInfo.select("td.chart-table-image");
						// 앨범 이미지 링크, 앨범 상세정보 링크 (spotify사이트연결)
					String albumImageLink = album.get(0).getElementsByTag("a").attr("href");
					String albumInfoLink = album.get(0).getElementsByTag("img").attr("src");
					
					// 8. 노래 순위 뽑기
					String rank = songInfo.select("td.chart-table-position").get(0).text();
					
					musicList.add(new ScrapMusic(songTitle, singer, albumImageLink, albumInfoLink, rank));
				}
				
			}
			return musicList;
 	}
	
	
}

