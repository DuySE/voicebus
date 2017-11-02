<?php 
	// priority: total time (walking + on_bus)
	// less time for walking (+15p)
	// less changing (at most 3)
	// distance for walking <= 2km
	include("../dbcon.php");
  	$_cnt = 0;
  	// 55 => 104
  	// 18 => 35
  	$stops = getRoutesById(104);

?>

<!DOCTYPE html>
<html>

<body>

<div id="map" style="width:100%;height:500px"></div>

<script>
function myMap() {
  var myCenter = new google.maps.LatLng(10.855122, 106.623866);
  
  var mapCanvas = document.getElementById("map");
  var mapOptions = {center: myCenter, zoom: 14};
  var map = new google.maps.Map(mapCanvas, mapOptions);

  var flightPlanCoordinates = [      
  <?php 
  		$_dem = 0;

  		foreach ($stops as $_stop) {
  			
  			$_insidePath = array_map('trim', explode(' ', trim($_stop['pathPoints'])));



  			if ($_dem == 1)
  			 foreach ($_insidePath as $_onePoint) {
  			 	if (strlen($_onePoint) == 0) continue;
  			 	
  			
  			 	$_latAndLng = explode(',', $_onePoint);
  			 	
  			// 	if ($_dem < count($_insidePath) - 1)
  			 		echo "{lat: ".$_latAndLng[1].",  lng: ".$_latAndLng[0]."},";	
  			 }
  			//	echo "this point = {lat: ".$_stop['Lat'].",  lng: ".$_stop['Lng']."},";  			 
  			
  			         $_dem++;
	
  		}
	?>
  		];

        var flightPath = new google.maps.Polyline({
          path: flightPlanCoordinates,
          geodesic: true,
          strokeColor: '#FF0000',
          strokeOpacity: 1.0,
          strokeWeight: 5
        });

  		

  		// set path to map
		flightPath.setMap(map);

  		<?php
		// find nearest station
		foreach($stops as $stop) {
				//if (!in_array($stop['ID'], array_map('trim', explode(',', $_otherRoutes)))) {
			//	print_r( array_map('trim', explode(' ', trim($stop['pathPoints'])) ) );

				?>
					var myMaker_<?php echo $_cnt?> = new google.maps.LatLng(<?php echo $stop['Lat']?>, <?php echo $stop['Lng']?>);

					var marker_<?php echo $_cnt?> = new google.maps.Marker({position: myMaker_<?php echo $_cnt?>, icon: 'https://www.imageupload.co.uk/images/2017/09/30/WX_circle_white.png', label: "<?php echo $stop['routeOrder'] ?>"});
					marker_<?php echo $_cnt?>.setMap(map);
				
				<?php
				$_cnt++;
			
		}
  ?>
		}

</script>

<script 
src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBky56RLRJAsNhmzoLNtWUnwEwjs7E_BcU&callback=myMap"></script>

</body>
</html>