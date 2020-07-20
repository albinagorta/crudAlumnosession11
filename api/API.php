<?php
    require "conexion.php";
    
    if($_SERVER['REQUEST_METHOD'] == 'GET') {
      // READ
      if(isset($_GET['id'])) {
        // http://localhost/PruebasCanal/API_REST.php
        $id = $_GET['id'];

        $sql_select_id = "SELECT id, nombre, apellido FROM alumnos WHERE id='$id'";
        $query_select_id = $mysqli->query($sql_select_id);
        
        $filas = $query_select_id->num_rows;
        if($filas == 0) {
          //echo "No existe ese registro";
          header("HTTP/1.0 204");
        } else {
          $resultado = $query_select_id->fetch_assoc();
          echo json_encode($resultado);
        }
      } else {
        //http://localhost/PruebasCanal/API_REST.php?idUsuario=#
        $sql_select = "SELECT id, nombre, apellido FROM alumnos";
        $query_select = $mysqli->query($sql_select);

        $datos = array();
        while($resultado = $query_select->fetch_assoc()) {
          $datos[] = $resultado;
        }

        echo json_encode($datos);
      }
    } else if($_SERVER['REQUEST_METHOD'] == 'POST') {
      // CREAD
      $datos = json_decode(file_get_contents("php://input"));
      $nombre = $datos->nombre;
      $apellido = $datos->apellido;
      if(empty($nombre) || empty($apellido)) {
        //echo "Faltan campos";
        header("HTTP/1.0 400");
      } else {
        $sql_insert = "INSERT INTO alumnos(nombre, apellido) VALUES('$nombre', '$apellido')";
        $query_insert = $mysqli->query($sql_insert);

        echo "Se inserto correctamente";
      }
    } else if($_SERVER['REQUEST_METHOD'] == 'PUT') {
      // UPDATE
      $datos = json_decode(file_get_contents("php://input"));
      $id = $datos->id;
      $nombre = $datos->nombre;
      $apellido = $datos->apellido;
      
      if(empty($id) || empty($nombre) || empty($apellido)) {
        //echo "Faltan campos";
        header("HTTP/1.0 400");
      } else {
        $sql_update = "UPDATE alumnos SET nombre='$nombre', apellido='$apellido' WHERE id='$id'";
        $query_update = $mysqli->query($sql_update);

        echo "Se actualizo correctamente";
      }
      
    } else if($_SERVER['REQUEST_METHOD'] == 'DELETE') {
      // DELETE
      if(isset($_GET['id'])) {
        $id = $_GET['id'];

        $sql_delete = "DELETE FROM alumnos WHERE id='$id'";
        $query_delete = $mysqli->query($sql_delete);

        echo "Se elimino el registro";
      } else {
        //echo "No hay elemento a borrar";
        header("HTTP/1.0 204");

      }
    }

?>