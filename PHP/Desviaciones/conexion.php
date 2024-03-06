<?php
	$serverName = "SRVINCAL\COMPAC";
	$connectionInfo = array("Database"=>"Balance_ScoreCard", "UID"=>"auxiliar_it", "PWD"=>"incalAX2020$$", "CharacterSet"=>"UTF-8");
	$con = sqlsrv_connect($serverName, $connectionInfo);

	if($con){
		
	}else{
		echo "fallo en la conexión";
	}
	
	function ygetDate(){
		date_default_timezone_set("America/Tegucigalpa");
		$ahora = date('c');
		return substr($ahora,0,19);
	}
?>