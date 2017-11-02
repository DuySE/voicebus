
<?php 

	ini_set('max_execution_time', 0);
    include("../dbcon.php");

    class Point {
		public $lat;
		public $lang;
		
		function __construct($lat, $lang) {
			$this->lat = $lat;
			$this->lang = $lang;
		}

		function getCoor() {
			return "".($this->lat).",".($this->lang);
		}
	}
    // direct distance on spheere
	function distance(Point $st, Point $fn) {
		$_radius = 6371000;
		$_dLon = deg2rad($fn->lang - $st->lang);
		$_lat1 = deg2rad($st->lat);
		$_lat2 = deg2rad($fn->lat);
		$_dist = acos(sin($_lat1) * sin($_lat2) + cos($_lat1) * cos($_lat2) * cos($_dLon)) * $_radius;
		return $_dist;
	}

    $_testingList = getAllTmpStation();

    /*foreach ($_testingList as $_comp) {

    	echo "at station ID = ".$_comp['stationID']."<br>";

    	$_listSame = getTmpStation($_comp['stationID']);
    	
    	foreach ($_listSame as $_same) {
    		echo $_same['bus']."<br>";
    	}
    } */

    $_sz = count($_testingList);

    for ($_i = 0; $_i < $_sz; $_i ++)
    	for ($_j = $_i + 1; $_j < $_sz; $_j++) {

    		$_p1 = new Point($_testingList[$_i]['Lat'], $_testingList[$_i]['Lng']);
    		$_p2 = new Point($_testingList[$_j]['Lat'], $_testingList[$_j]['Lng']);

    		$_myDist = distance($_p1, $_p2);
    		if ($_myDist <= 10) {
    			echo $_testingList[$_i]['stationID']." ".$_testingList[$_j]['stationID']."<br>";
    			echo "my bus => i => ".$_testingList[$_i]['bus']." j => ".$_testingList[$_j]['bus']."<br>";
    		}
    	}


/*	foreach($_testingList as $_test) {
		echo $_test['Address']."<br>";
	} */


	//echo json_encode($_testingList, JSON_UNESCAPED_UNICODE);
?>



