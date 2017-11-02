<?php 
	// priority: total time (walking + on_bus)
	// less time for walking (+15p)
	// less changing (at most 3)
	// distance for walking <= 2km
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

  <?php 
    include("../dbcon.php");

  	$_cnt = 0;
  	$stops = getRoutesById(104);

		// find nearest station
		foreach($stops as $stop) {
				//if (!in_array($stop['ID'], array_map('trim', explode(',', $_otherRoutes)))) {
				?>
					var myMaker_<?php echo $_cnt?> = new google.maps.LatLng(<?php echo $stop['Lat']?>, <?php echo $stop['Lng']?>);

					var marker_<?php echo $_cnt?> = new google.maps.Marker({position: myMaker_<?php echo $_cnt?>, icon: 'https://www.imageupload.co.uk/images/2017/09/30/WX_circle_white.png', label: "<?php echo $stop['routeOrder'] ?>"});
					marker_<?php echo $_cnt?>.setMap(map);
				
				<?php
			//	echo '"Lat":'.$stop['Lat'].', "Lng": '.$stop['Lng']."<br>";
			//	echo "path ==>>> ".$stop['pathPoints']."<br>";
				//}
				$_cnt++;
			
		}
  ?>
}
</script>

<script 
src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBky56RLRJAsNhmzoLNtWUnwEwjs7E_BcU&callback=myMap"></script>

</body>
</html>