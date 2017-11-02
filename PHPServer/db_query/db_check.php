<?php
// ============= CHECK ==========================================================
 	 /* All check function place here */

 	// Check if exist Quiz
 	function checkExistQuiz($quizID) {
		GLOBAL $db;	
		$sql = "SELECT quizID FROM quiz WHERE quizID = '$quizID' ";
   		
   		$rs = mysqli_query($db, $sql);

		if (isset($rs) && mysqli_num_rows($rs) > 0) {
			return true;
		}
		else
			return false;
	}

	// Check if exist Mall Quest
	function checkExistMall($mallID) {
		GLOBAL $db;
		$sql = "SELECT ID FROM mall WHERE ID = '$mallID' ";
		$rs = mysqli_query($db, $sql);

		if (isset($rs) && mysqli_num_rows($rs) > 0) return true;
		else
			return false;
	}

	function checkQA($_quizID, $_ansID) {
		GLOBAL $db;
		$_sql = "SELECT * FROM mapqa WHERE ansID = '$_ansID' AND quizID = '$_quizID'";
		$_rs = mysqli_query($db, $_sql);
		if (isset($_rs) && mysqli_num_rows($_rs) > 0) return true;
		else return false;
		mysqli_free_result($_rs);		
	}

	function checkBelong($_conID, $_quizID) {
		GLOBAL $db;
		$_sql = "SELECT * FROM belongcon WHERE conID = '$_conID' AND quizID = '$_quizID'";
		$_rs = mysqli_query($db, $_sql);
		if (isset($_rs) && mysqli_num_rows($_rs) > 0) return true;
		else return false;
		mysqli_free_result($_rs);
	}

	function checkExistBank($conID) {
		GLOBAL $db;	
		$sql = "SELECT * FROM bank WHERE conID = '$conID' ";
   		$rs = mysqli_query($db, $sql);   		
		if (isset($rs) && mysqli_num_rows($rs) > 0) {
			return true;
		}
		else
			return false;
	}

	function checkExistActivity($conID, $username) {
		GLOBAL $db;	
		$sql = "SELECT * FROM activity WHERE conID = '$conID' AND username = '$username' ";
   		$rs = mysqli_query($db, $sql);

		if (isset($rs) && mysqli_num_rows($rs) > 0) {
			return true;
		}
		else
			return false;
	}

	function checkExistRepoto($conID) {
		GLOBAL $db;	
		$sql = "SELECT * FROM repoto WHERE conID = '$conID' ";
   		$rs = mysqli_query($db, $sql);
   		   		
		if (isset($rs) && mysqli_num_rows($rs) > 0) {
			return true;
		}
		else
			return false;
	}

	function isDoneContest($conID, $username) {
		GLOBAL $db;
		$_sql = "SELECT * FROM activity WHERE conID = '$conID' AND username = '$username' ";
		$_rs = mysqli_query($db, $_sql);
		if (isset($_rs) && mysqli_num_rows($_rs) > 0) {
			$_row = $_rs -> fetch_assoc();
			return $_row['done'];
		}
	}

	function checkExistContest($conID) {
		GLOBAL $db;	
		$sql = "SELECT * FROM contest WHERE conID = '$conID' ";
   		$rs = mysqli_query($db, $sql);
   		   		
		if (isset($rs) && mysqli_num_rows($rs) > 0) {
			return true;
		}
		else
			return false;
	}
	function checkLogin($_user, $_pass) {
		GLOBAL $db;
		$_sql = "SELECT username, password FROM account WHERE username = '$_user' AND password = '$_pass'";
		$_rs = mysqli_query($db, $_sql);
		if (isset($_rs) && mysqli_num_rows($_rs) > 0) return true;
		else return false;
		mysqli_free_result($_rs);
	}
	function checkPassword($_user, $_conPass) {
		GLOBAL $db;
		$_sql = "SELECT password FROM account WHERE username = '$_user'";
		$_rs = mysqli_query($db, $_sql);
		if (isset($_rs) && mysqli_num_rows($_rs) > 0)
			$_row  = $_rs -> fetch_assoc();
		if ($_row['password'] == $_conPass) return true;
		else return false;
		mysqli_free_result($_rs);
	}
	function checkAdmin($_user) {
		GLOBAL $db;
		$_sql = "SELECT role FROM account WHERE username = '$_user'";
		$_rs = mysqli_query($db, $_sql);
		if (isset($_rs) && mysqli_num_rows($_rs) > 0)
			$_row = $_rs ->fetch_assoc();
		return $_row['role'];
		mysqli_free_result($_rs);
	}
	function checkExistedContest($_conID) {
		GLOBAL $db;
		$_sql = "SELECT * FROM contest WHERE conID = '$_conID'";
		$_rs = mysqli_query($db, $_sql);
		if (isset($_rs) && mysqli_num_rows($_rs) > 0) return true;
		else return false;
		mysqli_free_result($_rs);
	}
	function checkVote($_user, $_cmtID) {
		GLOBAL $db;
		$_sql = "SELECT * FROM vote WHERE username = '$_user' AND commentID = '$_cmtID'";
		$_rs = mysqli_query($db, $_sql);
		if (isset($_rs) && mysqli_num_rows($_rs) > 0) return true;
		else return false;
		mysqli_free_result($_rs);
	}

	// new
	function checkExistCmt($_parID) {
		GLOBAL $db;
		$_sql = "SELECT * FROM comment WHERE ID = '$_parID' AND parID = -1 ";
		$_rs = mysqli_query($db, $_sql);
		if (isset($_rs) && mysqli_num_rows($_rs) > 0) return true;
		else return false;
	}

	?>