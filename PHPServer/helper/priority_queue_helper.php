<?php 
	class PTest extends SplPriorityQueue {
		public function compare($obj1, $obj2) {
			return $obj1 - $obj2;
		}
	}

	class myObj {
		public $value;
		public $label;

		function __construct($label, $value) {
			$this->value = $value;
			$this->label = $label;
		}
	}


	$obj = new PTest();

	// obj
	$_RR = new myObj("Le Hung Son", 9);
	print_r($_RR);
	echo $_RR->value."<br>".$_RR->label."<br>";

	// 
	$obj-> insert(new myObj("Le Hung Son", 9), 1);
	$obj-> insert(new myObj("ABC", 10), 3);
	$obj-> insert(new myObj("ge ge", 1), 2);

	echo "COUNT->".$obj->count()."<BR>";

	//mode of extraction
	$obj->setExtractFlags(PTest::EXTR_BOTH);

	//Go to TOP
	$obj->top(); 

	while($obj->valid()){
    	print_r($obj->current());
    	echo "<BR>";
    	$obj->next();
	}

?>