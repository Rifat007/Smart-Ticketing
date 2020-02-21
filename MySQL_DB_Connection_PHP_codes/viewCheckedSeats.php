<?php
	require_once 'include/ST_db_functions.php';
	$db = new ST_db_functions();
	if (isset($_POST['id']) && isset($_POST['journeyDate']) && isset($_POST['journeyTime']))
	{
		$s1 = $_POST['id'];
		$s2 = $_POST['journeyDate'];
		$s3 = $_POST['journeyTime'];
		$db->getCheckedSeats($s1,$s2,$s3);
	} 
?>