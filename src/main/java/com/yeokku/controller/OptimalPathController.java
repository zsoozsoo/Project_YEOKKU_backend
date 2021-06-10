package com.yeokku.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.RestController;

import com.yeokku.model.dto.Point;

@CrossOrigin
@RestController
public class OptimalPathController {

    static int N;
    static int W[][] = new int[10][10];
    static int D[][] = new int[65537][10];
    static int P[][] = new int[65537][10];
    static List<Integer> OptPath = new ArrayList<>();
    static int visitedAllCity;
    static final int INF = Integer.MAX_VALUE;
    static int maxNum=0;
    
    
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
		Map<Integer,Point> map = new HashMap<>();
		
		for(int i = 0; i < input.size(); i++) {
			
			map.put(i,input.get(i));
		}
		
		// ================== 1. 거리 또는 시간 값으로 인접행렬 구성 - google distance api =============================	
		
		for(int i = 0; i < input.size(); i++) {
			for (int j = i + 1; j < input.size(); j++) {	

				Point origin = input.get(i);
				Point dest = input.get(j);
				
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
					
					W[i][j] = W[j][i] = (int) res;
					
					if(type.equals("distance")) System.out.println("distance between " + origin.getPointName()+" and " + dest.getPointName()+ " :" + res);
					else System.out.println("duration between " + origin.getPointName()+" and " + dest.getPointName()+ " :" + res);
					
				} catch (ParseException e) {
					e.printStackTrace();
					continue;
				}
			}
				
		}
		
		// ========================== 2. 외판원순회 알고리즘 활용 ============================
		
//        for(int i=0;i<65537;i++){
//            Arrays.fill(P[i],-1);
//        }
        
        N = input.size();
		visitedAllCity = (1<<N) -1;
        //최소비용
        System.out.println("min cost: " + tsp(0, 0));

        //최적경로
        for (int i = 0; i < (1 << N); i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(P[i][j]==INF?"INF ":P[i][j]+" ");
            }
            System.out.println();
        }
        OptPath.add(0);
        OptPath.add(P[0][0]);
        tracePath(P[0][0],1<<P[0][0]);

		for (int i = 0; i < OptPath.size(); i++) {	
			System.out.println(i+"번째:"+OptPath.get(i));
			output.add(map.get(OptPath.get(i)));
		}
        
		return output;
	}
	
	private static void tracePath(int start,int visit) {

        if(start == 0) return;

        int dest_new = P[visit][start];
        System.out.println("dest_new:"+dest_new);
        OptPath.add(dest_new);
        int visit_new = visit | (1<<dest_new);

        tracePath(dest_new,visit_new);

    }

    // visited => 지금까지 방문한 도시
    // i=> 시작 위치
    private static int tsp(int visited, int curr) {
        System.out.println("curr: " + curr + ", visited:" + (visited));
        if (curr==0 && visited == visitedAllCity) {
            return 0;
        }

        int result = 99999999;

        if (D[visited][curr] != 0) {
            return D[visited][curr];
        }
        int nextIndex=0;
        for (int i = 0; i < N; i++) {
            int visitedNew=visited | (1 << i);
            if (W[curr][i] == 0 || (visited & (1 << i)) >0) continue;
            int plus=tsp(visitedNew, i)+ W[curr][i];
            if(result > plus) {
                result = plus;
                nextIndex=i;

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
	
	
	// 관광지 배열(시작점,끝점) 받아서 최적경로(비순환)를 구성하는 순서대로 배열 반환
//	@GetMapping("/optpath_oneway/{type}")
//	private List<Point> optpath_oneway(@PathVariable String type, @RequestBody List<Point> input) {
//		
//		
//	}
}
