<?php
	require_once 'include/ST_db_functions.php';
	$db = new ST_db_functions();
	
	if (isset($_POST['userName']) && isset($_POST['routeId']) && isset($_POST['date']) && isset($_POST['time'])) 
	{
		$s1 = $_POST['userName']; 
		$s2 = $_POST['routeId'];
		$s3 = $_POST['date'];
		$s4 = $_POST['time'];
	}
	if (isset($_POST['t1']) && isset($_POST['t2']) && isset($_POST['t3']) && isset($_POST['t4'])) 
	{
		$s5 = $_POST['t1']; 
		$s6 = $_POST['t2'];
		$s7 = $_POST['t3'];
		$s8 = $_POST['t4'];
		$db->addUser($s1,$s2,$s3,$s4,$s5,$s6,$s7,$s8);
	}
	else if (isset($_POST['t1']) && isset($_POST['t2']) && isset($_POST['t3'])) 
	{
		$s5 = $_POST['t1']; 
		$s6 = $_POST['t2'];
		$s7 = $_POST['t3'];
		$db->addUser($s1,$s2,$s3,$s4,$s5,$s6,$s7);
	}
	else if (isset($_POST['t1']) && isset($_POST['t2'])) 
	{
		$s5 = $_POST['t1']; 
		$s6 = $_POST['t2']; 
		$db->addUser($s1,$s2,$s3,$s4,$s5,$s6);
	}
	elseif (isset($_POST['t1'])) 
	{
		$s5 = $_POST['t1']; 
		$db->addUser($s1,$s2,$s3,$s4,$s5);
	} 
?>