<?php

error_reporting(0);
$response = array();
require_once('dbConnect.php');

if (isset($_GET["zoneid"])) {
    $zoneid = $_GET['zoneid'];
	

    $result = mysqli_query($con, "SELECT * FROM `subarea` WHERE zone_id = '$zoneid'");
    
        if (mysqli_num_rows($result) > 0) {
			$response["subarealist"] = array();
			

            while($row = mysqli_fetch_array($result)){
				$list = array();
				
				$list["area_id"] = $row["area_id"];
				$list["area_name"] = $row["area_name"];
				$list["city_id"] = $row["city_id"];
				$list["zone_id"] = $row["zone_id"];
				
				
				array_push($response["subarealist"], $list);
			}
			$response["success"] = 1;

            echo json_encode($response);
        } else {
           
            $response["success"] = 0;
            $response["message"] = "No Records Found";

          
            echo json_encode($response);
        }
    
} else {
  
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

  
    echo json_encode($response);
}
?>