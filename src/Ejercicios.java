import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Ejercicios {

    
    public void ConsultaAlumno(String cadena, Conexion conexion) throws SQLException {

        String query = "select * from aulas"; // Consulta a ejecutar

        Statement stmt = conexion.createStatement();
        ResultSet rs = stmt.executeQuery("select * from aulas"); // Se ejecuta la consulta
        while (rs.next()) { // Mientras queden filas en rs (el método next devuelve true) recorremos las
                            // filas
            System.out.println(rs.getInt(1) + "\t" + // Se obtiene datos en función del número de columna
                    rs.getString("nombreAula") + "\t" + rs.getInt("puestos")); // o de su nombre
        }
        stmt.close(); // Se cierra el Statement

    }

}
