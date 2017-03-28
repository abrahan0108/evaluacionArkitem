<?php

/**
 * Obtiene todos los usuarios disponibles de la tabla
 */
require 'validaLogin.php';
if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    // Manejar petición GET
    $usuarios = validaLogin::getAll();
    if ($usuarios) {
        $datos["estado"] = 1;
        $datos["articlos"] = $usuarios;
        print json_encode($datos);
    } else {
        print json_encode(array(
            "estado" => 2,
            "mensaje" => "Ha ocurrido un error"
        ));
    }
}
