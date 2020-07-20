<?php

class Conexion
{
	public function conectar()
	{
		$localhost = "localhost";
		$database = "dbalumno";
		$user = "root";
		$password = "";

			
			$link = new PDO("mysql:host=".$localhost.";dbname=".$database .";charset=utf8mb4",$user,
						$password,array(PDO::ATTR_EMULATE_PREPARES => false,
								PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
		                      PDO::MYSQL_ATTR_INIT_COMMAND => "SET NAMES utf8")
						);

		return $link;
	}
}

?>