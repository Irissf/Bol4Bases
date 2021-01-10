import java.sql.*;

public class App {
    public static void main(String[] args) throws Exception {

        Conexion conexion = new Conexion();
        conexion.abrirConexion("add", "localhost", "root", "");

        Ejercicios ejer = new Ejercicios();

        //Ejercicio 1 =========================================
        try {
            ejer.ConsultaAlumno("La", conexion);
        } catch (SQLException e) {
            System.out.println("Error de base de datos");
        }
        

        conexion.cerrarConexion();
        
    }

}
