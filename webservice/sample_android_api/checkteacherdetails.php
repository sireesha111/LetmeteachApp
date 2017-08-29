<?php

error_reporting(0);
$response = array();


if (isset($_GET["user"])) {
	
	$user = $_GET['user'];
	require_once('dbConnect.php');

    $result = mysqli_query($con, "SELECT * FROM `tutors` where t_username = '$user' and t_name!='' and t_dob!='' and t_address!=''");
   
    $check = mysqli_fetch_array($result);

	if(isset($check)){
		
		$response["success"] = 1;
		$response["message"] = "View Details";
		echo json_encode($response);
		
	}else{
		
		$response["success"] = 0;
		$response["message"] = "Added Details";
		echo json_encode($response);
		
	}
    
} else {
  
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    echo json_encode($response);
}
?>