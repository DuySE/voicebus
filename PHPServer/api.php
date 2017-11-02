
<?php 
	// controller 
	include("dbcon.php");
	include('googleApi.php');
	include('apiImp.php');
	include('text_analysis.php');

	//print_r(getStationByCode('Q12 018'));

	//print_r(getStationByID('2057'));

	// request api
	if ($_SERVER['REQUEST_METHOD'] == 'GET') {
		
		//print_r( addressToLocation("chợ bến thành") );

		$_type = $_GET['type'];

		if ($_type <= 4) {
			$_long = $_GET['long'];
			$_lat = $_GET['lat'];
			$_dist = (isset($_GET['dist']) ? $_GET['dist'] : 2000); 
			$_myPoint = new Point($_lat, $_long);
		}

		if ($_type == 1) {
			$_stationInfo = findNearestStations($_myPoint, $_dist, "0");

			if ($_stationInfo['Lat'] == -1) {
				// no bus in area
				$_response = array();
				
				//$_response['mess'] = "No stop in here!";
				//$_response['status'] = "1";

				echo json_encode($_response, JSON_UNESCAPED_UNICODE);
   				exit(0);
			}
			else {
				// nearest bus information
				$_json = array();
				$_stationInfo['mess'] = "OK";
				$_stationInfo['status'] = "0";
				//$_stationInfo['duration'] = getDistance($_myPoint, new Point($_stationInfo['Lat'], $_stationInfo['Lng']));
				//print_r($_stationInfo);
				$_json[] = $_stationInfo;

				echo json_encode($_json, JSON_UNESCAPED_UNICODE);
   				exit(0);
			}
		}
		else
		if ($_type == 2) {
			$_stations['mess'] = "OK";
			$_stations['status'] = "0";
			$_stations = findNearStations($_myPoint, $_dist, "0");
			echo json_encode($_stations, JSON_UNESCAPED_UNICODE);
			exit(0);
		}
		else
		if ($_type == 3) {
			$_busNum = intval($_GET['busnum']);
			$_stationInfo = findNearestStations($_myPoint, $_dist, $_busNum);

			if ($_stationInfo['Lat'] == -1) {
				$_response = array();
				echo json_encode($_response, JSON_UNESCAPED_UNICODE);
   				exit(0);
			}
			else {
				$_json = array();
				
				$_json[] = $_stationInfo;
				echo json_encode($_json, JSON_UNESCAPED_UNICODE);
				exit(0);
			}
		}
		else
		if ($_type == 4) {
			$_busNum = intval($_GET['busnum']);
			$_stationInfo = findNearStations($_myPoint, $_dist, $_busNum);
			echo json_encode($_stationInfo, JSON_UNESCAPED_UNICODE);
			exit(0);
		}
		else
		if ($_type == 6) {
			// from current location
			$_long = $_GET['long'];
			$_lat = $_GET['lat'];
			// begin point
			$_myPoint = new Point($_lat, $_long);
			// end point
			$_desPoint = addressToLocation($_GET['end']);
			// get result
			$_result = findAllPath($_myPoint, $_desPoint, "Vị trí hiện tại", $_GET['end']);
			// output
			echo json_encode($_result, JSON_UNESCAPED_UNICODE);
			exit(0);
		}
		else
		if ($_type == 5) {
			// from any location
			// begin point
			$_beginPoint = addressToLocation($_GET['begin']);
			// end point
			$_endPoint = addressToLocation($_GET['end']);
			// get result
			$_result = findAllPath($_beginPoint, $_endPoint, $_GET['begin'], $_GET['end']);
			// output
			echo json_encode($_result, JSON_UNESCAPED_UNICODE);
			exit(0);
		}
		else
		if ($_type == 8) {	
			// text - analysis - data
			$_msg = $_GET['msg'];
			echo json_encode(textAnalysis($_msg), JSON_UNESCAPED_UNICODE);
			exit(0);
		}
	}

?>
