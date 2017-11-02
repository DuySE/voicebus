<?php 
	// set delay to infinitive

	ini_set('max_execution_time', 0);
	include("../dbcon.php");
	
	$time_start = microtime(true);
	//
	$_buss = array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 22, 23, 24, 25, 27, 28, 29, 30, 31, 32, 33, 34, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 601, 602, 603, 604, 61, 611, 613, 614, 616, 617, 618, 62, 621, 623, 625, 626, 627, 628, 629, 6210, 6211, 63, 64, 65, 66, 68, 69, 70, 701, 702, 703, 705, 71, 72, 73, 74, 76, 77, 78, 79,81,84,85,86,87,88,89,90,91,93,94,95,96,99,100,101,102,103,104,107,109,110,119,120,122,123,124,126,127,128,139,140,144,145,146,148,149,150,151,152,159);

	$_jsonLink = array('z0dif', 'ed293', 'rxbev', 'yh32f', 'h7iiv', '10x6d3', '8cdbr', '1em59z', 're8on', 'm1c8n', 'yjfxz', 'h9vef', '60s2f', 'ckjpz', 'vmf2v', '184is7', '12rmc7', 'rgduf', 'g55cn', '1ai97b', '1h20uv', '62x87', '1ccp6f', '1djkdz', 'kdbqv', 'wvfg7', '157hx3', 'tyel3', 'in63b', '18u8pj', '19fobb', 'g9fo7', 'ypvfb', 'j8lp3', 'zbb13', 'kfgwn', '677jr', '10kbef', '17431z', 'jw6gn', 'ngs3b', 'qfy47', 'r1dpz', '19hth3', '1g1l4n', '14qcmv', '69cpj', '10mgk7', 'pb82f', '11tbrr', '12erdj', '18yj13', '1fiaon', '1g3qaf', 'b0tjr', '15dxef', 'u4u2f', '16mxrr', '178ddj', 'pystz', 'qk8fr', 'r5o1j', '1fkfuf', '1497cn', 'sxyuv', 'boebb', '85xuf', '12j1p3', 'x6593', '9y8nr', 'ajo9j', 'b53vb', 'bqjh3', '165shj', '10sw1j', 'jjbhz', '194yif', 'lx1x3', 'altfb', 'b7913', '1hh0zb', 'cg9ef', 'd1p07', '7osk7', '1dykif', '18lo2f', 'xvv6f', 'siyqf', 'z2qdz','butsn', 'ts6tj', '10byh3', 'p0pzb', '182lc7', 'ewcp3', '9jg93', 'rzw07', 'aqbgn', 'z539j','1fve4v','9lm6n','eyimn','yltlb','1h29cf','15t60f','uhxin','111p67','vosq7','wvnxr','rirhr','g7izz','1btn7z','uk2of','ju9sf','94gwf','abc3z','1gl427','159vkf','67g1b','6svn3','11rf3j','ejii7','rn1tb','s8hf3','gx8xb','1019fz','10mp1r','1dq8cv','12ezv3','1ex3kf','samkv','11a9tb','11vpf3','12h50v','x48kv','149fu7','ywje7','1ben3j','o8vnz','1d932n');


	$_cnt_route = 0;

	for ($_cursor = 0; $_cursor < count($_buss); $_cursor++) {


		$_busNum = $_buss[$_cursor];

		$json = file_get_contents('https://api.myjson.com/bins/'.$_jsonLink[$_cursor]);


		echo "num = ".$_busNum."  json = ".$_jsonLink[$_cursor]."<br>";


		$data = json_decode($json, TRUE);


		$_routeOne = -1;
		$_routeTwo = -1;

		foreach ($data as $_route) {

		   if ($_routeOne == -1) $_routeOne = $_route['RouteVarId'];
			else 
				if ($_routeTwo == -1 && $_routeOne != $_route['RouteVarId']) $_routeTwo = $_route['RouteVarId'];

		 // add station
		// addStation($_route['StationId'], $_route['StationCode'], $_route['StationName'], $_route['Lat'], $_route['Lng'], $_route['Address'], $_busNum);

	
		  $_routeID = $_cnt_route + 1;
		  
		  if ($_route['RouteVarId'] == $_routeTwo) $_routeID = $_cnt_route + 2;

		  // add bus data to db
		 // addRoutesInfo($_routeID, $_route['pathPoints'], $_route['StationId'], $_route['StationOrder'], $_route['Lat'], $_route['Lng']);

		}  

		//addRouteMapping($_busNum, $_cnt_route + 1);
		//addRouteMapping($_busNum, $_cnt_route + 2);

		$_cnt_route += 2;
		echo "link = ".'https://api.myjson.com/bins/'.$_jsonLink[$_cursor]."<br>";
		echo "finish<br>";
	}
	//echo $data;
    $time_end = microtime(true);
    $time = $time_end - $time_start;
    echo "Process Time: {$time}"; /**/
?>