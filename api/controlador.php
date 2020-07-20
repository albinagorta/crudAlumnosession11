<?php

require_once "modelo.php";

class Controlador
{
	public function create($apellidop,$apellidom,$nombre)
	{
	
		$datosController = array("apellidop"=>$apellidop,
			"apellidom"=>$apellidom,
			"nombre"=>$nombre);

		$respuesta = Modelo::create($datosController);
		return $respuesta;
	}

		
	public function readalumno()
	{
		$respuesta = Modelo::read();
		return $respuesta;
	}


}
?>