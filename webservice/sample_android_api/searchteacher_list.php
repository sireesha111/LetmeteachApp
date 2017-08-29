<?php

error_reporting(0);

$response = array();

$city = $_GET['city'];
$zone = $_GET['zone'];
$class = $_GET['class'];
$subject = $_GET['subject'];
$quali = $_GET['quali'];

require_once('dbConnect.php');

//SELECT * FROM `tutors` where t_city='4' and t_zone='4' and t_class like '%B.A. I (H)%' and t_subject like '%Accounts%' and t_qualification like '%BCOM%' ORDER BY `t_id` ASC

$result = mysqli_query($con,"SELECT * FROM `tutors` where t_city='$city' and t_zone='$zone' and t_class like '%$class%' and t_subject like '%$subject%' and t_qualification like '%$quali%' ORDER BY `t_id` ASC") or die(mysql_error());

if (mysqli_num_rows($result) > 0) {
   
   $response["search"] = array();
    
    while ($row = mysqli_fetch_array($result)) {
		
   
        $search = array();
		$search['st_id'] = $row['t_id'];
		$search['st_code'] = $row['t_code'];
		$search['st_photo'] = $row['t_photo'];
		$search['st_name'] = $row['t_name'];
		$search['st_gander'] = $row['t_gander'];
		$search['st_dob'] = $row['t_dob'];
		$dateOfBirth = $row['t_dob'];
		
		$oDateNow = new DateTime();
		$oDateBirth = new DateTime($dateOfBirth);

		// New interval
		$oDateIntervall = $oDateNow->diff($oDateBirth);
		$search['st_age'] = $oDateIntervall->y;

	
		
        array_push($response["search"], $search);
    }
	
    $response["success"] = 1;

    echo json_encode($response);
} else {
  
    $response["success"] = 0;
    $response["message"] = "No Records found";

    echo json_encode($response);
}
?>
