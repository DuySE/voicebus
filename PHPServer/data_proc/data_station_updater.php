<?php 
	// set delay to infinitive

	ini_set('max_execution_time', 0);
	include("../dbcon.php");
	
	$time_start = microtime(true);
	///////////////////////////////////
	$_allBus = getAllBusNum();


	foreach ($_allBus as $_bus) {
		
		$_listRoute = getRoutesByBusnum($_bus['busnum']);	

		print_r($_listRoute);

		echo "<br>";

		$_oneroute = getRoutesById($_listRoute[0]['routeID']);

		$_begin = $_oneroute[0]['stationID'];
		$_end = $_oneroute[count($_oneroute) - 1]['stationID'];

		echo "begin/end = ".$_begin." ".$_end."<br>";
		echo "bus = ".$_bus['busnum']."<br>";

		$_name = getStationNameByID($_begin)." - ".getStationNameByID($_end);
		
		//addBusInfo($_bus['busnum'], $_name);
	}

	///////////////////////////////////
	//echo $data;
    $time_end = microtime(true);
    $time = $time_end - $time_start;
    echo "Process Time: {$time}"; /**/
?>