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
										WHERE dbo.calidad_desviaciones.iddesviacion='$editar_id'";
									$ejecutar = sqlsrv_query($con, $consulta);

									$fila = sqlsrv_fetch_array($ejecutar);
									$IDDESVIACION = $fila['iddesviacion'];
									$PROCESO = $fila['proceso'];
									$PARAMETRO_DESVIACION = $fila['param_desv'];
									$OBJETIVO = $fila['objetivo'];
									$RESULTADO_DESVIACION = $fila['result_desv'];
									$CORRECCION = $fila['correccion'];
									$RESPONSABLE = $fila['responsable'];
									$TIEMPO_ESTIMADO = $fila['t_estimado']->format("H:i:s");
									$RESULTADO_VALIDADO = $fila['result_validado'];
									$HR_ANALISIS_VALIDADO = $fila['hr_analisis_val'];
							}
							?>
							
							    <br />

							<form method="POST" action="">
								<div class="form-group">
											<label>IDDESVIACION:</label>
											<input readonly type="text" name="IDDESVIACION" class="form-control" value="<?php echo $IDDESVIACION; ?>"><br />
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
											<input readonly type="text" name="RESULTADO_DESVIACION" class="form-control" value="<?php echo $RESULTADO_DESVIACION; ?>"><br />
								</div>
								<div class="form-group">
											<label>CORRECCION:</label>
											<input  type="text" name="CORRECCION" class="form-control" value="<?php echo $CORRECCION; ?>"><br />
								</div>
								<div class="form-group">
											<label>RESPONSABLE:</label>
											<input type="text" name="RESPONSABLE" class="form-control" value="<?php echo $RESPONSABLE; ?>"><br />
								</div>
								<div class="form-group">
											<label>TIEMPO_ESTIMADO:</label>
											<input type="text" name="TIEMPO_ESTIMADO" class="form-control" value="<?php echo $TIEMPO_ESTIMADO; ?>"><br />
								</div>
								<div class="form-group">
											<label>RESULTADO_VALIDADO:</label>
											<input type="text" name="RESULTADO_VALIDADO" class="form-control" value="<?php echo $RESULTADO_VALIDADO; ?>"><br />
								</div>
								<div class="form-group">
											<label>HR_ANALISIS_VALIDADO:</label>
											<input type="text" name="HR_ANALISIS_VALIDADO" class="form-control" value="<?php echo $HR_ANALISIS_VALIDADO; ?>"><br />
								</div>
							
								
								<div class="form-group">				
									<input type="submit" name="update" class="btn btn-primary" value="ACTUALIZAR"><br />
								</div>
							</form>
							
							<?php
							if(isset($_POST['update'])){
									$CORRECCION = $_POST['CORRECCION'];
									$RESPONSABLE = $_POST['RESPONSABLE'];
									$TIEMPO_ESTIMADO = $_POST['TIEMPO_ESTIMADO'];
									
									$consulta = "UPDATE [Balance_ScoreCard].[dbo].[calidad_desviaciones]
									SET dbo.calidad_desviaciones.correccion ='$CORRECCION'
									,dbo.calidad_desviaciones.responsable ='$RESPONSABLE'
									,dbo.calidad_desviaciones.t_estimado ='$TIEMPO_ESTIMADO'
									WHERE dbo.calidad_desviaciones.iddesviacion ='$editar_id'";

									$ejecutar = sqlsrv_query($con, $consulta);

									if($ejecutar){
										echo "<script>alert('Datos actualizados')</script>";
										echo "<script>window.open('desviaciones.php', '_self')</script>";
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





