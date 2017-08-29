<?php

error_reporting(0);
$response = array();


if ($_SERVER['REQUEST_METHOD']=='POST') {
	
    $st_username = $_POST['st_username'];
	$st_name = $_POST['st_name'];
	$st_dob = $_POST['st_dob'];
	$st_gander = $_POST['st_gander'];
	$st_address = $_POST['st_address'];
	$st_landmark = $_POST['st_landmark'];
	$st_city = $_POST['st_city'];
	$st_zone = $_POST['st_zone'];
	$st_subarea = $_POST['st_subarea'];
	$st_typetutor = $_POST['st_typetutor'];
	$st_class = $_POST['st_class'];
	$st_subject = $_POST['st_subject'];
	
	$st_medium = $_POST['st_medium'];
	$st_board = $_POST['st_board'];
	$st_other = $_POST['st_other'];
	
	$st_contactno = $_POST['st_contactno'];
	$st_landline = $_POST['st_landline'];
	$st_otherno = $_POST['st_otherno'];
	
	$st_fromtime = $_POST['st_fromtime'];
	$st_totime = $_POST['st_totime'];
	
	$st_fees_hourly = $_POST['st_fees_hourly'];
	$st_fees_monthly = $_POST['st_fees_monthly'];
	$st_photo = $_POST['st_photo'];
	
	
	require_once('dbConnect.php');
	
    $query = mysqli_query($con,"SELECT * FROM `students` WHERE st_username= '$st_username' ORDER BY st_id ASC");
    $num = mysqli_num_rows($query);
    if($num == 1){
		
		$st_imgcode = "IMGS".rand(11111, 999999);
		
		$path = "uploads/$st_imgcode.png";
 
		$actualpath = "$path";
		 
		
		$result = mysqli_query($con,"UPDATE `students` SET `st_name`='$st_name',`st_dob`='$st_dob',`st_gander`='$st_gander',`st_address`='$st_address',`st_landmark`='$st_landmark',`st_city`='$st_city',`st_zone`='$st_zone',`st_subarea`='$st_subarea',`st_typetutor`='$st_typetutor',`st_class`='$st_class',`st_subject`='$st_subject',`st_medium`='$st_medium',`st_board`='$st_board',`st_other`='$st_other',`st_mobieno`='$st_contactno',`st_landline`='$st_landline',`st_otherno`='$st_otherno',`st_fromtime`='$st_fromtime',`st_totime`='$st_totime',`st_fees_hourly`='$st_fees_hourly',`st_fees_monthly`='$st_fees_monthly',`st_photo`='$actualpath' WHERE `st_username`='$st_username'");
    
		if ($result) {
			
			file_put_contents($path,base64_decode($st_photo));
		 
			$response["success"] = 1;
			$response["message"] = "Updated Successfully";

		 
			echo json_encode($response);
		} else {
		
			$response["success"] = 0;
			$response["message"] = "No Updated";
			
		 
			echo json_encode($response);
		}
    
   
	}
	else{
	$response["success"] = 0;
	$response["message"] = "no username".$t_username;
			
	echo json_encode($response);
}
 
}else {
  
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    echo json_encode($response);
}
?>