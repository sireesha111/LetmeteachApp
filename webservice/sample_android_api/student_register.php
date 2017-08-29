<?php
error_reporting(0);
$response = array();

if($_SERVER['REQUEST_METHOD']=='POST'){
	
	
	$username= $_POST['username'];
	$email = $_POST['email'];
	$password= $_POST['password'];
		
	require_once('dbConnect.php');

	$check = mysqli_fetch_array(mysqli_query($con,"SELECT * FROM students WHERE st_username='$username' OR st_activateemail='$email'"));
 
	if(isset($check)){
			
		$response["success"] = 0;
		$response["message"] = "Username or Email already exist";
		 
		echo json_encode($response);
	}else{ 
		
		$st_codeno = "LMTS".date("ymdHis");
	
		$sql = "INSERT INTO `students`(`st_code`, `st_username`, `st_activateemail`, `st_password`, `st_status`) VALUES ('$st_codeno', '$username', '$email', '$password', '1')";
		
		if(mysqli_query($con,$sql)){
			
			$response["success"] = 1;
			$response["message"] = "Successfully registered";

			echo json_encode($response);
			
		}else{
			
			$response["success"] = 0;
			$response["message"] = "Oops! Please try again!";
	 
			echo json_encode($response);
		}
	}
} else {
	$response["success"] = 0;
	$response["message"] = "Oops! An error occurred.";
			 
	echo json_encode($response);
}