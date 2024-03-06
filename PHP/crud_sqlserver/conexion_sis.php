<?php
	$serverName = "127.0.0.1";
	$connectionInfo = array("Database"=>"CRUDWEB", "UID"=>"ALONSODEV", "PWD"=>"6321@JArt_1", "CharacterSet"=>"UTF-8");
	$cn = sqlsrv_connect($serverName, $connectionInfo);

	if($cn){
		
	}else{
		echo "fallo en la conexión";
	}

?>