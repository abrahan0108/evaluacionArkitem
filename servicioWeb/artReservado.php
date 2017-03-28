<?php
/**
 * Representa la estructura de las las vistas ArtReservado
 * almacenadas en la base de datos
 */
require 'database.php';

class artReservado
{
    function __construct()
    {
    }
    /**
     * Retorna en la fila especificada de la vista 'artreservado'
     *
     */
    public static function getAll()
    {
        $consulta = "SELECT * FROM artreservado";
        try {
            // Preparar sentencia
            $comando = Database::getInstance()->getDb()->prepare($consulta);
            // Ejecutar sentencia preparada
            $comando->execute();
            return $comando->fetchAll(PDO::FETCH_ASSOC);
        } catch (PDOException $e) {
            return false;
        }
    }

}


?>

