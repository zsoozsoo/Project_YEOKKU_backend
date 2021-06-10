<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>google map</title>
<!-- Bootstrap core JS-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"></script>
<style>
  #map {
    height: 100%;
  }
  html, body {
    height: 100%;
    margin: 0;
    padding: 0;
  }
</style>

</head>
<body>


	<div class="">
		<select>
			<option>대륙</option>
		</select>
		<select>
			<option>국가</option>
		</select>
		<select>
			<option>도시</option>
		</select>
	
	</div>
	

	<div id="map"></div> 
	

	
	<div  class="">
		<div>		
			<h3>선택한 관광지</h3>
			
		</div>
	
		<div>
			 <input type="radio" id="round" value="round" name="roundoroneway"/>순환
			 <input type="radio" id="oneway" value="oneway" name="roundoroneway"/>비순환
			 
			 <button>경로찾기</button>
		</div>
	
	</div>


	
<script async
  src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDyU_VZ_a8MeeSamxk8c0IgaUaZXaTUPnk&callback=initMap">
</script>
	<script>
	//서버로 도시 보냄
 		$.ajax({
		    url:'/places/request_places', 
		    type:'post',
		    data:{'cityName':'아테네'}, 
		    success: function(result) {
		    	
		    	var positions = new Array();
				$.each(result, function(index, value){
					var data = new Object();
					
					data.title = value.title;
					data.desc = value.desc;
					data.lat = value.lat;
					data.lng = value.lng;

					positions.push(data);
					
				});
				
				console.log(positions);
		    },
		    error: function(err) {
		       console.log(err);
		    } 
		});

	</script>
	<script type="text/javascript">
	let map;
	function initMap() {
		
		//도시 선택하면 ajax로 중심 위도, 경도랑 관광지 정보 받아서 뿌림 
 		map = new google.maps.Map(document.getElementById('map'), {
			  center: {lat: 37.501783, lng: 127.039660}, 
			  zoom: 15
			});
		
		
		new google.maps.Marker(
					{
						position: {lat : 37.5012743, lng : 127.039585},
						map: map
					});
		new google.maps.Marker(
					{
						position: {lat : 37.5012743, lng : 127.049585},
						map: map
					});
	} 
	</script>  	

</body>
</html>