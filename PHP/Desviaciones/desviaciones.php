<!DOCTYPE html>
<?php
	include("conexion.php");
?>
<meta charset="UTF-8">

<html>
	<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="refresh"  />

    <title>INCAL S.A DE C.V</title>
	
    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap3.min.css" rel="stylesheet">

	<script src="js/jquery.js"></script>
	
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link href="css/bootstrap-datetimepicker.min.css" rel="stylesheet">  
	
	
	<script language ="javascript">
	
	</script>
	
	
	<style type="text/css">
		.abs-center {
		  display: flex;
		  align-items: center;
		  justify-content: center;
		  min-height: 100vh;
		}
		.fondo{
		   background: url(images/bg.png) no-repeat ;
		   -webkit-background-size: cover;
		   -moz-background-size: cover;
		   -o-background-size: cover;
		   background-size: cover;
		   height: 100%;
		   width: 100% ;
		}
    </style>
  
</head>

<body>
	<div  class="fondo"> 
	<div class="abs-center">

		<div class="col-sm-10">
		<div class="panel-group">
		
		<br>
			<br>
			<div class="panel panel-primary">
			
			<div class="panel-heading">INCAL S.A DE C.V - [MODULO DE ACTUALIZACION DE DESVIACION] </div>

			<div class="panel-body">
				<div class="row">
                      <div class="col col-sm-12"><img src="images/Logotipo Calidra.png" alt="" width="100" height="40"/><img src="images/Logotipo Incal.png" alt="" width="110" height="35" align="right" /></div>
                </div>
				<div class="row">
						<br>
                        <div id="pnl_err" class="col col-sm-12" style="border-style: groove; border-color: red;"><?php
						?></div>
                </div>
				
				<br>
				<br>
				
			    <div class="col-md-8 col-md-offset-0">

						<table class="table table-bordered table-responsive">
							<tr>
								<td>IDDESVIACION</td>
								<td>PROCESO</td>
								<td>PARAMETRO_DESVIACION</td>
								<td>OBJETIVO</td>
								<td>RESULTADO_DESVIACION</td>
								<td>CORRECCION</td>
								<td>RESPONSABLE</td>
								<td>TIEMPO_ESTIMADO</td>
								<td>RESULTADO_VALIDADO</td>
								<td>HR_ANALISIS_VALIDADO</td>
							</tr>
							
						<?php
								$consulta = "SELECT top(25) dbo.calidad_desviaciones.iddesviacion
								,dbo.calidad_proceso.proceso
								,dbo.calidad_desviaciones.param_desv
								,dbo.calidad_desviaciones.objetivo
								,dbo.calidad_desviaciones.result_desv
								,dbo.calidad_desviaciones.correccion
								,dbo.calidad_desviaciones.responsable
								,isnull(dbo.calidad_desviaciones.t_estimado,'') t_estimado
								,dbo.calidad_desviaciones.result_validado
								,dbo.calidad_desviaciones.hr_analisis_val
								FROM dbo.calidad_proceso INNER JOIN
								dbo.calidad_desviaciones ON dbo.calidad_proceso.idproceso = dbo.calidad_desviaciones.idproceso
								WHERE [fecha] >= (SELECT CONVERT (date, GETDATE()))
								ORDER BY [iddesviacion] DESC";

								$ejecutar = sqlsrv_query($con, $consulta);

								$i = 0;

								while($fila = sqlsrv_fetch_array($ejecutar)){
									$IDDESVIACION = $fila['iddesviacion'];
									$PROCESO = $fila['proceso'];
									$PARAMETRO_DESVIACION = $fila['param_desv'];
									$OBJETIVO = $fila['objetivo'];
									$RESULTADO_DESVIACION = $fila['result_desv'];
									$CORRECCION = $fila['correccion'];
									$RESPONSABLE = $fila['responsable'];
									$TIEMPO_ESTIMADO = $fila['t_estimado']->format('H:i:s');
									$RESULTADO_VALIDADO = $fila['result_validado'];
									$HR_ANALISIS_VALIDADO = $fila['hr_analisis_val'];
									$i++;
						?>

							<tr align="center">
								<td><?php echo $IDDESVIACION; ?></td>
								<td><?php echo $PROCESO; ?></td>
								<td><?php echo $PARAMETRO_DESVIACION; ?></td>
								<td><?php echo $OBJETIVO; ?></td>
								<td><?php echo $RESULTADO_DESVIACION; ?></td>
								<td><?php echo $CORRECCION; ?></td>
								<td><?php echo $RESPONSABLE; ?></td>
								<td><?php echo $TIEMPO_ESTIMADO ?></td>
								<td><?php echo $RESULTADO_VALIDADO; ?></td>
								<td><?php echo $HR_ANALISIS_VALIDADO; ?></td>
								<td><a href="desviaciones.php?editar=<?php echo $IDDESVIACION; ?>">Editar</a></td>
							</tr>

						<?php } ?>

						</table>
						</div>
	                </div>
						<?php
							if(isset($_GET['editar'])){
							include("editar_desviaciones.php");
							}
						?>	
						
	            </div>
	        </div>
	    </div>
	</div>

   <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
   <script src="js/moment.min.js"></script>
   <script src="js/bootstrap-datetimepicker.min.js"></script>
   <script src="js/bootstrap-datetimepicker.es.js"></script>
   
						

</body>
</html>



