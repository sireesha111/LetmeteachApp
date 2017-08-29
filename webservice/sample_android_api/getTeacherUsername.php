<?php

error_reporting(0);
$response = array();


if (isset($_GET["t_username"])) {
    $t_username = $_GET['t_username'];
	require_once('dbConnect.php');


    $result = mysqli_query($con, "SELECT *, (SELECT city_name FROM city as c WHERE c.city_id = t.t_city) as cityname, (SELECT zone_name FROM zone as z WHERE z.zone_id = t.t_zone) as zonename, (SELECT area_name FROM subarea as s WHERE s.area_id = t.t_subarea) as subareaname FROM tutors as t WHERE t_username='$t_username'");
   
        if (mysqli_num_rows($result) > 0) {
			$response["getuser"] = array();
			

            while($row = mysqli_fetch_array($result)){
				$list = array();
				
				
				$list["t_id"] = $row["t_id"];
				$list["t_code"] = $row["t_code"];
				$list["t_name"] = $row["t_name"];
				$list["t_dob"] = $row["t_dob"];
				$list["t_gander"] = $row["t_gander"];
				
				$list["t_address"] = $row["t_address"];
				$list["t_landmark"] = $row["t_landmark"];
				$list["t_city"] = $row["cityname"];
				$list["t_zone"] = $row["zonename"];
				$list["t_subarea"] = $row["subareaname"];
				
				$list["t_teaching"] = $row["t_teaching"];
				$list["t_class"] = $row["t_class"];
				$list["t_teachlocation"] = $row["t_teachlocation"];
				$list["t_subject"] = $row["t_subject"];
				$list["t_contactno"] = $row["t_contactno"];
				
				$list["t_fromtime"] = $row["t_fromtime"];
				$list["t_totime"] = $row["t_totime"];
				$list["t_experience"] = $row["t_experience"];
				$list["t_qualification"] = $row["t_qualification"];
				$list["t_fees_hourly"] = $row["t_fees_hourly"];
				
				$list["t_fees_monthly"] = $row["t_fees_monthly"];
				$list["t_photo"] = $row["t_photo"];
				$list["t_username"] = $row["t_username"];
				$list["t_activateemail"] = $row["t_activateemail"];
				$list["t_password"] = $row["t_password"];
				$list["t_status"] = $row["t_status"];
				
				
				array_push($response["getuser"], $list);
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