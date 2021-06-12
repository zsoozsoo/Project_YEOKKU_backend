package com.yeokku.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yeokku.model.dto.ConfigPathInput;
import com.yeokku.model.dto.DetailDirection;
import com.yeokku.model.dto.OptPath;
import com.yeokku.model.dto.Point;

@CrossOrigin
@RestController
public class OptimalPathController {

	static int N, minCost2;
	static int[][] W, W2, D, P;
	static boolean[][] noTransitPath;
	static boolean noOriginTransit;
	static boolean noDestTransit;
	static List<Integer> OptPath;
	static int visitedAllCity;
	static final int INF = Integer.MAX_VALUE;
	static int maxNum = 0;

	// 관광지 배열(시작점) 받아서 최적경로(순환)를 구성하는 순서대로 배열 반환
	@GetMapping("/optpath_round/{mode}")
	private List<OptPath> optpath_round(@PathVariable String mode, @RequestBody ConfigPathInput cpinput)
			throws IOException {
		
		List<OptPath> optPathList = new ArrayList<>();

		optPathList.add(configOptPathRound(mode, "distance", cpinput));
		optPathList.add(configOptPathRound(mode, "duration", cpinput));

		return optPathList;
	}

	private OptPath configOptPathRound(String mode, String type, ConfigPathInput cpinput) throws IOException {

		
		// ======================================= 테스트 후 주석처리해야 할 부분 시작 (오스트리아 빈의 관광지 5곳의 위치 정보 임의로 넣음)

//		List<Point> input = new ArrayList<>();
//		
//		input.add(new Point("관광지1", 48.2038016, 16.3617874));
//		input.add(new Point("관광지2", 48.2033369, 16.3586166));
//		input.add(new Point("관광지3", 48.2404153, 16.3868931));
//		input.add(new Point("관광지4", 48.2167398, 16.3980327));
//		input.add(new Point("관광지5", 48.0927289, 15.9518237));

		// ======================================== 테스트 후 주석처리해야 할 부분 끝

		Point start = cpinput.getStart();
		List<Point> input = cpinput.getPointList();
		input.add(0, start);
		
		N = input.size();
		visitedAllCity = (1 << N) - 1;
		W = new int[11][11];
		W2 = new int[11][11];
		D = new int[65537][11];
		P = new int[65537][11];
		noTransitPath = new boolean[11][11];
		OptPath = new ArrayList<>();
		
		List<Point> output = new ArrayList<>();
		Map<Integer, Point> map = new HashMap<>();

		for (int i = 0; i < input.size(); i++) {
			map.put(i, input.get(i));
		}

		// ================== 1. 모드(자동차,자전거,도보)에 따라 거리 또는 시간 값으로 인접행렬 구성 - google distance api =============================

		for (int i = 0; i < input.size(); i++) {
			for (int j = i + 1; j < input.size(); j++) {

				noOriginTransit = false;
				noDestTransit = false;
				
				Point origin = input.get(i);
				Point dest = input.get(j);

				final String SERVICE_KEY = "AIzaSyC6HRafHB4tQDc-GSCPbwTnybYJLNybxDw";
				String urlStr = "https://maps.googleapis.com/maps/api/distancematrix/json?mode=" + mode
						+ "&origins=" + origin.getLat() + "," + origin.getLng() + "&destinations=" + dest.getLat() + ","
						+ dest.getLng() + "&key=" + SERVICE_KEY;

//					final String urlStr = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=Washington,DC&destinations=New+York+City,NY&key="+SERVICE_KEY;

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
						System.out.println(mode + "모드에서 "+ origin.getPointName()+"로부터 "+ dest.getPointName() + "로의 경로가 존재하지 않습니다.");
						
						noTransitPath[i][j] = noTransitPath[j][i] = true;
						
						// 대중교통 경로 없으면 자동차 경로로 찾음
						urlStr = "https://maps.googleapis.com/maps/api/distancematrix/json?mode=driving"
								+ "&origins=" + origin.getLat() + "," + origin.getLng() + "&destinations=" + dest.getLat() + ","
								+ dest.getLng() + "&key=" + SERVICE_KEY;
						
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
			
		
		//  ============================= 3. 상세 경로 저장 =============================
		
		List<DetailDirection> detailDirectionList = new ArrayList<>();
		
		for (int i = 0; i < OptPath.size()-1; i++) {
			System.out.println(i + "번째:" + OptPath.get(i));
			
			int currIndex =  OptPath.get(i);
			Point curr = map.get(currIndex);
			int nextIndex = -1;
			Point next = null;
			
			if(i == OptPath.size()-1) {
				nextIndex = OptPath.get(0);
				next = map.get(nextIndex);
			}
			else {
				nextIndex = OptPath.get(i+1);
				next = map.get(nextIndex);
			}
			
			output.add(curr);
			
			// google direction api 로 상세정보
			final String SERVICE_KEY = "AIzaSyC6HRafHB4tQDc-GSCPbwTnybYJLNybxDw";
			String urlStr = null;
			
			if(noTransitPath[currIndex][nextIndex]) { // 이 구간에 대중교통 경로가 없어 자동차 경로로 대체한 경우
				System.out.println(currIndex+" 에서 "+nextIndex+" 대체 경로");
				urlStr = "https://maps.googleapis.com/maps/api/directions/json?language=ko&mode=driving"
						+ "&origin=" + curr.getLat() + "," + curr.getLng() + "&destination=" + next.getLat() + ","
						+ next.getLng() + "&key=" + SERVICE_KEY;
				
			} else { //사용자의 선호 이동방법을 그대로 적용한 경우
				System.out.println(currIndex+" 에서 "+nextIndex+" 정상 경로");
				urlStr = "https://maps.googleapis.com/maps/api/directions/json?language=ko&mode=" + mode
						+ "&origin=" + curr.getLat() + "," + curr.getLng() + "&destination=" + next.getLat() + ","
						+ next.getLng() + "&key=" + SERVICE_KEY;
			}

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
				String status = (String) jsonObj.get("status");
				if(!status.equals("OK")) {
					System.out.println("경로가 존재하지 않습니다.");
					continue;
				}
				JSONArray routes = (JSONArray) jsonObj.get("routes");
				JSONObject route = ((JSONObject) routes.get(0));
				JSONArray legs = (JSONArray) route.get("legs");
				JSONObject leg = ((JSONObject) legs.get(0));

//				System.out.println(leg);
				
				JSONObject totalDistance = (JSONObject) leg.get("distance");
				String totalDistanceStr = (long) totalDistance.get("value") + "";
				JSONObject totalDuration = (JSONObject) leg.get("duration");
				String totalDurationStr = (long) totalDuration.get("value") + "";
				
				String instruction = null;
				if(mode.equals("transit")) { // 대중교통일 경우만 instruction 추가
					JSONArray steps = (JSONArray) leg.get("steps");
					int size = steps.size();
					for (int j = 0; j < size; j++) {
						
						JSONObject step = (JSONObject) steps.get(j);
						String instruction_temp = (String) step.get("html_instructions");
						System.out.println("instruction: "+instruction_temp);
						if(instruction_temp != null && (instruction_temp.contains("버스") || instruction_temp.contains("지하철") || instruction_temp.contains("기차")|| instruction_temp.contains("트램")|| instruction_temp.contains("열차"))) {
							instruction = instruction_temp;
							break;
						}
						
					}
				}
				if(instruction == null) instruction = "대중교통 경로가 없습니다.";
				DetailDirection detailDirection = new DetailDirection(curr, next, totalDistanceStr, totalDurationStr, instruction);	
				detailDirectionList.add(detailDirection);

			} catch (ParseException e) {
				e.printStackTrace();
				continue;
			}
		}
		
		// 테스트 출력
		System.out.println("type: "+type);
		for (int j = 0; j < detailDirectionList.size(); j++) {
			System.out.println("구간 ["+(j+1) +"] 이동거리: "+ detailDirectionList.get(j).getTotalDistance() +", 소요시간: " + detailDirectionList.get(j).getTotalDuration()+", 대중교통 정보: "+ detailDirectionList.get(j).getDescription());
		}
		
		// ============================ 4. 단위변환====================================
		OptPath optPath = null;
		
		if (type.equals("distance")) {
			double distance = minCost / 1000;
			int hour = minCost2 / 3600;
			int min = (minCost2 % 3600) / 60;

			optPath = new OptPath(type, output, distance + "km", hour + "h " + min + "min", mode, detailDirectionList);

		} else {
			int hour = minCost / 3600;
			int min = (minCost % 3600) / 60;
			double distance = minCost2 / 1000;

			optPath = new OptPath(type, output, hour + "h " + min + "min", distance + "km", mode, detailDirectionList);
		}

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

}
