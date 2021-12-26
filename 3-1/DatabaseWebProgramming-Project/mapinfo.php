<?php
$latitude = $_GET["latitude"];
$longitude = $_GET["longitude"];

try{
    $degree = (int)$_GET["degree"];
}
catch(Exception $e){
    $degree = -1;
}

if($latitude == null || $longitude == null){
    $latitude = 37.570865;
    $longitude = 126.997938;
}

if($degree == null || $degree == -1){
    $degree = 10;
}

$select_query = "SELECT * FROM section WHERE 
            lt_lat >= $latitude and
            lt_lat-0.001 <= $latitude and
            lt_lng <= $longitude and
            lt_lng+0.001 >= $longitude
";


$result_query = mysqli_query($conn, $select_query);
$result_model = $result_query->fetch_array();

$x = 0;
$y = 0;
$mySectionId=0;


if($result_model != 0){
    $x = (int)$result_model['r_x'];
    $y = (int)$result_model['r_y'];
    $mySectionId = $result_model['id'];
}else{
    echo "invalid location!!!";
    return;
}

$select_query = "SELECT aa.id as id, std_lat, std_lng, r_x, r_y, aa.cnt 
FROM section left join density aa on section.id = aa.id 
WHERE r_x > $x - $degree and
r_x < $x + $degree and
r_y > $y - $degree and
r_y < $y + $degree
and cnt>0";

$result_query = mysqli_query($conn, $select_query);
$result = array();
while($row = mysqli_fetch_array($result_query)){
	array_push($result, array('id'=>$row[0],'std_lat'=>$row[1],'std_lng'=>$row[2],'cnt'=>$row[5]));
}

echo json_encode($result);
?>
