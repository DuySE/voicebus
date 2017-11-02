<?php 
	// ============= UPDATE ========================================================
  	 /* All update function place here */

  	function updateRouteLengInfo($_routeID, $_routeOrder, $_newLen) {
  		echo $_routeID." ".$_routeOrder." new Length = ".$_newLen."<br>";

  		GLOBAL $db;
  		$_sql = "UPDATE routesinfo SET Len = '$_newLen' WHERE routeID = '$_routeID' AND routeOrder = '$_routeOrder' ";
  		mysqli_query($db, $_sql);
  	}



  	/////////////////////////////////////////////////////////////////////////////////////
  	function addBusToStation($_code, $_busnum) {
  		
  		echo $_code." ".$_busnum."<br>";

  		GLOBAL $db;
  		$_sql = "UPDATE station SET bus = '$_busnum' WHERE Code = '$_code' ";

  		mysqli_query($db, $_sql);
  	}
?>