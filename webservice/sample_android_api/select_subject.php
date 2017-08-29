<?php
error_reporting(0);

$response = array();

require_once('dbConnect.php');

$result = mysqli_query($con,"SELECT * FROM subject") or die(mysql_error());


if (mysqli_num_rows($result) > 0) {
   
    $response["subjectlist"] = array();
    
    while ($row = mysqli_fetch_array($result)) {
   
        $list = array();
        $list["subj_id"] = $row["subj_id"];
        $list["subj_name"] = $row["subj_name"];

        array_push($response["subjectlist"], $list);
    }
	
    $response["success"] = 1;

    echo json_encode($response);
} else {
  
    $response["success"] = 0;
    $response["message"] = "No Records Found";

    echo json_encode($response);
}
?>
