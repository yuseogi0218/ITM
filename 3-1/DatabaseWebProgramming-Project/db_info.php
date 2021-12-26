<?php 
$conn = mysqli_connect(localhost,'test','1234','test'); 
header('Content-Type: application/json; charset=UTF-8'); 
header("HTTP/1.1 200 OK"); 
header("Access-Control-Allow-Methods: OPTIONS,GET,POST,PUT,DELETE"); 

$time = date("Y-m-d H시i분"); 
?>

