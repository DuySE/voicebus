<?php 

	//$_txt = "Từ đây đến quận 1 đi như thế nào?";
	// Tìm xe đến quận 1

	$_action = array(0 => "bắt", "đi");
	$_object = array(0 => "xe", "xe buýt");
	$_question1 = array(0 => "thế nào", "như thế nào", "làm sao", "bằng cách nào", "như nào", "làm thế nào", "nào");
	$_question2 = array(0 => "thế nào", "như thế nào", "làm sao", "bằng cách nào", "như nào", "làm thế nào");
	$_object2 = array(0 => "xe");

	$_fromLocal = array(0 => "đây", "nơi đây", "chỗ đây", "nơi này", "chỗ này", "vị trí của tôi");

	function isQuantityMore($_sub) {
		return ($_sub == "những" || $_sub == "các");
	}

	function isStation($_sub) {
		return ($_sub == "trạm" || $_sub == "bến");
	}

 	function isQuantityOne($_sub) {
		return ($_sub == "một");
	}

	function isOpen($_sub) {
		return ($_sub == "từ" || $_sub == "Từ");
	}

	function isClose($_sub) {
		return ($_sub == "đến" || $_sub == "tới" || $_sub == "đi" || $_sub == "Đến" || $_sub == "Tới" || $_sub == "Đi" || $_sub == "ra" || $_sub == "Ra");
	}

	function isOpenPair($_sub1, $_sub2) {
		return (($_sub1 == "đi" && $_sub2 == "đến") || ($_sub1 == "đi" && $_sub2 == "tới") || ($_sub1 == "Đi" && $_sub2 == "đến") || ($_sub1 == "Đi" && $_sub2 == "tới") || ($_sub1 == "Đi" && $_sub2 == "ra") || ($_sub1 == "đi" && $_sub2 == "ra"));
	}

	function tripleOwn($_sub1, $_sub2, $_sub3) {
		return ($_sub1 == "có" && $_sub2 == "xe" && $_sub3 == "buýt");
	}

	function doubleOwn($_sub1, $_sub2) {
		return (($_sub1 == "có" && $_sub2 == "xe") || ($_sub1 == "có" && $_sub2 == "buýt"));
	}

	

	// change iff current local
	function changeBegin($_bg) {
		GLOBAL $_fromLocal;
		$_n = count($_bg);
		foreach($_fromLocal as $_local) {
			$_list = explode(" ", $_local);
			$_m = count($_list);
			if ($_n  != $_m) continue;
			$_check = TRUE;
			for ($_i = 0; $_i < $_n; $_i++)
				if ($_list[$_i] != $_bg[$_i]) { $_check = FALSE; break; }
			if ($_check) {
				$_rss = array();
				return $_rss;
			}
		}
		return $_bg;
	}


	function findPost($_txt, $_key) {
		$_listKey = explode(" ", $_key);

		$_n = count($_txt);
		$_m = count($_listKey);
		$_cnt = 0;
		$_pos = -1;
		$_theEnd = FALSE;

		for ($_i = 0; $_i < $_n - $_m + 1; $_i++) {
			$_check = TRUE;
			for ($_j = 0; $_j < $_m; $_j++)
				if ($_listKey[$_j] != $_txt[$_i + $_j]) {
					$_check = FALSE;
				}
			if ($_check) {
				$_cnt++;
				$_pos = $_i;
				if ($_i == $_n - $_m) $_theEnd = TRUE;

			}
		}
		$_rs = array();
		$_rs['cnt'] = $_cnt;
		$_rs['pos'] = $_pos;
		$_rs['te'] = $_theEnd;
		return $_rs;
	}

	function isQuestionPharse($_txt) {
		GLOBAL $_action;
		GLOBAL $_question1;
		GLOBAL $_question2;
		GLOBAL $_object;
		GLOBAL $_object2;
		$_cnt = 0;

		$_n = count($_txt);

		foreach ($_action as $_act) {
			foreach ($_object as $_obj) {
				foreach ($_question1 as $_quest) {
					$_questPharse = $_act . " " . $_obj . " " . $_quest; 

					if (count(explode(" ", $_questPharse)) != $_n) continue;

					$_rs = findPost($_txt, $_questPharse);
					if ($_rs['cnt'] > 0) {
						$_cnt++;
					}
				}
				foreach ($_question2 as $_quest) {
					$_questPharse = $_act . " " . $_quest;
					
					if (count(explode(" ", $_questPharse)) != $_n) continue;

					$_rs = findPost($_txt, $_questPharse);
					if ($_rs['cnt'] > 0) {
						$_cnt++;
					}
				}
			}
		}

		return ($_cnt > 0);
	}

	function clearQuestionPharse($_txt) {
		
		GLOBAL $_action;
		GLOBAL $_question1;
		GLOBAL $_question2;
		GLOBAL $_object;
		GLOBAL $_object2;

		$_cnt = 0;
		$_pos = -1;
		$_isEnd = FALSE;

		foreach ($_action as $_act) {
			foreach ($_object as $_obj) {
				foreach ($_question1 as $_quest) {
					$_questPharse = $_act . " " . $_obj . " " . $_quest; 
					$_rs = findPost($_txt, $_questPharse);
					if ($_rs['cnt'] > 0) {
						$_cnt += $_rs['cnt'];
						$_pos = $_rs['pos'];
						$_isEnd |= $_rs['te'];
					}
				}
				
			}
		}

		foreach ($_action as $_act)
		foreach ($_question2 as $_quest) {
					$_questPharse = $_act . " " . $_quest;
					$_rs = findPost($_txt, $_questPharse);
					if ($_rs['cnt'] > 0) {
						$_cnt += $_rs['cnt'];
						$_pos = $_rs['pos'];
						$_isEnd |= $_rs['te'];
					}
				}


		$_rss = array();
		$_rss['stt'] = TRUE;

		if ($_cnt > 1) {
			$_rss['stt'] = FALSE;

			return $_rss;
		}

		foreach ($_action as $_act) {
			$_rs = findPost($_txt, $_act);
			if ($_rs['cnt'] > 1) {
				$_rss['stt'] = FALSE;
						
				return $_rss;
			}
		}

		foreach ($_object2 as $_obj) {
			$_rs = findPost($_txt, $_obj);
			if ($_rs['cnt'] > 1) {		

				$_rss['stt'] = FALSE;
				return $_rss;
			}
		}

		foreach ($_question2 as $_quest) {
			$_rs = findPost($_txt, $_obj);
			if ($_rs['cnt'] > 1) {
				$_rss['stt'] = FALSE;
				
				return $_rss;
			}			
		}
		
		// no-result
		if ($_cnt == 0) {
			$_rss['stt'] = TRUE;
			$_rss['pos'] = -1;
			return $_rss;
		}

		// last check
		if ($_isEnd == FALSE) {
			$_rss['stt'] = FALSE;

			return $_rss;	
		}

		$_rss['pos'] = $_pos;
		$_rss['stt'] = TRUE;

		return $_rss;
	}

	function type3($_txt) {

		$_words = explode(" ", $_txt);
		$_n = count($_words);
		$_cnt = 0;
		$_result = array();
		
		$_beginSet = array();
		
		$_endSet = array();

		/////////////////////////////////////
		for ($_i = 0; $_i < $_n; $_i++)
			if (isOpen($_words[$_i]))
				for ($_j = $_i + 1; $_j < $_n; $_j++)
					if (isClose($_words[$_j])) {

						// open pair
						if ($_j + 1 < $_n && isOpenPair($_words[$_j], $_words[$_j + 1])) $_j++;

						$_p = array();
						for ($_k = $_j + 1; $_k < $_n; $_k++) $_p[] = $_words[$_k];


						$_rs = clearQuestionPharse($_p);

						if ($_rs['stt'] == TRUE) {
							$_cnt++;
							
							for ($_k = $_i + 1; $_k < $_j; $_k++)
								$_beginSet[] = $_words[$_k];

							$_lim = ($_rs['pos'] == -1 ? count($_p) : $_rs['pos']);

							for ($_k = 0; $_k < $_lim; $_k++)
								$_endSet[] = $_p[$_k];
						
						}
					}
		
		if ($_cnt > 1) {
			$_result['stt'] = FALSE;
			return $_result;
		}

		if ($_cnt == 1) {
			$_result['stt'] = TRUE;
			$_result['startPoint'] = changeBegin($_beginSet);
			$_result['endPoint'] = $_endSet;
			return $_result;
		}
		
		////////////////////////////////////
		for ($_i = 0; $_i < $_n; $_i++) 
			if (isClose($_words[$_i])) {
				// Open pair
				if ($_i + 1 < $_n && isOpenPair($_words[$_i], $_words[$_i + 1])) $_i++;
				// special case
				$_pp = array();
				for ($_k = $_i; $_k < $_n; $_k++) $_pp[] = $_words[$_k];
				if (isQuestionPharse($_pp)) {
					continue;
				}
				// continue work
				$_p = array();

				for ($_k = $_i + 1; $_k < $_n; $_k++) $_p[] = $_words[$_k];
				
				$_rs = clearQuestionPharse($_p);
				if ($_rs['stt'] == TRUE) {
					$_cnt++;
					$_lim = ($_rs['pos'] == -1 ? count($_p) : $_rs['pos']);
					for ($_k = 0; $_k < $_lim; $_k++)
						$_endSet[] = $_p[$_k];
					
				}
			}

		if ($_cnt != 1) {
			$_result['stt'] = FALSE;
			return $_result;
		}

		$_result['stt'] = TRUE;
		$_result['startPoint'] = changeBegin($_beginSet);
		$_result['endPoint'] = $_endSet;
		return $_result;
	}

	function makeString($_txt) {
		$_ret = '';
		foreach ($_txt as $_val) {
			$_ret = $_ret.$_val." ";
		}
		return $_ret;
	}


	// find matching position
	function findPost2($_txt, $_listKey) {
		$_n = count($_txt);
		$_m = count($_listKey);
		$_cnt = 0;
		$_pos = -1;
		for ($_i = 0; $_i < $_n - $_m + 1; $_i++) {
			$_check = TRUE;
			for ($_j = 0; $_j < $_m; $_j++)
				if ($_listKey[$_j] != $_txt[$_i + $_j]) {
					$_check = FALSE;
				}

			if ($_check) {
				$_cnt++;
				$_pos = $_i;
				if ($_i == $_n - $_m) $_theEnd = TRUE;
			}
		}
		$_rs = array();
		$_rs['cnt'] = $_cnt;
		$_rs['pos'] = $_pos;
		return $_rs;
	}


	// ALL FOR TYPE 2
	$_busForm = array(0 => "xe số", "xe buýt số", "buýt số", "trạm số", "trạm xe buýt số", "trạm có xe số", "trạm buýt số", "trạm có xe buýt số", "trạm xe số", "trạm có buýt số",  
		"xe", "xe buýt", "buýt", "trạm", "trạm xe buýt", "trạm có xe", "trạm buýt", "trạm có xe buýt", "trạm xe", "trạm có buýt", "chạm số", "chạm xe buýt số", "chạm có xe số", "chạm buýt số", "chạm có xe buýt số", "chạm xe số", "chạm có buýt số", "chạm", "chạm xe buýt", "chạm có xe", "chạm buýt", "chạm có xe buýt", "chạm xe", "chạm có buýt"
		);

	$_range = array(0 => "gần đây nhất", "gần đây", "gần nhất", "ngang qua đây", "đi qua đây", "ngang đây", "qua đây", "xung quanh", "xung quanh đây");
	$_quanNum = array(0 => 1, 2, 1, 2, 2, 2, 2, 2, 2);

	function isDoubleBus($_sub1, $_sub2) {
		return ($_sub1 == "xe" && $_sub2 == "buýt");
	}

	// check bus num
	function isBusNum($_bus) {
		//echo "check bus => ".$_bus."<br>";
		$_check = TRUE;
		$_n = strlen($_bus);
		for ($_i = 0; $_i < $_n; $_i++)
			if ($_bus[$_i] < '0' || $_bus[$_i] > '9') $_check = FALSE;
		return $_check;
	}

	function type2($_command) {

		// station
		GLOBAL $_busForm;
		GLOBAL $_range;
		GLOBAL $_quanNum;

		$_found = TRUE;
		$_fail = FALSE;
		$_busnum = -1;
		$_numRS = 0;
		$_bgEvt = -1;
		$_enEvt = -1;
		// quantity
		$_quanOne = 3;
		$_quanTwo = 3;

		foreach ($_busForm as $_form) {
			$_listForm = explode(" ", $_form);
			$_rs = findPost2($_command, $_listForm);

			if ($_rs['cnt'] > 0) {
				
				if ($_rs['cnt'] > 1) {
					$_fail = TRUE;
					break;
				}

				$_numRS++;

				$_pos = $_rs['pos'] + count($_listForm);
				if ($_pos < count($_command) && isBusNum($_command[$_pos])) {
					$_busnum = intval($_command[$_pos]);
					$_bgEvt = $_rs['pos'];
					$_enEvt = $_rs['pos'] + count($_listForm);
					
					$_tail = array();
					for ($_i = 0; $_i < $_bgEvt; $_i++) $_tail[] = $_command[$_i];
					for ($_i = $_enEvt + 1; $_i < count($_command); $_i++) $_tail[] = $_command[$_i];
					// remain command
			
					for ($_i = 0; $_i < count($_range); $_i ++) {

						$_rs = findPost2($_tail, explode(" ", $_range[$_i]));
						
						if ($_rs['cnt'] > 0) {
							$_quanOne = min($_quanOne, $_quanNum[$_i]);
						}
					}
					// many - many
					for ($_i = 0; $_i < count($_tail); $_i++) 
						if (isQuantityMore($_tail[$_i])) {
							$_quanTwo = 2;
						}
				}
				break;
			}
		}
		
		if ($_quanTwo == 2 && $_quanOne == 1) $_fail = TRUE;
		if ($_busnum == -1) $_fail = TRUE;
		
		//echo "testing = ".$_numRS." ".$_busnum."<br>";

		if ($_numRS > 1) $_fail = TRUE;

		if ($_fail) {
			$_rrs = array();
			$_rrs['stt'] = 0;
			return $_rrs; 
		}

		$_rrs['stt'] = TRUE;
		$_rrs['busnum'] = $_busnum;

		if ($_quanOne == 1) {
			$_rrs['quan'] = 1;
		}
		else {
			$_rrs['quan'] = 2;
		}
		return $_rrs;
	}

	// ALL FOR TYPE 1
	$_busForm2 = array(0 => "xe", "xe buýt", "buýt", "trạm", "trạm xe buýt", "trạm có xe", "trạm buýt", "trạm có xe buýt", "trạm xe", "trạm có buýt", "chạm", "chạm xe buýt", "chạm có xe", "chạm buýt", "chạm có xe buýt", "chạm xe", "chạm có buýt"
	);

	function type1($_command) {
		// station
		GLOBAL $_busForm2;
		GLOBAL $_range;
		GLOBAL $_quanNum;

		$_found = TRUE;
		$_fail = FALSE;
		$_busnum = -1;
		$_numRS = 0;
		$_bgEvt = -1;
		$_enEvt = -1;
		// quantity
		$_quanOne = 3;
		$_quanTwo = 3;

		foreach ($_busForm2 as $_form) {
			$_listForm = explode(" ", $_form);
			$_rs = findPost2($_command, $_listForm);

			if ($_rs['cnt'] > 0) {
				
				if ($_rs['cnt'] > 1) {
					$_fail = TRUE;
					break;
				}

				$_numRS++;
				{
					$_bgEvt = $_rs['pos'];
					$_enEvt = $_rs['pos'] + count($_listForm) - 1;
					
					$_tail = array();
					for ($_i = 0; $_i < $_bgEvt; $_i++) $_tail[] = $_command[$_i];
					for ($_i = $_enEvt + 1; $_i < count($_command); $_i++) $_tail[] = $_command[$_i];
					// remain command
			
					for ($_i = 0; $_i < count($_range); $_i ++) {

						$_rs = findPost2($_tail, explode(" ", $_range[$_i]));
						
						if ($_rs['cnt'] > 0) {
							$_quanOne = min($_quanOne, $_quanNum[$_i]);
						}
					}
					// many - many
					for ($_i = 0; $_i < count($_tail); $_i++) 
						if (isQuantityMore($_tail[$_i])) {
							$_quanTwo = 2;
						}
				}
				break;
			}
		}
		
		if ($_quanTwo == 2 && $_quanOne == 1) $_fail = TRUE;
		if ($_numRS > 1) $_fail = TRUE;

		if ($_fail) {
			$_rrs = array();
			$_rrs['stt'] = 0;
			return $_rrs; 
		}

		$_rrs['stt'] = TRUE;

		if ($_quanOne == 1) {
			$_rrs['quan'] = 1;
		}
		else {
			$_rrs['quan'] = 2;
		}
		return $_rrs;
	}



	// MAIN PROCESSING //////////////////////////////////////////////////////////////////////
	function textAnalysis($_text) {

		
		// type 3
		$_rs = type3($_text);
		if ($_rs['stt'] == TRUE) {
			if (count($_rs['startPoint']) == 0) {
				$_json['mess'] = 'Tìm trạm từ vị trí hiện tại đến một điểm'; 
				$_json['type'] = 6;
				$_json['end'] = makeString($_rs['endPoint']);
			}
			else {
				$_json['type'] = 5;
				$_json['mess'] = 'Tìm trạm giữa hai điểm';
				$_json['begin'] = makeString($_rs['startPoint']);
				$_json['end'] = makeString($_rs['endPoint']);
			}
			return $_json;
		}

		// testing.............
		/*$_json = array();
		$_json['type'] = 7;
		$_json['mess'] = 'Xin lỗi, hệ thống không thể hiểu yêu cầu của bạn';
		return $_json; */
		// end testing.........

		// TYPE 2
		$_command = explode(" ", $_text);
		$_n = count($_command);
		$_rs = type2($_command);
		if ($_rs['stt'] == TRUE) {
			$_json = array();
			if ($_rs['quan'] == 1) {
				$_json['type'] = 3;
			}
			else {
				$_json['type'] = 4;
			}
			$_json['busnum'] = $_rs['busnum'];
			$_json['mess'] = "Tìm trạm có xe";
			// insert information
			return $_json;
		}
		/**/
		// TYPE 1
		$_rs = type1($_command);
		if ($_rs['stt'] == TRUE) {
			$_json = array();
			// insert information
			if ($_rs['quan'] == 1) {
				$_json['type'] = 1;
			}
			else {
				$_json['type'] = 2;
			}
			$_json['mess'] = "Tìm trạm";
			return $_json;
		}
		// UNDEFINED
		$_json = array();
		$_json['type'] = 7;
		$_json['mess'] = 'Xin lỗi, hệ thống không thể hiểu yêu cầu của bạn';
		return $_json;	/**/
	}

	//print_r(textAnalysis("tui muốn đi tới chợ bến thành"));
	//print_r(textAnalysis("xe nào tới chợ bến thành"));
	//print_r(textAnalysis("tui muốn đến đường nguyễn thiện thuật quận 7"));	
	// => chợ gần nhất

?>
