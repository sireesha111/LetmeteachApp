<?php

$response = array();

$email = $_GET['email'];

require_once('dbConnect.php');

$result = mysqli_query($con,"SELECT * FROM `students` where st_activateemail='$email'") or die(mysql_error());

if ($result) 
{
	
	//$current_id = mysqli_insert_id($con);
			
	$actual_link = "http://192.168.0.106/sample_android_api/student_activate.php?email=".$email;
	$toEmail = $email;
	$subject = "User Registration Activation Email";
	
	$htmlStr = "<html><body>";
	$htmlStr .= "Hi " . $email . ",<br /><br />";

	$htmlStr .= "Please click the button below activate your account.<br /><br /><br />";
	$htmlStr .= "<a href='" . $actual_link . "' target='_blank' style='padding:1em; font-weight:bold; background-color:blue; color:#fff;'>VERIFY EMAIL</a><br /><br /><br />";

	$htmlStr .= "Kind regards,<br />";
	$htmlStr .= "<a href='http://varnalabs.com/' target='_blank'>Varnalabs Teams</a><br /></body></html>";
	
	//$htmlStr .= 'MIME-Version: 1.0' . "\r\n";
	
	
	$headers[] = 'MIME-Version: 1.0';
	$headers[] = 'Content-type: text/html; charset=iso-8859-1';
	$headers[] = "From: sireesha.achugatla@gmail.com";
	
	$content = $htmlStr;
	
	
	//$content = "Click this link to activate your account. <a href='" . $actual_link . "'>" . $actual_link . "</a>";
	//$mailHeaders = "From: sireesha.achugatla@gmail.com";
	
	
	mail($toEmail, $subject, $content, implode("\r\n", $headers));
	unset($_POST);
	
	$response["success"] = 1;
	$response["message"] = "You have registered and the activation mail is sent to your email. Click the activation link to activate you account.";
	echo json_encode($response);
	
} else {

	$response["success"] = 0;
	$response["message"] = "Problem in account activation.";
	
 
	echo json_encode($response);
}
?>
