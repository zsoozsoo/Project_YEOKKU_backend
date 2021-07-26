package com.yeokku.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yeokku.model.dto.ConfigPathInput;
import com.yeokku.model.dto.DetailDirection;
import com.yeokku.model.dto.OptPath;
import com.yeokku.model.dto.OptPathPoint;
import com.yeokku.model.dto.Point;
import com.yeokku.model.dto.Step;

@CrossOrigin
@RestController
@RequestMapping("/path")
public class PathController {

	static int N, minCost2;
	static int[][] W, W2, D, P;
	static boolean[][] noTransitPath = new boolean[11][11];
	static List<Integer> OptPath;
	static Map<Integer, Point> map;
	static int visitedAllCity;
	static final int INF = Integer.MAX_VALUE;
	static int maxNum = 0;
	static int minCost1;

	// 관광지 배열(시작점) 받아서 최적경로(순환)를 구성하는 순서대로 배열 반환
	@PostMapping("/round/{mode}")
	public List<OptPath> optpath_round(@PathVariable String mode, @RequestBody ConfigPathInput cpinput)
			throws IOException {
		List<Point> input = null;
		if(cpinput.getPointList()==null)input= new ArrayList<>();
		else input = cpinput.getPointList();
		input.add(0, cpinput.getStart());
		
		List<OptPath> optPathList = new ArrayList<>();
		
		optPathList.add(configOptPathRound(mode, "distance", input,cpinput.getCountryName()));
		optPathList.add(configOptPathRound(mode, "duration", input,cpinput.getCountryName()));

		return optPathList;
	}
	// 관광지 배열(시작점) 받아서 최적경로(순환)를 구성하는 순서대로 배열 반환
		@PostMapping("/oneway/{mode}")
		public List<OptPath> optpath_oneway(@PathVariable String mode, @RequestBody ConfigPathInput cpinput)
				throws IOException {
			List<Point> input = null;
			if(cpinput.getPointList()==null)input= new ArrayList<>();
			else input = cpinput.getPointList();
			input.add(0, cpinput.getStart());
//			input.add(input.size(), cpinput.getEnd());
			
			List<OptPath> optPathList = new ArrayList<>();
			
			optPathList.add(configOptPathOneway(mode, "distance", input,cpinput.getCountryName()));
			optPathList.add(configOptPathOneway(mode, "duration", input,cpinput.getCountryName()));

			return optPathList;
		}

