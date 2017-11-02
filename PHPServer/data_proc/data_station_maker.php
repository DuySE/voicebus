<?php 
	// set delay to infinitive

	ini_set('max_execution_time', 0);
	include("../dbcon.php");
	
	$time_start = microtime(true);
	///////////////////////////////////
	$_allStaion = getAllRoutesComp();

	foreach ($_allStaion as $_station) {
		echo $_station['stationID']."<br>";
		$_listStation = getStationByID($_station['stationID']);
		
		$_myBus = "".$_listStation[0]["bus"];

		// hashmap
		$_myHashMap[$_myBus] = 1;

		$_ID = $_station['stationID'];

		$_Code = $_listStation[0]['Code'];

		$_Lat = $_listStation[0]['Lat'];

		$_Lng = $_listStation[0]['Lng'];

		$_Address = $_listStation[0]['Address'];

		$_Name = $_listStation[0]['Name'];

		for ($_i = 1; $_i < count($_listStation); $_i++) {
			
			$_curBus = ''.$_listStation[$_i]['bus'];

			if (!isset($_myHashMap[$_curBus])) {
				$_myBus = $_myBus.",".$_listStation[$_i]['bus'];
				$_myHashMap[$_curBus] = 1;
			}
		}
		$_myHashMap = null;
		echo "that bus = ".$_myBus."<br>";
		//addStation($_ID, $_Code, $_Name, $_Lat, $_Lng, $_Address, $_myBus);
	}
	///////////////////////////////////
	//echo $data;
    $time_end = microtime(true);
    $time = $time_end - $time_start;
    echo "Process Time: {$time}"; /**/
?>