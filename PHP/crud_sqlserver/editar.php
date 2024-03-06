<?php	
	if(isset($_GET['editar']))
	{
		$editar_idusuario = $_GET['editar'];
		$sql = "SELECT * FROM crud_sqlserver_usuarios WHERE idusuario='$editar_idusuario'";
		$cmd = sqlsrv_query($cn, $sql);

		$fila = sqlsrv_fetch_array($cmd);
		$usuario = $fila['usuario'];
		$password = $fila['password'];
		$email = $fila['email'];
	}
?>

<br />

<div class="col-md-8 col-md-offset-2">
	<form method="POST" action="">
		<div class="form-group">
			<label>Nombre:</label>
			<input type="text" name="nombre" class="form-control" value="<?php echo $usuario; ?>"><br />
		</div>
		<div class="form-group">
			<label>Contraseña:</label>
			<input type="text" name="passw" class="form-control" value="<?php echo $password; ?>"><br />
		</div>
		<div class="form-group">
			<label>Email:</label>
			<input type="text" name="email" class="form-control" value="<?php echo $email; ?>"><br />
		</div>
		<div class="form-group">				
			<input type="submit" name="actualizar" class="btn btn-warning" value="ACTUALIZAR DATOS"><br />
		</div>
	</form>
</div>

<?php
	if(isset($_POST['actualizar']))
	{
		$actualizar_nombre = $_POST['nombre'];
		$actualizar_password = $_POST['passw'];
		$actualizar_email = $_POST['email'];

		$sql = "UPDATE crud_sqlserver_usuarios SET usuario='$actualizar_nombre', password='$actualizar_password', email='$actualizar_email' WHERE idusuario='$editar_idusuario'";

		$cmd = sqlsrv_query($cn, $sql);

		if($cmd){
			echo "<script>alert('Datos actualizados')</script>";
			echo "<script>window.open('formulario.php', '_self')</script>";
		}	else
		{
			echo "<script>alert('Error! no se actualizo el registro')</script>";
		}		
	}

?>