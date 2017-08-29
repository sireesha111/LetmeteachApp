<?php

error_reporting(0);
$response = array();
require_once('dbConnect.php');

if (isset($_GET["cityid"])) {
    $cityid = $_GET['cityid'];
	

    $result = mysqli_query($con, "SELECT * FROM `zone` WHERE city_id = '$cityid'");
    
        if (mysqli_num_rows($result) > 0) {
			$response["zonelist"] = array();
			

            while($row = mysqli_fetch_array($result)){
				$list = array();
				
				$list["zone_id"] = $row["zone_id"];
				$list["zone_name"] = $row["zone_name"];
				$list["city_id"] = $row["city_id"];
				
				
				array_push($response["zonelist"], $list);
			}
			$response["success"] = 1;

            echo json_encode($response);
        } else {
           
            $response["success"] = 0;
            $response["message"] = "No Record Found";

          
            echo json_encode($response);
        }
    
} else {
  
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

  
    echo json_encode($response);
}
?>