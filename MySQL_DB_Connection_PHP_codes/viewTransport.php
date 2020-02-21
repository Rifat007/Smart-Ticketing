<?php
	require_once 'include/ST_db_functions.php';
	$db = new ST_db_functions();
	if (isset($_GET['start']) && isset($_GET['dest']))
	{
		$s1 = $_GET['start'];
		$s2 = $_GET['dest'];
		$db->getTransports($s1, $s2);
	}
?>