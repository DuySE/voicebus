<?php 

	function makeJsonPathOneBus($_routeID, $_bgOrder, $_enOrder) {
		
		//echo "route => ".$_routeID." begin = ".$_bgOrder." end = ".$_enOrder."<br>";

		$_routes = getRoutesById($_routeID);

		$_InRoutes = array();
		$_stations = array();

		$_result = array();

		foreach ($_routes as $_route) {
			if ($_route['routeOrder'] >= $_bgOrder && $_route['routeOrder'] <= $_enOrder) {
				
				$_InRoutes[] = $_route;
				
				$_station = array();
				$_station['Lat'] = $_route['Lat'];
				$_station['Lng'] = $_route['Lng'];
				$_station['name'] = getStationNameByID($_route['stationID']);

				$_stations[] = $_station;
			}
		}

		$_result['stations'] = $_stations;

		$_result['path'] = array();

		$_result['path'][] = array("Lat" => $_InRoutes[0]['Lat'], "Lng" => $_InRoutes[0]['Lng']);

		for($_i = 1; $_i < count($_InRoutes); $_i++) {

			$_insidePath = array_map('trim', explode(' ', trim($_InRoutes[$_i]['pathPoints'])));	
	
			foreach ($_insidePath as $_path) {
				if (strlen($_path) == 0) continue;
  			 	$_latAndLng = explode(',', $_path);
  			 	$_result['path'][] = array("Lat" => $_latAndLng[1], "Lng" => $_latAndLng[0]);
			}
		}

		//$_sz = count($_InRoutes) - 1;
		//$_result['path'][] = array("Lat" => $_InRoutes[$_sz]['Lat'], "Lng" => $_InRoutes[$_sz]['Lng']);
		
		return $_result;
	} 

?>
