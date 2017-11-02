<?php
	include("json_maker.php");
	// maximum result 
	function getBusNames($_listBus) {

		$_listBusInStation = explode(",", $_listBus);
		$_stationInfo = array();

		foreach ($_listBusInStation as $_bus) {
					$_busInfo = array();
					$_busInfo['busnum'] = $_bus;
					$_busInfo['routeName'] = getBusName($_bus);
					$_stationInfo[] = $_busInfo;			
		}
		return $_stationInfo;
	}

	// find nearest bus station
	function findNearestStations($_myPoint, $_limit, $_busNum) {
		$_listStations = array();
        // station info
		$_stationInfo = array();
		// init param
		$_minDist = $_limit + 1;
		$_choiceLong = -1;
		$_choiceLat = -1;
		// init
		$_stationInfo['Lat'] = -1;
		$_stationInfo['Lng'] = -1;
		// get all stops
		$stops = getAllStation();
		// find nearest station
		foreach($stops as $stop) {
			// check if contains bus
			if ($_busNum != "0") {
					if (!isHaveBus($_busNum, $stop['bus'])) continue;
			}
			// distance
			$_curDist = distance($_myPoint, new Point($stop['Lat'], $stop['Lng']));
			// qualified
			if ($_curDist < $_minDist) {
				$_minDist = $_curDist;
				// update choice
				$_choiceLat = $stop['Lat'];
				$_choiceLong = $stop['Lng'];
				// get info
				$_stationInfo['Lat'] = $_choiceLat;
				$_stationInfo['Lng'] = $_choiceLong;
				$_stationInfo['routes'] = getBusNames($stop['bus']);
				$_stationInfo['address'] = $stop['Address'];

                $_stationInfo['name'] = $stop['Name'];
				
                //$_distValue = getDistance($_myPoint, new Point($_stop['Lat'], $_stop['Lng']));
				$_distValue = distance($_myPoint, new Point($stop['Lat'], $stop['Lng']));
				
				//$_stationInfo['duration'] = getDurationValue($_myPoint, new Point($_stop['Lat'], $_stop['Lng']));
				$_stationInfo['duration'] = round($_distValue / 83);
				$_stationInfo['distance'] = $_distValue;

//				echo "duration = ".round($_distValue / 83)."<br>";
			}
		} 

		return $_stationInfo;
	}

	

	function isHaveBus($_busNum, $_listBus) {
		return in_array($_busNum, explode(',', trim($_listBus)));
	}

    // comparator
    function cmpStation($a, $b) {
        return $a['distance'] - $b['distance'];
    }

	// All-near stations
	function findNearStations($_myPoint, $_limit, $_busNum) {
		$_listStations = array();
		$_stops = getAllStation();
		foreach ($_stops as $_stop) {
			// check if contains bus
			if ($_busNum != "0") {
					if (!isHaveBus($_busNum, $_stop['bus'])) continue;
			}

			// compute distance
			$_curDist = distance($_myPoint, new Point($_stop['Lat'], $_stop['Lng']));

			if ($_curDist <= $_limit) {
				$_oneStation = array();

				$_oneStation['Lat'] = $_stop['Lat'];
				
				$_oneStation['Lng'] = $_stop['Lng'];

				$_oneStation['routes'] = getBusNames($_stop['bus']);
				
				$_oneStation['name'] = $_stop['Name'];

				$_oneStation['address'] = $_stop['Address'];
				
				//$_distValue = getDistance($_myPoint, new Point($_stop['Lat'], $_stop['Lng']))
				$_distValue = distance($_myPoint, new Point($_stop['Lat'], $_stop['Lng']));
				
				//$_oneStation['duration'] = getDurationValue($_myPoint, new Point($_stop['Lat'], $_stop['Lng']));

				$_oneStation['duration'] = round($_distValue / 83);

				$_oneStation['distance'] = $_distValue;


				$_listStations[] = $_oneStation;
			}
		}
		usort($_listStations, "cmpStation");
        $_sz = min(10, count($_listStations));
        $_result = array();
        for ($_i = 0; $_i < $_sz; $_i++)
            $_result[] = $_listStations[$_i];
		return $_result;
	}


	// measure time
	$_time_start = microtime(true);
	function debugEndTime() {
		GLOBAL $_time_end;
		GLOBAL $_time_start;
		$time_end = microtime(true);
    	$time = $time_end - $time_start;
    	echo "Process Time: {$time}"; /**/
	}

	function debugBeginTime() {
		GLOBAL $_time_start;
		$time_start = microtime(true);
	}


	// Comparator
	function cmpOne($a, $b) {
		return $a['walk'] - $b['walk'];
	}

    function cmpAll($a, $b) {
        return $a['duration'] - $b['duration'];
    }

	// find path
	function findAllPath($_bgPoint, $_enPoint, $_bgName, $_enName) {
		/*print_r($_bgPoint);
		echo "<br>";
		print_r($_enPoint);
		echo "<br>"; */

		// find bus number
		//debugBeginTime();
		// start list
		$_startList = array();
		$_stationList = array();
		$_isStartPoint = array();
		// end list
		$_endList = array();
		$_isEndPoint = array();
		// list bus in one station
		$_listBusInStation = array();
		////////////////////////////////////////////////////////////////////////////
		$_allBus = array();
		$_stops = getAllStation();
		$_maximum_len = 2000;
		$_mapping = array(); // marking map
		foreach ($_stops as $_stop) {
			
			$_curDist = distance($_bgPoint, new Point($_stop['Lat'], $_stop['Lng']));

			if ($_curDist <= $_maximum_len) {
				$_candidateBus = explode(',', $_stop['bus']);
				// get all busnum
				foreach ($_candidateBus as $_bus) {
				 	
				 	if (!isset($_mapping[$_bus])) {
				 		$_mapping[$_bus] = 1;
				 		$_allBus[] = $_bus;
				 	}
				}
				$_startList[] = $_stop['ID'];
				$_isStartPoint[$_stop['ID']] = TRUE;
			}

			$_curDistEnd = distance($_enPoint, new Point($_stop['Lat'], $_stop['Lng']));
			if ($_curDistEnd <= $_maximum_len) {
				$_candidateBus = explode(',', $_stop['bus']);
				// get all busnum
				foreach ($_candidateBus as $_bus) {
				 	
				 	if (!isset($_mapping[$_bus])) {
				 		$_mapping[$_bus] = 1;
				 		$_allBus[] = $_bus;
				 	}
				}
				$_endList[] = $_stop['ID'];
				$_isEndPoint[$_stop['ID']] = TRUE;
			}
		}
		/////////////////////////////////////////////////////////////////////////////////
		$_inf = 1000000001.0; // inf len
		$_map_mark = array();
		$_adj = array(); // graph
		// all graph
		$_tot = 0;
		// distance array
		$_dist = array();
		$_atInfo = array();
		// all route info
		$_allRoute = array();
		$_allRouteInfo = array();
		$_allRouteID = array();

//		echo "number of bus must care = ".count($_allBus)."<br>";
		foreach ($_allBus as $_bus) {
				$_listRoute = getRoutesByBusnum($_bus);
  				foreach ($_listRoute as $_route) {
  					// routeID <---> routeOrder
  					/////////////////////////////////////////
          			$_stops = getRoutesById($_route['routeID']);
          			// add to list
					$_allRoute[] = $_stops;
					$_allRouteInfo[] = $_bus;
					$_allRouteID[] = $_route['routeID'];

          			// set pre-compute array
          			for ($_i = 0; $_i < count($_stops); $_i++) {
          				$_stationID = $_stops[$_i]['stationID'];
          				if (!isset($_stationID)) {
          					$_dist[$_stationID] = array();
          					$_atInfo[$_stationID] = array();
          				}
          				
          				$_dist[$_stationID][$_bus] = $_stops[$_i]['Len'];
          				$_atInfo[$_stationID][$_bus] = array($_stops[$_i]['routeID'], $_stops[$_i]['routeOrder']);
          			}
          			// end pre-compute 
          			$_tot += sizeof($_stops);
        			
          		}
          		//echo "end list<br>";
        }
       
        ////////////////////////////////////////////////////////////////////////////////////////////////
        $_ALLways = array();

        $_banned = array();
        // CASE 1: ONE PATH
       	$_resultSetOne = array();

        for ($_i = 0; $_i < count($_allRoute); $_i++) {
        	$_routes = $_allRoute[$_i];
        	$_bus = $_allRouteInfo[$_i];
        	$_routeID = $_allRouteID[$_i];

        	$_check = FALSE;

        	$_beginLen = 0;
        	$_endLen = 0;
        	
        	$_upPoint = new Point(0, 0);
        	$_dnPoint = new Point(0, 0);

        	
            // catch the min
        	$_minDist = $_inf;
            // check if contains start-points
        	foreach ($_routes as $_route) {
        		if (isset($_isStartPoint[$_route['stationID']])) {
        					$_check = TRUE;
        					
        					// save point
        					$_tmpUpPoint = new Point($_route['Lat'], $_route['Lng']);
                
                            $_newDist = distance($_bgPoint, $_tmpUpPoint);

                            if ($_newDist < $_minDist) {
                                $_upPoint = $_tmpUpPoint;
                                $_minDist = $_newDist;
                                $_bgOrder = $_route['routeOrder'];
                                $_beginLen = $_route['Len'];
                            }
        				}
        		}

        	if (!$_check) continue;

            $_minDist = $_inf;
        	$_check = FALSE;
        	// check if contains end-points
        	foreach ($_routes as $_route) {
        		if (isset($_isEndPoint[$_route['stationID']])) {
        					$_check = TRUE;
        					// save point
                            $_tmpDnPoint = new Point($_route['Lat'], $_route['Lng']);

                            $_newDist = distance($_enPoint, $_tmpDnPoint);
                            if ($_newDist < $_minDist) {
        					   $_dnPoint = $_tmpDnPoint;
                               $_enOrder = $_route['routeOrder'];
                               $_endLen = $_route['Len'];
                               $_minDist = $_newDist;
                            }
        				}
        		}	

        	if (!$_check) continue;
        	if ($_enOrder <= $_bgOrder) continue;

        	$_lengWalking = distance($_bgPoint, $_upPoint) + distance($_dnPoint, $_enPoint);

        	/*echo "choose bus: ".$_bus."<br>";
        	echo "walking = ".$_lengWalking."<br>";
        	echo "length: ".($_endLen - $_beginLen)."<br><br>"; 
        	echo "begin = ".$_bgOrder." ".$_enOrder."<br>"; */

        	$_lengthInBus = ($_endLen - $_beginLen);

            $_timeNeed = round($_lengWalking / 83) + round($_lengthInBus / 1000);
        	// new result add
        	$_resultSetOne[] = 
        	array("bus"=>$_bus, "routeID" => $_routeID, "begin" => $_bgOrder, "end" => $_enOrder, 
        		  "len" => $_lengthInBus, "walk" => $_lengWalking, 
        		  "upPoint" => $_upPoint, "dnPoint" => $_dnPoint, "w1" => distance($_bgPoint, $_upPoint), "w2"=> distance($_dnPoint, $_enPoint), "duration" => $_timeNeed);

        		$_banned[$_bus] = TRUE;
        }

        usort($_resultSetOne, "cmpAll");

        $_lim = min(4, count($_resultSetOne));
        ///////////////////////////////////////////////////////////////////////////////////////////////
        for ($_i = 0; $_i < $_lim; $_i++) {

            $_oneWay = array();
        	// on bus
        	$_rrs = makeJsonPathOneBus($_resultSetOne[$_i]['routeID'], $_resultSetOne[$_i]['begin'], $_resultSetOne[$_i]['end']);
        	$_rrs['type'] = 2;
        	$_rrs['busnum'] = $_resultSetOne[$_i]['bus'];
        	$_rrs['distance'] = round($_resultSetOne[$_i]['len']);
        	$_rrs['duration'] = round($_resultSetOne[$_i]['len'] / 1000);	
        	$_rrs['color'] = "#00C853";
        	
        	$_nameUp = $_rrs['stations'][0]['name'];
        	$_nameDn = $_rrs['stations'][count($_rrs['stations']) - 1]['name'];

        	// go up bus
        	$_rrone = array();
        	$_rrone['type'] = 1;
        	$_rrone['beginType'] = 1;
        	$_rrone['endType'] = 2;
        	$_upPoint = $_resultSetOne[$_i]['upPoint'];

        	$_rrone['beginCoord'] = array("Lat"=>"".$_bgPoint->lat, "Lng"=>"".$_bgPoint->lang, "name" => $_bgName);
        	$_rrone['endCoord'] = array("Lat"=>$_upPoint->lat, "Lng"=>$_upPoint->lang, "name" => $_nameUp);
        	$_rrone['distance'] = round($_resultSetOne[$_i]['w1']);
        	$_rrone['duration'] = round($_resultSetOne[$_i]['w1'] / 83);
        	$_oneWay[] = $_rrone;
        	

        	// on bus
        	$_oneWay[] = $_rrs;
        	
        	// go down bus

        	$_rrtwo = array();
        	$_rrtwo['type'] = 1;

        	$_rrtwo['beginType'] = 2;
        	$_rrtwo['endType'] = 3;

        	$_dnPoint = $_resultSetOne[$_i]['dnPoint'];

        	$_rrtwo['beginCoord'] = array("Lat"=>$_dnPoint->lat, "Lng"=>$_dnPoint->lang, "name" => $_nameDn);
        	$_rrtwo['endCoord'] = array("Lat"=>"".$_enPoint->lat, "Lng"=>"".$_enPoint->lang, "name" => $_enName);
        	$_rrtwo['distance'] = round($_resultSetOne[$_i]['w2']);
        	$_rrtwo['duration'] = round($_resultSetOne[$_i]['w2'] / 83);

        	$_oneWay[] = $_rrtwo;
        	//echo json_encode($_rrs);
    		
        	$_oneWayFinal = array();

        	$_oneWayFinal['listBus'] = "".$_resultSetOne[$_i]['bus'];
        	
        	$_oneWayFinal['instruction'] = $_oneWay;
        	
        	$_oneWayFinal['totalBus'] = 1;
        	
        	// time to need
        	$_oneWayFinal['duration'] = round($_resultSetOne[$_i]['len'] / 1000) +
        	round($_resultSetOne[$_i]['w1'] / 83) + round($_resultSetOne[$_i]['w2'] / 83);

            // add to result
    		$_ALLways[] = $_oneWayFinal;

    		$_rrs = null;
    		$_rrone = null;
    		$_rrtwo = null;
    	}

    	//echo json_encode($_ALLways, JSON_UNESCAPED_UNICODE);
    	//exit(0);
       
        ////////////////////////////////////////////////////////////////////////////////////////////////
        // Case 2: TWO PATHS

    	$_countRS = 0;

        for ($_i = 0; $_i < count($_allRoute); $_i++) 
        	for ($_j = 0; $_j < count($_allRoute); $_j++) {
        		if ($_allRouteInfo[$_i] != $_allRouteInfo[$_j]) {
        		if (isset($_banned[$_allRouteInfo[$_i]]) || isset($_banned[$_allRouteInfo[$_j]])) continue;
        			/// do it!!!
        			$_routeBegin = $_allRoute[$_i];
        			$_routeEnd = $_allRoute[$_j];

        			$_idRBg = $_allRouteID[$_i];
        			$_idREn = $_allRouteID[$_j];

        			$_check = FALSE;

        			$_startLen = 0.0;
        			$_endLen = 0.0;

        			$_bgOrder = -1;
        			$_enOrder = -1;

        			$_idStationOne = -1;
        			$_idStationTwo = -1;

        			$_upPoint = new Point(0, 0);
        			$_dnPoint = new Point(0, 0);

        			$_busnumOne = $_allRouteInfo[$_i];
        			$_busnumTwo = $_allRouteInfo[$_j];

                    $_minDist = $_inf;
        			// check if contains start-points
        			foreach ($_routeBegin as $_route) {
        				if (isset($_isStartPoint[$_route['stationID']])) {
        					$_check = TRUE;

        					
                            $_tmpUpPoint = new Point($_route['Lat'], $_route['Lng']);

                            $_newDist = distance($_tmpUpPoint, $_bgPoint);

                            if ($_newDist < $_minDist) {
                               $_startLen = $_route['Len'];
        					   $_bgOrder = $_route['routeOrder'];
        					   $_idStationOne = $_route['stationID'];
        					   // up-point
        					   $_upPoint->lat = $_route['Lat'];
        					   $_upPoint->lang = $_route['Lng'];
        					   // update shortest-path
                               $_minDist = $_newDist;
                            }
        				}
        			}

        			if (!$_check) continue;

        			$_check = FALSE;
                    $_minDist = $_inf;

        			// check if contains end-points
        			foreach ($_routeEnd as $_route) {
        				if (isset($_isEndPoint[$_route['stationID']])) {
        					$_check = TRUE;

                            $_tmpDnPoint = new Point($_route['Lat'], $_route['Lng']);
                            $_newDist = distance($_tmpDnPoint, $_enPoint);

                            if ($_newDist < $_minDist) {
        					   $_endLen = $_route['Len'];
        					   $_enOrder = $_route['routeOrder'];
        					   $_idStationTwo = $_route['stationID'];
        					   // down-point
        					   $_dnPoint->lat = $_route['Lat'];
        					   $_dnPoint->lang = $_route['Lng'];
                               // update shortest-path
                               $_minDist = $_newDist;
                            }

        				}
        			}	

        			if (!$_check) continue;
        			// check if have commond vertex
        			$_marking = array();
        			$_saveLen = array();

        			foreach ($_routeBegin as $_route) {
        				$_marking[$_route['stationID']] = $_route['routeOrder'];
        				$_saveLen[$_route['stationID']] = $_route['Len'];
        			}

        			$_totalMinDist = $_inf; // infinitive
        			$_cenOrder = -1;

        			foreach ($_routeEnd as $_route) {
        				
        					$_id = $_route['stationID'];
        				
        					if (isset($_marking[$_id])) {

        					$_orderOne = $_marking[$_id];
        					$_orderTwo = $_route['routeOrder'];


        					$_pathOneLen = $_saveLen[$_id] - $_dist[$_id][$_busnumOne];
        					$_pathTwoLen = $_endLen - $_route['Len'];
        					
        					if ($_pathOneLen <= 0.0) continue;
        					if ($_pathTwoLen <= 0.0) continue;

							$_currentDist = $_pathOneLen + $_pathTwoLen;

        					if ($_currentDist < $_totalMinDist && $_currentDist > 0) {
        							$_cenOrder = $_route['stationID'];
        							$_totalMinDist = $_currentDist;
        					}
        					
        				}
        			}
        			// clear
        			$_marking = null;

        			if ($_cenOrder == -1) continue;

        			/*echo "new way:<br>";
        			echo "use bus ".$_busnumOne." and bus ".$_busnumTwo."<br>";
        			echo "change: ".$_idStationOne." center = ".$_cenOrder." end = ".$_idStationTwo."<br>";
        			
        			echo "total Length: ".$_totalMinDist."<br><br>"; */

                    // can not
                    if ($_bgOrder >= $_orderOne) continue;
                    if ($_orderTwo >= $_enOrder) continue;

                    // counter
        			$_countRS++;
        			if ($_countRS > 5) continue;
                    

        			// compute distance
        			$_w1 = distance($_bgPoint, $_upPoint);
        			$_w2 = distance($_dnPoint, $_enPoint);
        			//
        			$_oneWay = array();
        			// go up bus
        			$_rrone = array();
        			$_rrone['type'] = 1;
        			$_rrone['beginType'] = 1;
        			$_rrone['endType'] = 2;

 					$_nameUp = getStationNameByID($_idStationOne);
 					$_rrone['beginCoord'] = array("Lat"=>"".$_bgPoint->lat, "Lng"=>"".$_bgPoint->lang, "name" => $_bgName);
 					$_rrone['endCoord'] = array("Lat"=>$_upPoint->lat, "Lng"=>$_upPoint->lang, "name" => $_nameUp);
        			$_rrone['distance'] = round($_w1);
        			$_rrone['duration'] = round($_w1 / 83);
        			$_oneWay[] = $_rrone;
        			// on bus
        			$_rrs = makeJsonPathOneBus($_idRBg, $_bgOrder, $_orderOne);
        			$_rrs['type'] = 2;
        			$_rrs['busnum'] = $_busnumOne;
        			$_rrs['distance'] = round($_pathOneLen);
        			$_rrs['duration'] = round($_pathOneLen / 1000);	
        			$_rrs['color'] = "#00C853";
        			$_oneWay[] = $_rrs;
        			// change bus
        			$_changing = array();
        			$_changing['type'] = 3;
        			$_changing['fromBus'] = $_busnumOne;
        			$_changing['toBus'] = $_busnumTwo;

                    $_sz = count($_rrs['stations']);

                    $_changing['changeCoord'] = array("Lat" => $_rrs['stations'][$_sz - 1]['Lat'], 
                        "Lng" => $_rrs['stations'][$_sz - 1]['Lng'], 
                        "name" => $_rrs['stations'][$_sz - 1]['name']);

        			$_oneWay[] = $_changing;
        			// on bus
        			$_rrs2 = makeJsonPathOneBus($_idREn, $_orderTwo, $_enOrder);
        			$_rrs2['type'] = 2;
        			$_rrs2['busnum'] = $_busnumTwo;
        			$_rrs2['distance'] = round($_pathTwoLen);
        			$_rrs2['duration'] = round($_pathTwoLen / 1000);	
        			$_rrs2['color'] = "#01579B";
        			$_oneWay[] = $_rrs2;
        			// go down bus
        			$_rrtwo = array();
        			$_rrtwo['type'] = 1;
        			$_rrtwo['beginType'] = 2;
        			$_rrtwo['endType'] = 3;
        			//$_dnPoint = $_resultSetOne[$_i]['dnPoint'];
        			$_nameDn = getStationNameByID($_idStationTwo);
        			$_rrtwo['beginCoord'] = array("Lat"=>$_dnPoint->lat, "Lng"=>$_dnPoint->lang, "name" => $_nameDn);
        			$_rrtwo['endCoord'] = array("Lat"=>"".$_enPoint->lat, "Lng"=>"".$_enPoint->lang, "name" => $_enName);
        			$_rrtwo['distance'] = round($_w2);
        			$_rrtwo['duration'] = round($_w2 / 83);

        			$_oneWay[] = $_rrtwo;

        			// time: 50
        			// routes: 55,44
        			$_oneWayFinal = array();
        			$_oneWayFinal['instruction'] = $_oneWay;
        			$_oneWayFinal['listBus'] = $_busnumOne.",".$_busnumTwo;
        			$_oneWayFinal['totalBus'] = 2;
        			$_oneWayFinal['duration'] = round($_pathOneLen / 1000) + round($_pathTwoLen / 1000) + round($_w1/83) + round($_w2 / 83);
        			/////////////////////////////
        			$_ALLways[] = $_oneWayFinal;
        		}
        	}
        usort($_ALLways, "cmpAll");

        echo json_encode($_ALLways, JSON_UNESCAPED_UNICODE);
    	exit(0);
		/**/
	}
?>