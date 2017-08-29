<?php

error_reporting(0);
$response = array();


if ($_SERVER['REQUEST_METHOD']=='POST') {
	
    $t_username = $_POST['t_username'];
	$t_name = $_POST['t_name'];
	$t_dob = $_POST['t_dob'];
	$t_gander = $_POST['t_gander'];
	$t_address = $_POST['t_address'];
	$t_landmark = $_POST['t_landmark'];
	$t_city = $_POST['t_city'];
	$t_zone = $_POST['t_zone'];
	$t_subarea = $_POST['t_subarea'];
	$t_teaching = $_POST['t_teaching'];
	$t_class = $_POST['t_class'];
	$t_teachlocation = $_POST['t_teachlocation'];
	$t_subject = $_POST['t_subject'];
	$t_contactno = $_POST['t_contactno'];
	
	
	$t_fromtime = $_POST['t_fromtime'];
	$t_totime = $_POST['t_totime'];
	$t_experience = $_POST['t_experience'];
	$t_qualification = $_POST['t_qualification'];
	
	$t_fees_hourly = $_POST['t_fees_hourly'];
	$t_fees_monthly = $_POST['t_fees_monthly'];
	$t_photo = $_POST['t_photo'];
	
	
	require_once('dbConnect.php');
	
    $query = mysqli_query($con,"SELECT * FROM `tutors` WHERE t_username= '$t_username' ORDER BY t_id ASC");
    $num = mysqli_num_rows($query);
    if($num == 1){
		
		$st_imgcode = "IMGT".rand(11111, 999999);
		
		$path = "uploads/$st_imgcode.png";
 
		$actualpath = "$path";
		 
		
		$result = mysqli_query($con,"UPDATE `tutors` SET `t_name`='$t_name', `t_dob`='$t_dob', `t_gander`='$t_gander', `t_address`='$t_address', `t_landmark`='$t_landmark', `t_city`='$t_city', `t_zone`='$t_zone', `t_subarea`='$t_subarea', `t_teaching`='$t_teaching', `t_class`='$t_class', `t_teachlocation`='$t_teachlocation', `t_subject`='$t_subject', `t_contactno`='$t_contactno', `t_fromtime`='$t_fromtime', `t_totime`='$t_totime',`t_experience`='$t_experience', `t_qualification`='$t_qualification', `t_fees_hourly`='$t_fees_hourly', `t_fees_monthly`='$t_fees_monthly', `t_photo`='$actualpath' WHERE `t_username`='$t_username'");
    
		if ($result) {
			
			file_put_contents($path,base64_decode($t_photo));
		 
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