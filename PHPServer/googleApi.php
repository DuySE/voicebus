<?php
    // google API Url
    $key = "AIzaSyDjqBZOq-GvYnl1mgPrVn8rYwONXPZLZno";
	$url = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial";

	// point
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

	// google api - get distance need for walking
	function getDistance(Point $st, Point $fn) {
		GLOBAL $url;
		GLOBAL $key;

		$requestUrl = $url."&origins=".$st->getCoor()."&destinations=".$fn->getCoor()."&key=".$key;
		//echo $requestUrl;

		$xml = file_get_contents($requestUrl);
		$data = json_decode($xml, TRUE);
		// get distance
		return $data['rows'][0]['elements'][0]['distance']['value'];
	}

	// google api - get time need for walking
	function getDurationValue(Point $st, Point $fn) {
		GLOBAL $url;
		GLOBAL $key;
		$requestUrl = $url."&origins=".$st->getCoor()."&destinations=".$fn->getCoor()."&key=".$key;
		$xml = file_get_contents($requestUrl);
		$data = json_decode($xml, TRUE);
		return $data['rows'][0]['elements'][0]['duration']['value'];
	}	

	// compare function
	function cmpTime($_a, $_b) {
		$_timeA = getDurationValue(new Point($_a['Lat'], $_a['Long']));
		$_timeB = getDurationValue(new Point($_b['Lat'], $_b['Long']));
		return $_timeA < $_timeB;
	}

	// google api - get time need for walking
	function getDurationValueText(Point $st, Point $fn) {
		GLOBAL $url;
		GLOBAL $key;
		$requestUrl = $url."&origins=".$st->getCoor()."&destinations=".$fn->getCoor()."&key=".$key;
		$xml = file_get_contents($requestUrl);
		$data = json_decode($xml, TRUE);
		return $data['rows'][0]['elements'][0]['duration']['text'];
	}


	// direct distance on spheere
	function distance(Point $st, Point $fn) {
		if ($st->lat == $fn->lat && $st->lang == $fn->lang) return 0;
		$_radius = 6371000;
		$_dLon = deg2rad($fn->lang - $st->lang);
		$_lat1 = deg2rad($st->lat);
		$_lat2 = deg2rad($fn->lat);
		$_dist = acos(sin($_lat1) * sin($_lat2) + cos($_lat1) * cos($_lat2) * cos($_dLon)) * $_radius;
		return $_dist;
	}

	// api to get coordinate
	function addressToLocation($_address) {

		$_key2 = 'AIzaSyBfNxDyeUt2iyd3zfpNTJjXz6LUNTFw9_A';

		$_url = 'https://maps.googleapis.com/maps/api/geocode/json?address='.urlencode($_address)."&key=".$_key2;

		$_xml = file_get_contents($_url);
		$_data = json_decode($_xml, TRUE);
		// get location
		$_Lat = $_data["results"][0]["geometry"]["location"]['lat'];
		$_Lng = $_data["results"][0]["geometry"]["location"]['lng'];
		return new Point($_Lat, $_Lng);
	}
?>