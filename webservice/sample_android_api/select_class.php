<?php
error_reporting(0);

$response = array();

require_once('dbConnect.php');

$result = mysqli_query($con,"SELECT * FROM class") or die(mysql_error());


if (mysqli_num_rows($result) > 0) {
   
    $response["classlist"] = array();
    
    while ($row = mysqli_fetch_array($result)) {
   
        $list = array();
        $list["class_id"] = $row["class_id"];
        $list["class_name"] = $row["class_name"];

        array_push($response["classlist"], $list);
    }
	
    $response["success"] = 1;

    echo json_encode($response);
} else {
  
    $response["success"] = 0;
    $response["message"] = "No Records Found";

    echo json_encode($response);
}
?>
