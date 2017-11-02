<?php
		
	function getAllBusNum() {
		GLOBAL $db;
		$_sql = "SELECT busnum FROM mapping GROUP BY busnum";
		$_rs = mysqli_query($db, $_sql);
		$_list = array();
		if (isset($_rs)) {
			while ($_row = $_rs -> fetch_assoc()) $_list[] = $_row;
		}
		mysqli_free_result($_rs);
		return $_list;
	}

	function getRoutesByBusnum($_busnum) {
		GLOBAL $db;
		$_sql = "SELECT * FROM mapping WHERE busnum = '$_busnum' ";
		$_rs = mysqli_query($db, $_sql);
		$_list = array();
		if (isset($_rs)) {
			while ($_row = $_rs -> fetch_assoc()) $_list[] = $_row;
		}
		mysqli_free_result($_rs);
		return $_list;
	}

	function getAllRoutesComp() {
		GLOBAL $db;
		$_sql = "SELECT stationID FROM  routesinfo GROUP BY stationID ";
		$_rs = mysqli_query($db, $_sql);

		$_list = array();
		if (isset($_rs)) {
			while ($_row = $_rs -> fetch_assoc()) $_list[] = $_row;
		}
		mysqli_free_result($_rs);
		return $_list;
	}


	function getRoutesById($_routeID) {
		GLOBAL $db;
		$_sql = "SELECT * FROM routesinfo WHERE routeID = '$_routeID' ORDER BY routeOrder";
		$_rs = mysqli_query($db, $_sql);
		$_listStop = array();
		if (isset($_rs)) {
			while ($_row = $_rs -> fetch_assoc())
				$_listStop[] = $_row;
		}
		mysqli_free_result($_rs);
		return $_listStop;
	}

	function getAllStation() {
		GLOBAL $db;
		$_sql = "SELECT * FROM stations";
		$_rs = mysqli_query($db, $_sql);

		$_list = array();
		if (isset($_rs)) {
			while ($_row = $_rs -> fetch_assoc()) $_list[] = $_row;
		}
		mysqli_free_result($_rs);
		return $_list;
	}


	function getStationByCode($_code) {
		GLOBAL $db;
		$_sql = "SELECT * FROM stops WHERE Code = '$_code'";
		$_rs = mysqli_query($db, $_sql);

		$_list = array();
		if (isset($_rs)) {
			while ($_row = $_rs -> fetch_assoc()) $_list[] = $_row;
		}
		mysqli_free_result($_rs);
		return $_list;	
	}


	function getStationNameByID($_stationID) {
		GLOBAL $db;
		$_sql = "SELECT * FROM stations WHERE ID = '$_stationID'";


		$_rs = mysqli_query($db, $_sql);

		if (isset($_rs) && mysqli_num_rows($_rs) > 0) {
			$row = $_rs->fetch_assoc();
			return $row["Name"];	
		}
	}

	function getBusName($_busnum) {
		GLOBAL $db;
		$_sql = "SELECT * FROM businfo WHERE busnum = '$_busnum'";
		$_rs = mysqli_query($db, $_sql);

		if (isset($_rs) && mysqli_num_rows($_rs) > 0) {
			$row = $_rs->fetch_assoc();
			return $row["name"];	
		}
	}
	///============================================================================