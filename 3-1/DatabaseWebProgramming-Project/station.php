<?php

$query = "SELECT * FROM station";

$query_result = mysqli_query($conn, $query);

$result = array();

while($row = mysqli_fetch_array($query_result)){
	array_push($result, array('lat'=>$row[1],'lng'=>$row[2]));
}

#$query_result->fetch_array();

echo json_encode($result);


?>
