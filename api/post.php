
<?php

// Set del contenido de la respuesta  http://localhost/api/post.php
//header('Content-Type: application/json; charset=utf8');

require_once "controlador.php";

$db = new Controlador();

$result = $db->create($_POST['pat'], $_POST['mat'], $_POST['nom']);

echo json_encode($result);

?>