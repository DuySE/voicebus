<?php
// ============= CREATE - INSERT ===============================================
  	
    function addBusInfo($_busnum, $_name) {
      GLOBAL $db;
      $sql = "INSERT INTO businfo(busnum, name) VALUES ('$_busnum', '$_name')";
      mysqli_query($db, $sql);
    }

  	function addRoutesInfo($_routeID, $_pathPoints, $_stationID, $_routeOrder, $_Lat, $_Lng) {
  		GLOBAL $db;
  		$sql = "INSERT INTO routesinfo(routeID, pathPoints, stationID, 	routeOrder, Lat, Lng) VALUES ('$_routeID', '$_pathPoints', '$_stationID', '$_routeOrder', '$_Lat', '$_Lng')";
  		
  		mysqli_query($db, $sql);
  	}
  	
  	function addRouteMapping($_busNum, $_routeID) {
  		GLOBAL $db;
  		$sql = "INSERT INTO mapping(busnum, routeID) VALUES ('$_busNum', '$_routeID')";
  		mysqli_query($db, $sql);
  	}


  	function addStops($_ID, $_code, $_name, $_Lat, $_Lng, $_address, $_busNum) {
  		
  		GLOBAL $db; 
  		$sql = "INSERT INTO stops(ID, Code, Name, Lat, Lng, Address, bus) VALUES ('$_ID', '$_code', '$_name', '$_Lat', '$_Lng', '$_address', '$_busNum')";
  		mysqli_query($db, $sql);
  	}

    function addStation($_ID, $_code, $_name, $_Lat, $_Lng, $_address, $_busNum) {
      
      GLOBAL $db; 
      $sql = "INSERT INTO stations(ID, Code, Name, Lat, Lng, Address, bus) VALUES ('$_ID', '$_code', '$_name', '$_Lat', '$_Lng', '$_address', '$_busNum')";
      mysqli_query($db, $sql);
    }