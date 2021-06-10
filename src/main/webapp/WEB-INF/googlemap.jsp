<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>google map</title>
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
<div id="map"></div>
	
	<script async
	  src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDyU_VZ_a8MeeSamxk8c0IgaUaZXaTUPnk&callback=initMap">
	</script>
	    
	<script type="text/javascript">
	let map;
	function initMap() {
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