<?php

error_reporting(0);
$response = array();


if (isset($_GET["s_username"])) {
    $s_username = $_GET['s_username'];
	require_once('dbConnect.php');


    $result = mysqli_query($con, "SELECT *, (SELECT city_name FROM city as c WHERE c.city_id = st.st_city) as cityname, (SELECT zone_name FROM zone as z WHERE z.zone_id = st.st_zone) as zonename, (SELECT area_name FROM subarea as s WHERE s.area_id = st.st_subarea) as subareaname FROM students as st WHERE st_username='$s_username'");
   
        if (mysqli_num_rows($result) > 0) {
			$response["getuser"] = array();
			

            while($row = mysqli_fetch_array($result)){
				$list = array();
				
				$list["st_id"] = $row["st_id"];
				$list["st_code"] = $row["st_code"];
				$list["st_username"] = $row["st_username"];
				$list["st_activateemail"] = $row["st_activateemail"];
				$list["st_password"] = $row["st_password"];
				
				$list["st_name"] = $row["st_name"];
				$list["st_dob"] = $row["st_dob"];
				$list["st_gander"] = $row["st_gander"];
				$list["st_address"] = $row["st_address"];
				$list["st_landmark"] = $row["st_landmark"];
				$list["st_city"] = $row["cityname"];
				
				$list["st_zone"] = $row["zonename"];
				$list["st_subarea"] = $row["subareaname"];
				$list["st_typetutor"] = $row["st_typetutor"];
				$list["st_class"] = $row["st_class"];
				$list["st_subject"] = $row["st_subject"];
				
				$list["st_medium"] = $row["st_medium"];
				$list["st_board"] = $row["st_board"];
				$list["st_other"] = $row["st_other"];
				$list["st_mobieno"] = $row["st_mobieno"];
				$list["st_landline"] = $row["st_landline"];
				$list["st_otherno"] = $row["st_otherno"];
				$list["st_fromtime"] = $row["st_fromtime"];
				$list["st_totime"] = $row["st_totime"];
				
				$list["st_photo"] = $row["st_photo"];
				$list["st_status"] = $row["st_status"];
				$list["st_fees_hourly"] = $row["st_fees_hourly"];
				$list["st_fees_monthly"] = $row["st_fees_monthly"];
				
				
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