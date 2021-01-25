import java.sql.*;

public class App {
    public static void main(String[] args) {

        Conexion conexion = new Conexion();
        conexion.abrirConexion("add", "localhost", "root", "");
        try {
            //conexion.consultaAlumno("la");
            conexion.insertarAlumno("Marta", "Compi", 165, 31);
            //conexion.insertarAsignatura(10, "Bioética");
            //conexion.eliminarAlumno(31,"alumnos");
            
        } catch (SQLException e) {
            System.out.println("error: "+e.getMessage());
        }
        conexion.cerrarConexion();
        
    }

}
