<?php
	
	
	$_allIntructions = array();

	$_inOne = array();
	$_inOne['distance'] = 1350;
	$_inOne['duration'] = 50;
	$_inOne['instruction'] = array();

	$_subPath = array();
	$_subPath['type'] = 1;
	$_subPath['beginType'] = 1;
	$_subPath['endType'] = 2;
	

	$_point = array();
	$_point['Lat'] = 10.768116;
	$_point['Lng'] = 106.688573;
	$_point['name'] = "FPT University";

	$_subPath['beginCoord'] = $_point;

	$_point2 = array();
	$_point2['Lat'] = 10.767676;
	$_point2['Lng'] = 106.689362;
	$_point2['name'] = "Station 1";

	$_subPath['endCoord'] = $_point2; 
	
	$_subPath['duration'] = 12;
	$_subPath['distance'] = 200;


	$_inOne['instruction'][] = $_subPath;

	$_allIntructions[] = $_inOne;

	echo json_encode($_allIntructions);
?>