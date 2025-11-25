package Modelo;

import java.io.InputStream;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBBDD {
    
    private static final Properties PROPIEDADES = new Properties();
    private static String URL;
    private static String USER;
    private static String PASS;
    private static Connection conn = null;
    
    static {
        try (InputStream input = ConexionBBDD.class.getClassLoader().getResourceAsStream("db.properties")) {
            
            if (input == null) {
                throw new RuntimeException("Error: Archivo 'db.properties' no encontrado en el classpath.");
            }

            // Cargar el archivo
            PROPIEDADES.load(input);

            // Asignar valores a las variables estáticas para facilitar el uso
            URL = PROPIEDADES.getProperty("db.url");
            USER = PROPIEDADES.getProperty("db.user");
            PASS = PROPIEDADES.getProperty("db.pass");
            System.out.println("url "+URL);
            System.out.println("USER "+USER);
            System.out.println("PASS "+PASS);
 

        } catch (Exception e) {
            // Manejar la excepción de carga de archivo
            System.err.println("Fallo al cargar las propiedades de la base de datos.");
            e.printStackTrace();
            // Es buena práctica relanzar la excepción para detener la aplicación si falla la configuración esencial
            throw new ExceptionInInitializerError(e); 
        }
    }
    
    public static Connection get() throws Exception {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(URL, USER, PASS);
        }
        return conn;
    }
}