	private OptPath configOptPathOneway(String mode, String type, List<Point> input,String countryName) throws IOException {
		
		long startTime = System.currentTimeMillis();
		// ======================================= 테스트 후 주석처리해야 할 부분 시작 (오스트리아 빈의 관광지 5곳의 위치 정보 임의로 넣음)

//		List<Point> input = new ArrayList<>();
//		
//		input.add(new Point("관광지1", 48.2038016, 16.3617874));
//		input.add(new Point("관광지2", 48.2033369, 16.3586166));
//		input.add(new Point("관광지3", 48.2404153, 16.3868931));
//		input.add(new Point("관광지4", 48.2167398, 16.3980327));
//		input.add(new Point("관광지5", 48.0927289, 15.9518237));

		// ======================================== 테스트 후 주석처리해야 할 부분 끝

		N = input.size();	
		visitedAllCity = (1 << N) - 1;
		W = new int[11][11];
		W2 = new int[11][11];
		D = new int[65537][11];
		P = new int[65537][11];
//		noTransitPath = new boolean[11][11];
		OptPath = new ArrayList<>();
		minCost1 = 0;
		minCost2 = 0;
		
		List<Point> output = new ArrayList<>();
		map = new HashMap<>();

		for (int i = 0; i < input.size(); i++) {
			map.put(i, input.get(i));
		}

		// ================== 1. 모드(자동차,자전거,도보)에 따라 거리 또는 시간 값으로 인접행렬 구성 - google
		// distance api =============================

		String message = null;
		for (int i = 0; i < input.size(); i++) {
			for (int j = i+1; j < input.size(); j++) {

				if(i == j) continue;
				
				message = "";

				Point origin = input.get(i);
				Point dest = input.get(j);

				final String SERVICE_KEY = "AIzaSyCt4KmDVjM1tPcZlSZPu_QmY7GvFVP5vQI";
				String urlStr = "https://maps.googleapis.com/maps/api/distancematrix/json?mode=" + mode + "&origins="
						+ origin.getAddress().replace(" ","+") + "&destinations=" + dest.getAddress() .replace(" ","+")
						+ "&key=" + SERVICE_KEY;

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
					JSONArray positionArray = (JSONArray) jsonObj.get("rows");

					JSONObject posObject = ((JSONObject) positionArray.get(0));
					JSONArray posObject2 = (JSONArray) posObject.get("elements");
					JSONObject posObject3 = ((JSONObject) posObject2.get(0));

					if (posObject3.get("status").equals("ZERO_RESULTS")) {
//						System.out.println(mode + "모드에서 " + origin.getPointName() + "로부터 " + dest.getPointName()
//								+ "로의 경로가 존재하지 않습니다.");

//						noTransitPath[i][j] = true;
						message = "하나 이상의 구간에 대중교통정보가 존재하지 않아 자동차 모드가 적용된 경로입니다.";
						
						// 대중교통 경로 없으면 자동차 경로로 찾음
						urlStr = "https://maps.googleapis.com/maps/api/distancematrix/json?mode=driving" + "&origins="
								+ origin.getAddress().replace(" ","+") + "&destinations=" + dest.getAddress().replace(" ","+") + "&key=" + SERVICE_KEY;

						try {
							URL url_alt = new URL(urlStr);
							String line_alt = "";
							String response_alt = "";

							BufferedReader br_alt;
							br_alt = new BufferedReader(new InputStreamReader(url_alt.openStream()));
							while ((line_alt = br_alt.readLine()) != null) {
								response_alt = response_alt.concat(line_alt);
							}

							JSONParser jsonParse_alt = new JSONParser();
							JSONObject jsonObj_alt = (JSONObject) jsonParse_alt.parse(response_alt);
							JSONArray positionArray_alt = (JSONArray) jsonObj_alt.get("rows");

							JSONObject posObject_alt = ((JSONObject) positionArray_alt.get(0));
							JSONArray posObject2_alt = (JSONArray) posObject_alt.get("elements");
							posObject3 = ((JSONObject) posObject2_alt.get(0));

						} catch (ParseException e) {
							e.printStackTrace();
							continue;
						}
					}

					JSONObject posObject4;
					long res = 0, res2 = 0;

					if (type.equals("distance")) {
						posObject4 = (JSONObject) posObject3.get("distance");
						res = (long) posObject4.get("value");
						posObject4 = (JSONObject) posObject3.get("duration");
						res2 = (long) posObject4.get("value");

					} else {
						posObject4 = (JSONObject) posObject3.get("duration");
						res = (long) posObject4.get("value");
						posObject4 = (JSONObject) posObject3.get("distance");
						res2 = (long) posObject4.get("value");
					}

					W[i][j] = W[j][i] = (int) res; // 주 인접행렬 (외판원순회에 실제로 쓰임)
					W2[i][j] = W2[j][i] = (int) res2; // 부 인접행렬 (외판원순회에 쓰이지 않으나 최적경로 구성 후 계산에 사용됨)

//					if (type.equals("distance")) {
//						System.out.println("distance between " + origin.getPointName() + " and " + dest.getPointName()
//								+ " :" + res);
//						System.out.println("duration between " + origin.getPointName() + " and " + dest.getPointName()
//								+ " :" + res2);
//					} else {
//						System.out.println("duration between " + origin.getPointName() + " and " + dest.getPointName()
//								+ " :" + res);
//						System.out.println("distance between " + origin.getPointName() + " and " + dest.getPointName()
//								+ " :" + res2);
//					}

				} catch (ParseException e) {
					e.printStackTrace();
					continue;
				}
			}

		}

		// ========================== 2. 그리디 알고리즘 활용 ============================

//		        for(int i=0;i<65537;i++){
//		            Arrays.fill(P[i],-1);
//		        }

