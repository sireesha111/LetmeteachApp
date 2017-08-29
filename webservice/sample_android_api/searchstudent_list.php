<?php

$response = array();

$city = $_GET['city'];
$zone = $_GET['zone'];
$class = $_GET['class'];
$subject = $_GET['subject'];

require_once('dbConnect.php');

$result = mysqli_query($con,"SELECT * FROM `students` where st_city='$city' and st_zone='$zone' and st_class like '%$class%' and st_subject like '%$subject%' ORDER BY `st_id` ASC") or die(mysql_error());

if (mysqli_num_rows($result) > 0) {
   
   $response["search"] = array();
    
    while ($row = mysqli_fetch_array($result)) {
		
   
        $search = array();
		$search['st_id'] = $row['st_id'];
		$search['st_code'] = $row['st_code'];
		$search['st_photo'] = $row['st_photo'];
		$search['st_name'] = $row['st_name'];
		$search['st_gander'] = $row['st_gander'];
		$search['st_dob'] = $row['st_dob'];
		$dateOfBirth = $row['st_dob'];
		
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
