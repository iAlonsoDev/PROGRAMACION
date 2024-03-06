<!DOCTYPE html> 

<?php 
	include("conexion_sis.php");
?>

<meta charset="UTF-8">

<html> 	

	<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Registro de Usuarios PHP-MSSQLSERVER</title>
    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">  

	</head>
		<body>
			<div class="col-md-8 col-md-offset-2">
				<h1>Registro de Usuarios</h1>

				<form method="POST" action="formulario.php">
					<div class="form-group">
						<label>Nombre:</label>
						<input type="text" name="nombre" class="form-control" placeholder="Escriba su nombre"><br />
					</div>
					<div class="form-group">
						<label>Contraseña:</label>
						<input type="text" name="passw" class="form-control" placeholder="Escriba su contraseña"><br />
					</div>
					<div class="form-group">
						<label>Email:</label>
						<input type="text" name="email" class="form-control" placeholder="Escriba su email"><br />
					</div>
					<div class="form-group">				
						<input type="submit" name="insert" class="btn btn-warning" value="Guardar"><br />
					</div>
				</form>
			</div>

			<br /><br /><br />

			<?php
				if(isset($_POST['insert']))
				{
					$usuario = $_POST['nombre'];
					$pass = $_POST['passw'];
					$email = $_POST['email'];

					$sql = "INSERT INTO  crud_sqlserver_usuarios(usuario, password, email)VALUES('$usuario', '$pass', '$email')";
					$cmd = sqlsrv_query($cn, $sql);

					if($cmd){
						echo "<script>alert('Se creo un nuevo registro')</script>";
					} else
					{
						echo "<script>alert('Error! no se guardo el registro')</script>";
					}
				}
			?>

			<div class="col-md-8 col-md-offset-2">
				<table class="table table-bordered table-responsive">
					
					<tr>
						<td>ID Usuario</td>
						<td>Usuario</td>
						<td>Password</td>
						<td>Email</td>
						<td>Acción</td>
						<td>Acción</td>
					</tr>

					<?php
						$sql = "SELECT * FROM crud_sqlserver_usuarios";
						$cmd = sqlsrv_query($cn, $sql);
						$i = 0;

						while($fila = sqlsrv_fetch_array($cmd)){
							$idusuario = $fila['idusuario'];
							$usuario = $fila['usuario'];
							$password = $fila['password'];
							$email = $fila['email'];
							$i++;
					?>

					<tr align="center">
						<td><?php echo $idusuario; ?></td>
						<td><?php echo $usuario; ?></td>
						<td><?php echo $password; ?></td>
						<td><?php echo $email; ?></td>
						<td><a href="formulario.php?editar=<?php echo $idusuario; ?>">Editar</a></td>
						<td><a href="formulario.php?borrar=<?php echo $idusuario; ?>">Borrar</a></td>
					</tr>
					<?php } ?>

				</table>
			</div>

			<?php
				if(isset($_GET['editar'])){
					include("editar.php");
				}
			?>	

			<?php	
			if(isset($_GET['borrar'])){

					$borrar_idusuario= $_GET['borrar'];
					$sql = "DELETE FROM crud_sqlserver_usuarios WHERE idusuario='$borrar_idusuario'";
					$cmd = sqlsrv_query($cn, $sql);

					if($cmd){
						echo "<script>alert('El usuario ha sido borrado')</script>";
						echo "<script>window.open('formulario.php', '_self')</script>";
					}	else
					{
						echo "<script>alert('Error! no se elimino el registro')</script>";
					}
				}
			?>
			
		</body>
	</head>
</html>



