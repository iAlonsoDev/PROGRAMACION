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
	
		$(document).ready(function(){
			
			load_comboparam();
	
                $("#insert").click(function(){
					$("#hd_opc").val('insert')
					document.jform.submit();
				})
				

				//llenar combobox
				$("#idproceso").change(function(){
					load_comboparam();
				})
				
				function load_comboparam(){
				$.post("ajax.php", { op: 'load_parametros', proc: $("#idproceso").val()}, function(data){	
						$("#param_desv").empty().append(data);
						$("#param_desv").trigger("chosen:updated");	
			    });
			    }
				
				$("#param_desv").change(function(){
					load_obj();
				});
		
				function load_obj(){
				$.post("ajax.php", { op: 'load_objetivos', proc: $("#param_desv").val()}, function(data){	
						$("#objetivo").val(data);
			    });
				}
				
		});
				
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

		<div class="col-sm-8">
		<div class="panel-group">
		<br>
			<br>
			<div class="panel panel-primary">
				
			<div class="panel-heading">INCAL S.A DE C.V - [MODULO DE REGISTRO DESVIACION] </div>

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
				
			    <div class="col-md-10 col-md-offset-1">

						<form method="POST" action="registrar_desviacion.php" name="jform">
					
						<input type="hidden" id="hd_opc" name="hd_opc">
							
						<div class="form-group">
							<label>Fecha:</label>
							<input type='text' id="fecha" name="fecha" class="form-control" value="<?php echo ygetDate(); ?>" readonly/> <br />
							 
						<div class="form-group">
							<label>Proceso:</label>
						<select id="idproceso" name="idproceso"
						[(ngModel)]="idproceso" class="form-control">
							<?php
								$consulta = "SELECT [idproceso], [proceso] FROM [Balance_ScoreCard].[dbo].[calidad_proceso]";
								$ejecutar = sqlsrv_query($con, $consulta);
								$i = 0;
								while($fila = sqlsrv_fetch_array($ejecutar)){
									$idproceso = $fila['idproceso'];
									$proceso = $fila['proceso'];
									$i++;
							?>
								<option value="<?php echo $idproceso; ?>"><?php echo $proceso; ?></option>
							<?php } ?>
						</select><br />
			
						<div class="form-group">
							<label>Parametro Desviacion:</label>
							
						<select id="param_desv" name="param_desv"
						[(ngModel)]="param_desv" class="form-control">
						</select><br />
						
						<div class="form-group">
								<label>Objetivo:</label>
								<input readonly type="text" id="objetivo" name="objetivo" class="form-control" placeholder=""><br />
								
								
							
								<div class="form-group">
									<label>Resultado Desviacion:</label>
									<input type="text" name="result_desv" class="form-control" placeholder=""><br />
								
								<div class="form-group">				
									<button type="button" id="insert" name="insert" class="btn btn-primary">Guardar</button><br />
								</div>
						</form>
						
						
						<?php
							if (isset($_POST['hd_opc']) and ($_POST['fecha']<>''))
							{
								if ($_POST['hd_opc']=="insert")
								{
									$fecha = $_POST['fecha'];
									$idproceso = $_POST['idproceso'];
									$param_desv = $_POST['param_desv'];
									$objetivo = $_POST['objetivo'];
									$result_desv = $_POST['result_desv'];

									$insertar = "INSERT INTO calidad_desviaciones(fecha, idproceso, param_desv, objetivo, result_desv)VALUES('$fecha', '$idproceso', '$param_desv', '$objetivo','$result_desv')";

									$ejecutar = sqlsrv_query($con, $insertar);

									if($ejecutar)
									{
										echo "<script> alert('Datos Guardados Correctamente'); </script>"; 
										echo'<meta http-equiv="refresh" content="1;URL=registrar_desviacion.php">';
									} else
									{
										echo "<script>alert('Error! no se guardo la desviacion')</script>";
									}
								}
							}
						?>
						
						<?php	
							if(isset($_GET['borrar'])){
								$borrar_id = $_GET['borrar'];
									$borrar = "DELETE FROM [Balance_ScoreCard].[dbo].[calidad_desviaciones] WHERE iddesviacion='$borrar_id'";
									$ejecutar = sqlsrv_query($con, $borrar);
									if($ejecutar){
									echo "<script>alert('La desviacion se ha borrado..!')</script>";
									echo "<script>window.open('registrar_desviacion.php', '_self')</script>";
								}	else
								{
									echo "<script>alert('Error! no se elimino el registro')</script>";
								}
							}
						?>
						
						<table class="table table-bordered table-responsive">
							<tr>
								<td>IDDESVIACION</td>
								<td>FECHA</td>
								<td>PROCESO</td>
								<td>PARAMETRO_DESVIACION</td>
								<td>OBJETIVO</td>
								<td>RESULTADO_DESVIACION</td>
							</tr>
							
						<?php
								$consulta = "SELECT top(25) dbo.calidad_desviaciones.iddesviacion
								,dbo.calidad_desviaciones.fecha
								,dbo.calidad_proceso.proceso
								,dbo.calidad_desviaciones.param_desv
								,dbo.calidad_desviaciones.objetivo
								,dbo.calidad_desviaciones.result_desv
								FROM dbo.calidad_proceso INNER JOIN
								dbo.calidad_desviaciones ON dbo.calidad_proceso.idproceso = dbo.calidad_desviaciones.idproceso
								WHERE [fecha] >= (SELECT CONVERT (date, GETDATE()))
								ORDER BY [iddesviacion] DESC";

								$ejecutar = sqlsrv_query($con, $consulta);

								$i = 0;

								while($fila = sqlsrv_fetch_array($ejecutar)){
									$IDDESVIACION = $fila['iddesviacion'];
									$FECHA = $fila['fecha']->format('Y-m-d H:i:s');
									$PROCESO = $fila['proceso'];
									$PARAMETRO_DESVIACION = $fila['param_desv'];
									$OBJETIVO = $fila['objetivo'];
									$RESULTADO_DESVIACION = $fila['result_desv'];
									$i++;
						?>

							<tr align="center">
								<td><?php echo $IDDESVIACION; ?></td>
								<td><?php echo $FECHA; ?></td>
								<td><?php echo $PROCESO; ?></td>
								<td><?php echo $PARAMETRO_DESVIACION; ?></td>
								<td><?php echo $OBJETIVO; ?></td>
								<td><?php echo $RESULTADO_DESVIACION; ?></td>
								<td><a href="registrar_desviacion.php?editar=<?php echo $IDDESVIACION; ?>">Editar</a></td>
								<td><a href="registrar_desviacion.php?borrar=<?php echo $IDDESVIACION; ?>">Borrar</a></td>
							</tr>

						<?php } ?>
						</table>
	                </div>
	            </div>
						<?php
							if(isset($_GET['editar'])){
							include("editar_desviacion.php");
							}
						?>	

	        </div>
	    </div>
	</div>

   <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
   <script src="js/moment.min.js"></script>
   <script src="js/bootstrap-datetimepicker.min.js"></script>
   <script src="js/bootstrap-datetimepicker.es.js"></script>

</body>
</html>