		// 총 비용(선택기준에 따라 달라짐 - 이동거리 또는 소요시간)
		shortest(1, 0);
		System.out.println("min cost: " + minCost1);

		// 최적경로를 구성하는 관광지별 순서 추적 (P배열 이용) + 부가정보 총 비용 계산(거리 선택한 경우 시간, 시간 선택한 경우 거리)
//		OptPath.add(0);
//		OptPath.add(P[0][0]);
//		minCost2 = W2[0][P[0][0]];

//		tracePath(P[0][0], 1 << P[0][0]);
//		System.out.println("min cost2: " + minCost2);
//
		for (int i = 0; i < OptPath.size(); i++) {
			output.add(map.get(OptPath.get(i)));
		}

		// .....3. 흔적

		// ============================ 4. 단위변환====================================
		OptPath optPath = null;

		if (type.equals("distance")) {
			double distance = minCost1 / 1000;
			int hour = minCost2 / 3600;
			int min = (minCost2 % 3600) / 60;
			
			if(hour==0)optPath = new OptPath("비순환", type, output, distance + "km",  min + "분", mode, message,countryName);
			else optPath = new OptPath("비순환", type, output, distance + "km", hour + "시간 " + min + "분", mode, message,countryName);

		} else {
			int hour = minCost1 / 3600;
			int min = (minCost1 % 3600) / 60;
			double distance = minCost2 / 1000;

			if(hour==0) optPath = new OptPath("비순환", type, output, distance + "km",  min + "분", mode, message,countryName);
			optPath = new OptPath("비순환", type, output,  distance + "km",hour + "시간 " + min + "분", mode, message,countryName);
		}
		long endTime = System.currentTimeMillis();
		System.out.println("걸린시간:"+(endTime-startTime));
		return optPath;
		
	}
	private static void shortest(int visited, int curr) {
//      System.out.println("curr: " + curr + ", visited:" + (visited));
      OptPath.add(curr);
		if (visited == visitedAllCity) {
			return;
		}	
		
		
		int min = Integer.MAX_VALUE;
		int minNode = -1;
		for (int i = 0; i < N; i++) {		

			if((visited & (1<<i)) != 0) continue; //	이미 방문한 노드는 고려하지 않음
			
			if (W[curr][i] < min) { // 최소 비용, 최소로 만드는 정점 인덱스 저장하는 코드
				min = W[curr][i];
				minNode = i;
			}
		}
		minCost1 += W[curr][minNode];
		minCost2 += W2[curr][minNode];
		shortest(visited | (1<<minNode),minNode);
	}
	@PostMapping("/oneway_2/{mode}")	
	private OptPath configOptPathOneWay2(@PathVariable String mode, @RequestBody ConfigPathInput cpinput) throws IOException {

		long startTime = System.currentTimeMillis();
		
		Map<String,Point> map = new HashMap<>();
		map.put(cpinput.getStart().getAddress(),cpinput.getStart());
		map.put(cpinput.getEnd().getAddress(),cpinput.getEnd());
		
		if(cpinput.getPointList()!= null) {
			
			for (int i = 0; i < cpinput.getPointList().size(); i++) {
				Point p = cpinput.getPointList().get(i);
				map.put(p.getAddress(), p);
			}
		}
		
		final String SERVICE_KEY = "AIzaSyCt4KmDVjM1tPcZlSZPu_QmY7GvFVP5vQI";
		
		String urlStr = "https://maps.googleapis.com/maps/api/directions/json?"
//				+ "&origin=" + cpinput.getStart().getLat() + "," + cpinput.getStart().getLng()
//				+ "&destination=" + cpinput.getEnd().getLat() + "," + cpinput.getEnd().getLng();
				+ "&origin=" + cpinput.getStart().getPointName().replace(" ", "+")
				+ "&destination=" + cpinput.getEnd().getPointName().replace(" ", "+");
		
				if(cpinput.getPointList() == null) {

					urlStr += "&mode" + mode + "&key=" + SERVICE_KEY;
				}else {

					int size = cpinput.getPointList().size();
					urlStr += "&waypoints=optimize:true|";
					for(int i=0;i<size;i++) {		
						urlStr += cpinput.getPointList().get(i).getPointName().replace(" ", "+") + "|";
					}
					
					urlStr = urlStr.substring(0, urlStr.length()-1); // 마지막 파이프라인 제거
					urlStr += "&mode="+mode + "&key=" + SERVICE_KEY;
				}
				
		System.out.println(urlStr);
		String countryName = cpinput.getCountryName();
		OptPath optPath = null;
		try { // json 파싱

			URL url = new URL(urlStr);
			String line = "";
			String response = "";
			String msg = "";

			BufferedReader br;
			br = new BufferedReader(new InputStreamReader(url.openStream()));
			while ((line = br.readLine()) != null) {
				response = response.concat(line);
			}
			
			JSONParser jsonParse = new JSONParser();
			JSONObject jsonObj = (JSONObject) jsonParse.parse(response);
			JSONArray routesArray = (JSONArray) jsonObj.get("routes");
			
			
			if(mode.equals("transit") && routesArray.size()==0) msg = "정보가 없습니다. 도보나 운전 정보를 불러옵니다." ;
				
			// 여러 경로 for문으로 돌리기
			JSONObject subJsonObject = (JSONObject) routesArray.get(0);
			JSONArray legsArray = (JSONArray) subJsonObject.get("legs");
			
			//시작점, 종점, 경유지 포함한 모든 지점 리스트
			List<Point> pointsInOrder = new ArrayList<Point>();
			
			//총 걸리는 거리와 시간 더해주기
			long totalDistance = 0, totalDuration = 0;
			
			for (int i = 0; i < legsArray.size(); i++) {
				JSONObject legsObject = ((JSONObject) legsArray.get(i));
				
				// -------------------- 시작점,끝점 정보 --------------------
				Point point = new Point();
				
				//지점 간 거리
				JSONObject distanceObject = (JSONObject) legsObject.get("distance");
				long legDistance = (long) distanceObject.get("value");
				//총 거리에 더해주기
				totalDistance += legDistance;
				
				//지점 간 걸리는 시간
				JSONObject durationObject = (JSONObject) legsObject.get("duration");
				long legDuration = (long) durationObject.get("value");
				//총 걸리는 시간에 더해주기
				totalDuration += legDuration;
				
				//시작점 이름
				String startName = (String) legsObject.get("start_address");
				String[] split = startName.split(",");
				String startCityName = split[2];
				//시작점 위도,경도
				JSONObject startObject = (JSONObject) legsObject.get("start_location");
				Double startLat = (Double) startObject.get("lat");
				Double startLng = (Double) startObject.get("lng");
				//끝점 이름
				String endName = (String) legsObject.get("end_address");
				String[] split2 = endName.split(",");
				String endCityName = split[2];
				//끝점 위도,경도
				JSONObject endObject = (JSONObject) legsObject.get("end_location");
				Double endLat = (Double) endObject.get("lat");
				Double endLng = (Double) endObject.get("lng");
				
				//시작점, 종점, 경유지 포함한 모든 지점 리스트에 정보 넣기
				
				//처음 시작점만 시작점이랑 끝점 둘 다 넣기
//				if(i==0) pointsInOrder.add(new Point(startName,startCityName,startLat,startLng));
				if(i==0) {
					pointsInOrder.add(cpinput.getStart());
				}
				//보통은 끝점만 넣기
//				pointsInOrder.add(new Point(endName,endCityName,endLat,endLng));
	
				pointsInOrder.add(map.get(endName));
				if(i==legsArray.size()-1) {
					pointsInOrder.add(cpinput.getEnd());
				}
				
			}
			
			// -------------------- 경로(polyline) --------------------
			JSONObject polylineObject = (JSONObject) subJsonObject.get("overview_polyline");
			String polyline = (String) polylineObject.get("points");
			
			// -------------------- 방문순서 --------------------
			JSONArray waypointOrder = (JSONArray) subJsonObject.get("waypoint_order");
//			System.out.print("방문순서 ");
//			for (int i = 0; i <waypointOrder.size(); i++) {
//				System.out.print(waypointOrder.get(i)+" ");
//			}
			System.out.println();
			System.out.println(totalDistance);
			System.out.println(totalDuration);
			totalDistance = totalDistance / 1000;
			int hour = (int) (totalDuration / 3600);
			int min = (int) ((totalDuration % 3600) / 60);
			
			// ------------------- 경로 관련 모든 정보 보내주기 --------------------
			// 순서대로 저장된 관광지 리스트, 총 거리, 총 시간, 선택한 이동모드, 메세지
			optPath = new OptPath("비순환",pointsInOrder,totalDistance+"km", hour+"시간 "+min+"분",mode,msg,countryName);
//			System.out.println(optPath.toString());
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		long endTime = System.currentTimeMillis();
		System.out.println("걸린시간:"+(endTime-startTime));
		return optPath;
	}

	private OptPath configOptPathRound(String mode, String type, List<Point> input,String countryName) throws IOException {
		long startTime = System.currentTimeMillis();
		// ======================================= 테스트 후 주석처리해야 할 부분 시작 (오스트리아 빈의 관광지
		// 5곳의 위치 정보 임의로 넣음)

//		List<Point> input = new ArrayList<>();
//		
//		input.add(new Point("관광지1", 48.2038016, 16.3617874));
//		input.add(new Point("관광지2", 48.2033369, 16.3586166));
//		input.add(new Point("관광지3", 48.2404153, 16.3868931));
//		input.add(new Point("관광지4", 48.2167398, 16.3980327));
//		input.add(new Point("관광지5", 48.0927289, 15.9518237));

		// ======================================== 테스트 후 주석처리해야 할 부분 끝

		N = input.size();	
		visitedAllCity = (1 << N) - 1;
		W = new int[11][11];
		W2 = new int[11][11];
		D = new int[65537][11];
		P = new int[65537][11];
		noTransitPath = new boolean[11][11];
		OptPath = new ArrayList<>();
		
		List<Point> output = new ArrayList<>();
		map = new HashMap<>();

		for (int i = 0; i < input.size(); i++) {
			map.put(i, input.get(i));
		}

		// ================== 1. 모드(자동차,자전거,도보)에 따라 거리 또는 시간 값으로 인접행렬 구성 - google
		// distance api =============================

		String message = null;
		for (int i = 0; i < input.size(); i++) {
			for (int j = i+1; j < input.size(); j++) {

				if(i == j) continue;
				
				message = "";

				Point origin = input.get(i);
				Point dest = input.get(j);

				final String SERVICE_KEY = "AIzaSyCt4KmDVjM1tPcZlSZPu_QmY7GvFVP5vQI";
				String urlStr = "https://maps.googleapis.com/maps/api/distancematrix/json?mode=" + mode + "&origins="
						+ origin.getAddress().replace(" ","+") + "&destinations=" + dest.getAddress() .replace(" ","+")
						+ "&key=" + SERVICE_KEY;

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
					JSONArray positionArray = (JSONArray) jsonObj.get("rows");

					JSONObject posObject = ((JSONObject) positionArray.get(0));
					JSONArray posObject2 = (JSONArray) posObject.get("elements");
					JSONObject posObject3 = ((JSONObject) posObject2.get(0));

					if (posObject3.get("status").equals("ZERO_RESULTS")) {
//						System.out.println(mode + "모드에서 " + origin.getPointName() + "로부터 " + dest.getPointName()
//								+ "로의 경로가 존재하지 않습니다.");

//						noTransitPath[i][j] = true;
						message = "하나 이상의 구간에 대중교통정보가 존재하지 않아 자동차 모드가 적용된 경로입니다.";
						
						// 대중교통 경로 없으면 자동차 경로로 찾음
						urlStr = "https://maps.googleapis.com/maps/api/distancematrix/json?mode=driving" + "&origins="
								+ origin.getAddress().replace(" ","+") + "&destinations=" + dest.getAddress().replace(" ","+") + "&key=" + SERVICE_KEY;

						try {
							URL url_alt = new URL(urlStr);
							String line_alt = "";
							String response_alt = "";

							BufferedReader br_alt;
							br_alt = new BufferedReader(new InputStreamReader(url_alt.openStream()));
							while ((line_alt = br_alt.readLine()) != null) {
								response_alt = response_alt.concat(line_alt);
							}

							JSONParser jsonParse_alt = new JSONParser();
							JSONObject jsonObj_alt = (JSONObject) jsonParse_alt.parse(response_alt);
							JSONArray positionArray_alt = (JSONArray) jsonObj_alt.get("rows");

							JSONObject posObject_alt = ((JSONObject) positionArray_alt.get(0));
							JSONArray posObject2_alt = (JSONArray) posObject_alt.get("elements");
							posObject3 = ((JSONObject) posObject2_alt.get(0));

						} catch (ParseException e) {
							e.printStackTrace();
							continue;
						}
					}

					JSONObject posObject4;
					long res = 0, res2 = 0;

					if (type.equals("distance")) {
						posObject4 = (JSONObject) posObject3.get("distance");
						res = (long) posObject4.get("value");
						posObject4 = (JSONObject) posObject3.get("duration");
						res2 = (long) posObject4.get("value");

					} else {
						posObject4 = (JSONObject) posObject3.get("duration");
						res = (long) posObject4.get("value");
						posObject4 = (JSONObject) posObject3.get("distance");
						res2 = (long) posObject4.get("value");
					}

					W[i][j] = W[j][i] = (int) res; // 주 인접행렬 (외판원순회에 실제로 쓰임)
					W2[i][j] = W2[j][i] = (int) res2; // 부 인접행렬 (외판원순회에 쓰이지 않으나 최적경로 구성 후 계산에 사용됨)

//					if (type.equals("distance")) {
//						System.out.println("distance between " + origin.getPointName() + " and " + dest.getPointName()
//								+ " :" + res);
//						System.out.println("duration between " + origin.getPointName() + " and " + dest.getPointName()
//								+ " :" + res2);
//					} else {
//						System.out.println("duration between " + origin.getPointName() + " and " + dest.getPointName()
//								+ " :" + res);
//						System.out.println("distance between " + origin.getPointName() + " and " + dest.getPointName()
//								+ " :" + res2);
//					}

				} catch (ParseException e) {
					e.printStackTrace();
					continue;
				}
			}

		}

		// ========================== 2. 외판원순회 알고리즘 활용 ============================

//		        for(int i=0;i<65537;i++){
//		            Arrays.fill(P[i],-1);
//		        }

		// 총 비용(선택기준에 따라 달라짐 - 이동거리 또는 소요시간)
		int minCost = tsp(0, 0);
		System.out.println("min cost: " + minCost);

		// 최적경로 계산에 필요한 배열 출력(테스트용)
//		        for (int i = 0; i < (1 << N); i++) {
//		            for (int j = 0; j < N; j++) {
//		                System.out.print(P[i][j]==INF?"INF ":P[i][j]+" ");
//		            }
//		            System.out.println();
//		        }

		// 최적경로를 구성하는 관광지별 순서 추적 (P배열 이용) + 부가정보 총 비용 계산(거리 선택한 경우 시간, 시간 선택한 경우 거리)
		OptPath.add(0);
		OptPath.add(P[0][0]);
		minCost2 = W2[0][P[0][0]];

		tracePath(P[0][0], 1 << P[0][0]);
		System.out.println("min cost2: " + minCost2);

		for (int i = 0; i < OptPath.size(); i++) {
			output.add(map.get( OptPath.get(i)));
		}

		// .....3. 흔적

		// ============================ 4. 단위변환====================================
		OptPath optPath = null;

		if (type.equals("distance")) {
			double distance = minCost / 1000;
			int hour = minCost2 / 3600;
			int min = (minCost2 % 3600) / 60;
			
			if(hour==0)optPath = new OptPath("순환", type, output, distance + "km",  min + "분", mode, message,countryName);
			else optPath = new OptPath("순환", type, output, distance + "km", hour + "시간 " + min + "분", mode, message,countryName);

		} else {
			int hour = minCost / 3600;
			int min = (minCost % 3600) / 60;
			double distance = minCost2 / 1000;

			if(hour==0) optPath = new OptPath("순환", type, output, distance + "km",  min + "분", mode, message,countryName);
			optPath = new OptPath("순환", type, output,  distance + "km",hour + "시간 " + min + "분", mode, message,countryName);
		}
		long endTime = System.currentTimeMillis();
		System.out.println("걸린시간:"+(endTime-startTime));
		return optPath;
	}

	// 경로 추척하는 메서드
	private static void tracePath(int start, int visit) {

		if (start == 0)
			return;

		int dest = P[visit][start];
//        System.out.println("dest_new:" + dest_new);

		OptPath.add(dest);
		minCost2 += W2[start][dest];

		tracePath(dest, visit | (1 << dest));

	}

	// 외판원순회 기본 메서드
	// visited => 지금까지 방문한 도시
	// i => 시작 위치
	private static int tsp(int visited, int curr) {
//        System.out.println("curr: " + curr + ", visited:" + (visited));
		if (curr == 0 && visited == visitedAllCity) {
			return 0;
		}

		int result = 99999999;

		if (D[visited][curr] != 0)
			return D[visited][curr];

		int nextIndex = 0;
		for (int i = 0; i < N; i++) {
			int visitedNew = visited | (1 << i);

			if (W[curr][i] == 0 || (visited & (1 << i)) > 0)
				continue;

			int temp = tsp(visitedNew, i) + W[curr][i];

			if (result > temp) { // 최소 비용, 최소로 만드는 정점 인덱스 저장하는 코드
				result = temp;
				nextIndex = i;
			}

		}
		// visited에 들어 있는 도시를 방문했고,
		// curr를 시작으로 앞으로 방문할 도시의 가중치 최소 값
		D[visited][curr] = result;

		// 값이 갱신된다 => curr에서 다른 도시를 돌아 0번까지 간 경우이다
		// visited만큼 도시를 방문했고, curr에서 시작하는 경우 최소 가중치는 nextIndex로 가야 한다.
		P[visited][curr] = nextIndex;

		return result;
	}

	//상세정보 응답
	@PostMapping("/detail")
	public List<DetailDirection> path_detail(@RequestBody OptPath optpath) throws IOException {

			String mode = optpath.getTravelMode();
			String type = optpath.getPathCriteria();
			List<Point> path = optpath.getPointsInOrder();
			// ============================= 상세 경로 저장 =============================

			List<DetailDirection> detailDirectionList = new ArrayList<>();

			for (int i = 0; i < path.size() - 1; i++) {
				String message = null;
//				System.out.println(i + "번째:" +  i);
				Point curr =  path.get(i);
				Point next = null;

				if (i == path.size() - 1) next =  path.get(0);
				else next =  path.get(i+1);

				// google direction api 로 상세정보
				final String SERVICE_KEY = "AIzaSyCt4KmDVjM1tPcZlSZPu_QmY7GvFVP5vQI";
				String urlStr = "https://maps.googleapis.com/maps/api/directions/json?language=ko&mode=" + mode + "&origin="
							+ curr.getAddress().replace(" ","+") + "&destination=" + next.getAddress().replace(" ","+")
							+ "&key=" + SERVICE_KEY;

				try { // json 파싱
					URL url = new URL(urlStr);
					String line = "";
					String response = "";

					BufferedReader br;
					br = new BufferedReader(new InputStreamReader(url.openStream()));
					while ((line = br.readLine()) != null) response = response.concat(line);
					
					JSONParser jsonParse = new JSONParser();
					JSONObject jsonObj = (JSONObject) jsonParse.parse(response);
					String status = (String) jsonObj.get("status");
					
					if (!status.equals("OK")) { // 대중교통인 경우 길찾기 정보가 없을 수 있음 => 자동차 모드로 찾되, 이를 명시해야 함.
						System.out.println("교통 정보가 존재하지 않아 대체경로로 탐색함.");
						message = "교통정보가 존재하지 않아 대체경로가 적용된 구간입니다.";
						urlStr = "https://maps.googleapis.com/maps/api/directions/json?language=ko&origin="
								+ curr.getAddress().replace(" ","+") + "&destination=" + next.getAddress().replace(" ","+")
								+ "&key=" + SERVICE_KEY;
						System.out.println(urlStr);
						try { 
							url = new URL(urlStr);
							line = "";
							response = "";

							br = new BufferedReader(new InputStreamReader(url.openStream()));
							while ((line = br.readLine()) != null) {
								response = response.concat(line);
							}

							jsonParse = new JSONParser();
							jsonObj = (JSONObject) jsonParse.parse(response);
						    status = (String) jsonObj.get("status");
						    
						    if (!status.equals("OK")) {
						    	System.out.println("대체경로도 존재하지 않음.");
//						    	throw new Exception("경로존재하지않음예외발생.");
						    }
						    
						} catch (ParseException e) {
							e.printStackTrace();
							continue;
						}
					}
					
					JSONArray routes = (JSONArray) jsonObj.get("routes");
					JSONObject route = ((JSONObject) routes.get(0));
					String polyline  = (String) ((JSONObject) route.get("overview_polyline")).get("points");
					JSONArray legs = (JSONArray) route.get("legs");
					JSONObject leg = ((JSONObject) legs.get(0)); //경유지 없으므로 첫 번째 요소만 이용함.

					String totalDistanceStr =  (String) ((JSONObject) leg.get("distance")).get("text");
					String totalDurationStr = (String) ((JSONObject) leg.get("duration")).get("text");

					// == DRIVING. WALKING 인 경우는 구간별 거리.시간만 얻음
					
					// == TRANSIT은 step 별 html_instructions 얻음
					List<Step> instructionList = new ArrayList<>();
					
					if (mode.equals("transit")) {		
						JSONArray steps = (JSONArray) leg.get("steps");
						int size = steps.size();
						for (int j = 0; j < size; j++) {
							JSONObject step = (JSONObject) steps.get(j);					
							String step_mode = (String) step.get("travel_mode");
							
							 if(step_mode.equals("TRANSIT")) {
								 
								String step_distance = (String) ((JSONObject) step.get("distance")).get("text");
								String step_durtaion = (String) ((JSONObject) step.get("duration")).get("text");
								String step_instruction = (String) step.get("html_instructions");

								List<Step> detailStepList = null;
								JSONArray steps_detail = (JSONArray) step.get("steps");
								
								if(steps_detail != null) {
									detailStepList = new ArrayList<>();
									int size_detail = steps_detail.size();
									for (int k = 0; k < size_detail; k++) {
										
										JSONObject step_detail = (JSONObject) steps_detail.get(k);
										
										String step_distance_detail = (String) ((JSONObject) step_detail.get("distance")).get("text");
										String step_durtaion_detail = (String) ((JSONObject) step_detail.get("duration")).get("text");
										String step_mode_detail = (String) step_detail.get("travel_mode");
										String step_mode_instruction = (String) step_detail.get("html_instructions");
										
										Step sd = new Step(step_distance_detail,step_durtaion_detail,step_mode_detail,step_mode_instruction);
										
										detailStepList.add(sd);
									}	
								}
								
								Step s = new Step(step_distance,step_durtaion,step_mode,step_instruction);
								s.setDetailStepList(detailStepList);
								instructionList.add(s);
							}		
						}
					}
					
					// 한 개의 구간에 대한 상세정보 입력
					DetailDirection detailDirection = new DetailDirection(curr, next, totalDistanceStr, totalDurationStr,
							instructionList, message, polyline);
					detailDirectionList.add(detailDirection);

				} catch (ParseException e) {
					e.printStackTrace();
					continue;
				}
			} //for문 종료

			// 테스트 출력
//			System.out.println("type: " + type);
//			for (int j = 0; j < detailDirectionList.size(); j++) {
//				System.out.println("구간 [" + (j + 1) + "] 이동거리: " + detailDirectionList.get(j).getTotalDistance()
//						+ ", 소요시간: " + detailDirectionList.get(j).getTotalDuration());
//			}
			
			return detailDirectionList;
		}

		
}
