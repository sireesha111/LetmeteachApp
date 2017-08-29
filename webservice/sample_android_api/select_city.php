<?php
error_reporting(0);

$response = array();

require_once('dbConnect.php');

$result = mysqli_query($con,"SELECT * FROM city") or die(mysql_error());


if (mysqli_num_rows($result) > 0) {
   
    $response["citylist"] = array();
    
    while ($row = mysqli_fetch_array($result)) {
   
        $list = array();
        $list["city_id"] = $row["city_id"];
        $list["city_name"] = $row["city_name"];

        array_push($response["citylist"], $list);
    }
	
    $response["success"] = 1;

    echo json_encode($response);
} else {
  
    $response["success"] = 0;
    $response["message"] = "No Records Found";

    echo json_encode($response);
}
?>
