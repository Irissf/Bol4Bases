import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {

    private Connection conexion;
    private Statement statement;
    private String consulta;

    public void abrirConexion(String bd, String servidor, String usuario, String password) {
        try {
            String url = String.format("jdbc:mariadb://%s:3306/%s", servidor, bd);
            this.conexion = DriverManager.getConnection(url, usuario, password);// Establecemos la conexión con la BD
            if (this.conexion != null){
                System.out.println("Conectado a la base de datos " + bd + " en " + servidor);
            }
                
            else
                System.out.println("No se ha conectado a la base de datos " + bd + " en " + servidor);
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getLocalizedMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("Código error: " + e.getErrorCode());
        }
    }

    public Statement createStatement() {
        return null;
    }

    public void cerrarConexion() {
        try {
            this.conexion.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getLocalizedMessage());
        }
    }

    //========================================EJERCICIO 1=====================================================================
    /**
     * Un método que permita consultar alumnos que contengan una cadena de caracteres en su nombre.
        Además deberá visualizar el número de resultados obtenidos.
     * @param cadena
     * @throws SQLException
     */

    public void consultaAlumno(String cadena) throws SQLException {

        consulta = "SELECT * FROM alumnos WHERE nombre LIKE \"%"+cadena+"%\""; // Consulta a ejecutar

        Statement stmt = conexion.createStatement();
        ResultSet rs = stmt.executeQuery(consulta); // Se ejecuta la consulta
        while (rs.next()) { // Mientras queden filas en rs (el método next devuelve true) recorremos las
                            // filas
            System.out.println(rs.getInt(1) + "\t" + // Se obtiene datos en función del número de columna
                    rs.getString("nombre") + "\t" + rs.getString("apellidos")); // o de su nombre
        }
        stmt.close(); // Se cierra el Statement
        cerrarConexion();
    }

    //NOTAS executeQuery para consultas que recibes datos, executeUpdate consultas que no reciben datos pero si los.
    //executeUpdate devuelve el numero de filas insertadas.
    
    //========================================EJERCICIO 2=====================================================================
    /**
     *  Dar de alta alumnos y asignaturas
     * 
     * @param nombre
     * @param apellido
     * @param altura
     * @param aula
     */

    public void insertarAlumno(String nombre, String apellido, int altura, int aula) throws SQLException{

        consulta = "INSERT INTO alumnos(nombre, apellidos, altura, aula) VALUES ('" +nombre+ "', '"+ apellido +"',"+altura+", "+ aula +")";

        boolean numAulaCorrecto = comprobarInt("numero", "aulas", aula);
        
        if(numAulaCorrecto){
            statement = conexion.createStatement();
            statement.executeUpdate(consulta);
            System.out.println("los datos fueron introducidos correctamente");
        }else{
            System.out.println("No existe esa aula");
        }
    }

    public void insertarAsignatura(int cod, String nombre) throws SQLException {

        consulta = "INSERT INTO asignaturas VALUES (" + cod + ",'" + nombre + "');";
        boolean numAsignaturaCorrecta = comprobarInt("cod", "asignaturas", cod);

        if(!numAsignaturaCorrecta){
            statement = conexion.createStatement();
            statement.executeUpdate(consulta);
            System.out.println("los datos fueron introducidos correctamente");
        }else{
            System.out.println("El codigo de asignatura ya existe");
        }

    }   

    //=================================== EJERCICIO 3 ======================================================================

    /**
     * Dar de baja alumnos y asignaturas.
     * 
     * @param id
     * @throws SQLException
     */
    public void eliminarAlumno(int id , String tabla) throws SQLException{
        
        consulta = String.format("DELETE FROM alumnos WHERE %s = 3;", (tabla.equals("alumnos"))?"codigo":"cod");

        boolean existe = comprobarInt((tabla.equals("alumnos"))?"codigo":"cod", tabla, id);
        if (existe) {
            statement = conexion.createStatement();
            statement.executeUpdate(consulta);
            System.out.printf("Eliminado %s", (tabla.equals("alumnos"))?"el alumno":"la asignatura");
        }else{
            System.out.printf("no existe %s", (tabla.equals("alumnos"))?"el alumno":"la asignatura");
        }
    }

    //=================================== EJERCICIO 4 ======================================================================

    
  
    //=================================== FUNCIONES DE UTILIDAD ============================================================

    public boolean comprobarInt(String campo, String tabla, int numComparar){
        String consulta = "SELECT " + campo + " FROM " + tabla + "";

        try {
            statement = conexion.createStatement();
            ResultSet result = statement.executeQuery(consulta);

            while (result.next()) {
                if(result.getInt(campo) == numComparar){
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error:" +e.getMessage());
        }

        return false;
        
    }

}
