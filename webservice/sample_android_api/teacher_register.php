<?php
error_reporting(0);
$response = array();

if($_SERVER['REQUEST_METHOD']=='POST'){
	
	
	$username= $_POST['username'];
	$email = $_POST['email'];
	$password= $_POST['password'];
		
	require_once('dbConnect.php');

	$check = mysqli_fetch_array(mysqli_query($con,"SELECT * FROM tutors WHERE t_username='$username' OR t_activateemail='$email'"));
 
	if(isset($check)){
			
		$response["success"] = 0;
		$response["message"] = "Username or Email already exist";
		 
		echo json_encode($response);
	}else{ 
		
		$st_codeno = "LMTT".date("ymdHis");;
	
		$sql = "INSERT INTO `tutors`(`t_code`, `t_username`, `t_activateemail`, `t_password`, `t_status`) VALUES ('$st_codeno', '$username', '$email', '$password', '1')";
		
		if(mysqli_query($con,$sql)){
			
			$response["success"] = 1;
			$response["message"] = "Successfully Registered";

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