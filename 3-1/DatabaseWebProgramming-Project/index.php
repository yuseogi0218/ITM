<?php 
include './db_info.php'; 
$uri = parse_url($_SERVER['REQUEST_URI'], PHP_URL_PATH); 
$uri = explode('/', $uri); 

$src = $_GET['request'];
$requestMethod = $_SERVER["REQUEST_METHOD"];

if ($src == 'info'){
	switch ($requestMethod){
		case 'GET':
			include 'mapinfo.php';
			break;
		default:
			break;
	}	
}
else if($src == 'station'){
	switch ($requestMethod){
		case 'GET':
			include 'station.php';
			break;
		default:
			break;
	}
}else{
	exit();
}

?>

