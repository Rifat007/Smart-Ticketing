<?php
class ST_db_functions {
	
	
	public function getTransports($s1,$s2)
	{
		$link = mysqli_connect("localhost", "root", "123321", "ticket_db");
		$query = "SELECT id, bus_name, rating, fare from bus_info where start = '$s1' and dest = '$s2'";
		$result = mysqli_query($link, $query);
		$return_array = array();
		while ($row = mysqli_fetch_array($result, MYSQLI_ASSOC))
		{
			$row_array['id'] = $row['id'];
			$row_array['bus_name'] = $row['bus_name']; 
			$row_array['rating']=$row['rating'];
			$row_array['fare']=$row['fare'];
			array_push($return_array, $row_array);
		}
		echo json_encode($return_array);
		$link->close();	
	 
	}
	
	public function getCheckedSeats($s1,$s2,$s3)
	{
		$link = mysqli_connect("localhost", "root", "123321", "ticket_db");
		$query = "select seat_no from seat_table where userid in
			(select userid from user_table where id='$s1' and journey_date ='$s2' and journey_time='$s3')";
			
		$result = mysqli_query($link, $query);
		$return_array = array();
		while ($row = mysqli_fetch_array($result, MYSQLI_ASSOC))
		{
			$row_array['seat_no'] = $row['seat_no']; 
			array_push($return_array, $row_array);
		}
		echo json_encode($return_array);
		$link->close();	
	 
	}
	
	public function addUser($s1,$s2,$s3,$s4, ...$t)
	{
		$link = mysqli_connect("localhost","root","123321","ticket_db");
		$sql = "insert into user_table (user_name,id,journey_date,journey_time) values ('$s1','$s2','$s3','$s4')"; 
		if (mysqli_query($link, $sql)) {
			$last_id = mysqli_insert_id($link);
			echo "New record created successfully with id ".$last_id;
		} else {
			echo "Error: " . $sql . "<br>" . mysqli_error($link);
		}
		foreach ($t as $val) {
			$sql1 = "insert into seat_table(userid,seat_no) values ('$last_id','$val')";
			if (mysqli_query($link, $sql1)) {  
			} else {
				echo "Error: " . $sql1 . "<br>" . mysqli_error($link);
			}
			
			$sql2 = "insert into checked_seat(id,journey_date,journey_time,seat_no) values ('$s2','$s3','$s4','$val')";
			if (mysqli_query($link, $sql2)) {  
			} else {
				echo "Error: " . $sql2 . "<br>" . mysqli_error($link);
			}
		}
		$link->close();
	}
}
?> 