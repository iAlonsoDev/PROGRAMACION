<!DOCTYPE html>

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
	<div class="abs-center">

	<div class="col-sm-6">
	
		<div class="panel-group">
		
			<div class="panel panel-primary">
				
				<div class="panel-body">
					
						<div class="col-md-8 col-md-offset-2">

							<?php	
							if(isset($_GET['editar']))
							{
									$editar_id = $_GET['editar'];

									$consulta = "SELECT dbo.calidad_desviaciones.iddesviacion
									    ,dbo.calidad_desviaciones.fecha
								        ,dbo.calidad_proceso.proceso
										,dbo.calidad_desviaciones.param_desv
										,dbo.calidad_desviaciones.objetivo
										,dbo.calidad_desviaciones.result_desv
										FROM dbo.calidad_proceso INNER JOIN
										dbo.calidad_desviaciones ON dbo.calidad_proceso.idproceso = dbo.calidad_desviaciones.idproceso
										WHERE dbo.calidad_desviaciones.iddesviacion='$editar_id'";
									$ejecutar = sqlsrv_query($con, $consulta);

									$fila = sqlsrv_fetch_array($ejecutar);
									$IDDESVIACION = $fila['iddesviacion'];
									$FECHA = $fila['fecha']->format('Y-m-d H:i:s');
									$PROCESO = $fila['proceso'];
									$PARAMETRO_DESVIACION = $fila['param_desv'];
									$OBJETIVO = $fila['objetivo'];
									$RESULTADO_DESVIACION = $fila['result_desv'];
							}
							?>
							
							    <br />

							<form method="POST" action="">
								<div class="form-group">
											<label>IDDESVIACION:</label>
											<input readonly type="text" name="IDDESVIACION" class="form-control" value="<?php echo $IDDESVIACION; ?>"><br />
								
								<div class="form-group">
											<label>FECHA:</label>
											<input readonly type="text" name="FECHA" class="form-control" value="<?php echo $FECHA; ?>"><br />
								</div>
								<div class="form-group">
											<label>PROCESO:</label>
											<input readonly type="text" name="PROCESO" class="form-control" value="<?php echo $PROCESO; ?>"><br />
								</div>
								<div class="form-group">
											<label>PARAMETRO_DESVIACION:</label>
											<input readonly type="text" name="PARAMETRO_DESVIACION" class="form-control" value="<?php echo $PARAMETRO_DESVIACION; ?>"><br />
								</div>
								<div class="form-group">
											<label>OBJETIVO:</label>
											<input readonly type="text" name="OBJETIVO" class="form-control" value="<?php echo $OBJETIVO; ?>"><br />
								</div>
								<div class="form-group">
											<label>RESULTADO_DESVIACION:</label>
											<input type="text" name="RESULTADO_DESVIACION" class="form-control" value="<?php echo $RESULTADO_DESVIACION; ?>"><br />
								</div>
								
								<div class="form-group">				
									<input type="submit" name="update" class="btn btn-primary" value="ACTUALIZAR"><br />
								</div>
							</form>
							
							<?php
							if(isset($_POST['update'])){
									$RESULTADO_DESVIACION = $_POST['RESULTADO_DESVIACION'];

									$consulta = "UPDATE [Balance_ScoreCard].[dbo].[calidad_desviaciones] SET dbo.calidad_desviaciones.result_desv='$RESULTADO_DESVIACION' WHERE dbo.calidad_desviaciones.iddesviacion ='$editar_id'";

									$ejecutar = sqlsrv_query($con, $consulta);

									if($ejecutar){
										echo "<script>alert('Datos actualizados')</script>";
										echo "<script>window.open('registrar_desviacion.php', '_self')</script>";
									}	else
									{
										echo "<script>alert('Error! no se actualizo el registro')</script>";
									}		
								}
							?>
							
						</div>
					</div>
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



