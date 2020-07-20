
<?php

// Set del contenido de la respuesta http://localhost/api/get.php
//header('Content-Type: application/json; charset=utf8');

require_once "controlador.php";

$db = new Controlador();
$result=$db->readalumno();

echo json_encode($result);

?>