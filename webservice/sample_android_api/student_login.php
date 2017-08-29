<?php 
error_reporting(0);
if($_SERVER['REQUEST_METHOD']=='POST'){
	
	$student_username = $_POST['student_username'];
	$student_password = $_POST['student_password'];
	require_once('dbConnect.php');
	
	$sql = "SELECT * FROM students WHERE (st_username='$student_username' OR st_activateemail='$student_username') AND st_password='$student_password' AND st_status='1'";

	$result = mysqli_query($con,$sql);

	$check = mysqli_fetch_array($result);

	if(isset($check)){
		echo "success";
	}else{
		echo "failure";
	}
	
	mysqli_close($con);
}
 
 ?>