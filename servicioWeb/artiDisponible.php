<?php
/**
 * Representa la estructura de las las vistas ArtDisponible
 * almacenadas en la base de datos
 */
require 'database.php';
class artDisponible
{
    function __construct()
    {
    }
    /**
     * Retorna en la fila especificada de la vista 'artdisponible'
     *
     */
    public static function getAll()
    {
        $consulta = "SELECT * FROM artdisponible";
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

