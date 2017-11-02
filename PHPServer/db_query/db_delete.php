<?php
	function deleteVote($_user, $_cmtID) {
		GLOBAL $db;
		$_sql = "DELETE FROM vote WHERE username = '$_user' AND commentID = '$_cmtID'";
		mysqli_query($db, $_sql);
	}

	function deleteRepoto($_conID) {
		GLOBAL $db;
		$_sql = "DELETE FROM repoto WHERE conID = '$_conID'";
		mysqli_query($db, $_sql);
	}

	function delPrize($_user, $_prizeName) {
		GLOBAL $db;
		$_sql = "DELETE FROM achievement WHERE username = '$_user' AND namePrize = '$_prizeName'";
		mysqli_query($db, $_sql);
	}
?>