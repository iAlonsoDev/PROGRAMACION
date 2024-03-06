<?php
session_start();
require('conexion.php');

$op=$_POST['op'];

if(isset($_POST['proc']))
	$rx=$_POST['proc'];

// Carga parametros
if($op=='load_parametros'){
   $sql="SELECT [param_desv]
   FROM [Balance_ScoreCard].[dbo].[calidad_parametros] 
   WHERE [dbo].[calidad_parametros].idproceso = ".$rx;
	
	$rs = sqlsrv_query($con, $sql, array(), array( "Scrollable" => SQLSRV_CURSOR_KEYSET ));
	$salida='<option value="-1">-</option>';
	while($row=sqlsrv_fetch_array($rs)){
		$salida.='<option value="'.$row[0].'">'.$row[0].'</option>';		
	}
    echo $salida;
}

// Carga objetivo
if($op=='load_objetivos'){
   $sql="SELECT [objetivo]
   FROM [Balance_ScoreCard].[dbo].[calidad_parametros] 
   WHERE [dbo].[calidad_parametros].param_desv ='".$rx."'";
   
   $rs = sqlsrv_query($con, $sql, array(), array( "Scrollable" => SQLSRV_CURSOR_KEYSET ));
	$salida='';
	$row=sqlsrv_fetch_array($rs);
	$salida=$row[0];		
	
    echo $salida;

}

?>

