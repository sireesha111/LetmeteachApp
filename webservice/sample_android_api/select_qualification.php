<?php
error_reporting(0);

$response = array();

require_once('dbConnect.php');

$result = mysqli_query($con,"SELECT * FROM qualification") or die(mysql_error());


if (mysqli_num_rows($result) > 0) {
   
    $response["qualilist"] = array();
    
    while ($row = mysqli_fetch_array($result)) {
   
        $list = array();
        $list["quali_id"] = $row["quali_id"];
        $list["quali_name"] = $row["quali_name"];

        array_push($response["qualilist"], $list);
    }
	
    $response["success"] = 1;

    echo json_encode($response);
} else {
  
    $response["success"] = 0;
    $response["message"] = "No Records Found";

    echo json_encode($response);
}
?>
