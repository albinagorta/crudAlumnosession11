<?php

require_once "conexion.php";

class Modelo extends Conexion
{
	
	public function create($datosModel)
	{

		$stmt = Conexion::conectar()->prepare("INSERT INTO alumno (apellidop,apellidom,nombre) VALUES (:apellidop, :apellidom, :nombre)");

		$stmt->bindParam(":apellidop", $datosModel["apellidop"], PDO::PARAM_STR);
		$stmt->bindParam(":apellidom", $datosModel["apellidom"], PDO::PARAM_STR);
		$stmt->bindParam(":nombre", $datosModel["nombre"], PDO::PARAM_STR);

		if($stmt->execute()){
			return true;
		}else{
			return false;
		}
	}

	


	# red --------------------------------

	public function read()
	{
		$stmt = Conexion::conectar()->prepare("SELECT * FROM alumno");

		$stmt->execute();

		$stmt->bindColumn("id", $id);
		$stmt->bindColumn("apellidop", $apellidop);
		$stmt->bindColumn("apellidom", $apellidom);
		$stmt->bindColumn("nombre", $nombre);
		$arrayalumno = array();

		while($fila = $stmt->fetch(PDO::FETCH_BOUND))
		{
			$alu = array();
			$alu["id"] = ($id);
			$alu["pat"] = ($apellidop);
			$alu["mat"] = ($apellidom);
			$alu["nom"] = ($nombre);			
			array_push($arrayalumno, $alu);

		}

		return $arrayalumno;
		
	}

}
?>