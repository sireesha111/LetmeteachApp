<?php 
error_reporting(0);
if($_SERVER['REQUEST_METHOD']=='POST'){
	
	$teacher_username = $_POST['teacher_username'];
	$teacher_password = $_POST['teacher_password'];
	
	require_once('dbConnect.php');
	
	$sql = "SELECT * FROM tutors WHERE (t_activateemail='$teacher_username' OR t_username='$teacher_username') AND t_password='$teacher_password' AND t_status=1";

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