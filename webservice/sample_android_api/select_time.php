<?php
error_reporting(0);

$response = array();

require_once('dbConnect.php');

$result = mysqli_query($con,"SELECT * FROM time") or die(mysql_error());


if (mysqli_num_rows($result) > 0) {
   
    $response["timelist"] = array();
    
    while ($row = mysqli_fetch_array($result)) {
   
        $list = array();
        $list["time_id"] = $row["time_id"];
        $list["from_time"] = $row["from_time"];
		$list["to_time"] = $row["to_time"];

        array_push($response["timelist"], $list);
    }
	
    $response["success"] = 1;

    echo json_encode($response);
} else {
  
    $response["success"] = 0;
    $response["message"] = "No Records Found";

    echo json_encode($response);
}
?>
