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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
<button class="sendBtn">SEND</button>
<a href="/yeokku/optpath_oneway/driving">oneway</a>
<div id="map"></div>
	
	<script async
	  src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDyU_VZ_a8MeeSamxk8c0IgaUaZXaTUPnk&callback=initMap">
	</script>
	
	<script type="text/javascript">
	let map;
 	function initMap() {
		map = new google.maps.Map(document.getElementById('map'), {
			  center: {lat : 48.2038016, lng : 16.3617874}, 
			  zoom: 15
			});
		 new google.maps.Marker(
					{
						position: {lat : 48.2038016, lng : 16.3617874},
						map: map
					});
		new google.maps.Marker(
					{
						position: {lat : 48.2033369, lng : 16.3586166},
						map: map
					}); 
		new google.maps.Marker(
				{
					position: {lat : 48.2404153, lng : 16.3868931},
					map: map
				}); 
		new google.maps.Marker(
				{
					position: {lat : 48.2167398, lng : 16.3980327},
					map: map
				}); 
					
			 map.addListener('click', function(event) {  
				    addMarker(event.latLng);  
				  }); 
					
		 var marker = new google.maps.Marker({
		        position: location, 
		        map: map
		    });
		    
		    console.log(marker.position);
		    map.addListener('click', function(event) {  
			     addMarker(event.latLng);  
			   });  
			   
			  
		    
	}
	 
	 
	 var markers = [];
	 var locations = [];
	  
/* 	 function initMap() {  
	   
		 var lat_lng = {lat: 37.501783, lng: 127.039660};  
		 
	   map = new google.maps.Map(document.getElementById('map'), {  
		   center: lat_lng,  
			  zoom: 15
	   });   
	   
	   // This event listener will call addMarker() when the map is clicked.  
	   map.addListener('click', function(event) {  
	     addMarker(event.latLng);  
	   });  
	   
	   // Adds a marker at the center of the map.  
	   addMarker(lat_lng); 
	   
	 }  */
	   
	 // Adds a marker to the map and push to the array.  
	 function addMarker(location) {  
	    var marker = new google.maps.Marker({  
	    	position : location,
	     	map: map  
	   });
	    
	   var mPosition = {
			   lat : marker.getPosition().lat(),
			   lng : marker.getPosition().lng()
	   }
	   markers.push(mPosition);  
	   
	  const infowindow = new google.maps.InfoWindow({
		    content: "<p>Marker Location:" + marker.getPosition()+ "</p>",
		  });
	   
		  google.maps.event.addListener(marker, "click", () => {
		    infowindow.open(map, marker);
		  });
		  
	 }  

	 $('.sendBtn').click(function(){
		getLocation();
	 });
	 
	function getLocation(){
		console.log(markers);

		$.ajax({
			url:"/yeokku/map/getLocation",
			dataType:"json",
			type:"POST",
			traditional: true,
			data : {
				data : JSON.stringify(markers)
			},
			success : function(data){
			console.log('성공');
			},
			error : function(data){
				console.log('오류');
			}
		});
	}
	
	</script>  	

</body>
</html>