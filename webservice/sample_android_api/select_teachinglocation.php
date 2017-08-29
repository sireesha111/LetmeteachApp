<?php
error_reporting(0);

$response = array();

require_once('dbConnect.php');

$result = mysqli_query($con,"SELECT * FROM teach_location") or die(mysql_error());


if (mysqli_num_rows($result) > 0) {
   
    $response["teachlist"] = array();
    
    while ($row = mysqli_fetch_array($result)) {
   
        $list = array();
        $list["teach_id"] = $row["teach_id"];
        $list["teach_name"] = $row["teach_name"];

        array_push($response["teachlist"], $list);
    }
	
    $response["success"] = 1;

    echo json_encode($response);
} else {
  
    $response["success"] = 0;
    $response["message"] = "No Records Found";

    echo json_encode($response);
}
?>
