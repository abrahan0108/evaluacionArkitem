<?php
/**
 * Obtiene todas los articulos disponibles de la vista
 */
require 'artExistenciaNeta.php';
if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    // Manejar petición GET
    $articulo = artExistenciaNeta::getAll();
    if ($articulo) {
        $datos["estado"] = 1;
        $datos["articlos"] = $articulo;
        print json_encode($datos);
    } else {
        print json_encode(array(
            "estado" => 2,
            "mensaje" => "Ha ocurrido un error"
        ));
    }
}