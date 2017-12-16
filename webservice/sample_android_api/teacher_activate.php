<?php

$response = array();

$email = $_GET['email'];

require_once('dbConnect.php');

$result = mysqli_query($con,"UPDATE `tutors` SET `t_status` = '1' WHERE t_activateemail='$email'") or die(mysql_error());

if ($result) {
	
	echo "Your account is activated.";
	
} else {

	echo  "Problem in account activation.";
}
?>
