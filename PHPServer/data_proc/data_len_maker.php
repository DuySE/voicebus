<?php 
	// set delay to infinitive
	ini_set('max_execution_time', 0);
	include("../dbcon.php");
	include("../googleApi.php");

	$time_start = microtime(true);
	//////////////////////////////


$_allBus = getAllBusNum();
foreach ($_allBus as $_bus)  {

  $_listRoute = getRoutesByBusnum($_bus['busnum']);

  foreach ($_listRoute as $_route) {
          $_stops = getRoutesById($_route['routeID']);
  
          $_listPathPoints = array();
          $_distance = array();

          // get all path points
          foreach ($_stops as $_stop) {
                $_insidePath = array_map('trim', explode(' ', trim($_stop['pathPoints'])));
      
          foreach ($_insidePath as $_onePoint) {
                if (strlen($_onePoint) == 0) continue;          
                $_latAndLng = explode(',', $_onePoint);
          
                $_point = array();
                $_point['lat'] = $_latAndLng[1];
                $_point['lng'] = $_latAndLng[0];
        
                $_listPathPoints[] = $_point;
         }    
        }
    // compute distance
    $_distance[] = 0;
    $_sumDist = 0;

    for ($_i = 1; $_i < count($_listPathPoints); $_i++) {
      $p1 = new Point($_listPathPoints[$_i - 1]['lat'], $_listPathPoints[$_i - 1]['lng']);
      $p2 = new Point($_listPathPoints[$_i]['lat'], $_listPathPoints[$_i]['lng']);
      $_sumDist = $_sumDist + distance($p1, $p2);
      $_distance[] = $_sumDist;
    }

    // assign distance to station
    $sz = count($_listPathPoints);

      foreach ($_stops as $_stop) { 
       // print_r($_stop);

         // echo $_stop['Lat']." + ".$_stop['Lng']."<br>";

          $_myPoint = new Point($_stop['Lat'], $_stop['Lng']);

          $_minDist = distance($_myPoint, new Point($_listPathPoints[0]['lat'], $_listPathPoints[0]['lng']));
          $_curRet = 0;

          for ($_i = 1; $_i < $sz; $_i++) {

                $_curDist = distance($_myPoint, new Point($_listPathPoints[$_i]['lat'], $_listPathPoints[$_i]['lng']));

               // echo "this dist = ".$_curDist."<br>";
                
                if ($_curDist < $_minDist) {
                    $_minDist = $_curDist;
                    $_curRet = $_i;
                }
          }

         // echo "near => ".$_distance[$_curRet]." distance = ".$_minDist."<br>";
          //updateRouteLengInfo($_stop['routeID'], $_stop['routeOrder'], $_distance[$_curRet]);
         // echo "update routeID = ".$_stop['routeID']." routeOrder = ".$_stop['routeOrder']." distance = ".$_distance[$_curRet]."<br>";


      }
  }
	
  }
	//////////////////////////////
    $time_end = microtime(true);
    $time = $time_end - $time_start;
    echo "Process Time: {$time}"; /**/
?>