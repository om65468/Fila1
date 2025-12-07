package Modelo;

import net.sf.jasperreports.engine.*;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.io.InputStream;
import java.io.IOException;

public class GeneradorFactura {

    // Rutas de recursos dentro del Classpath (src/main/resources/)
    // ¡IMPORTANTE! Asegúrate de que las rutas reflejen tu estructura: davinci/gestorfactura/reports/
    private static final String RUTA_BASE_RECURSOS = "davinci/gestorfactura/reports/"; 
    private static final String NOMBRE_JASPER = RUTA_BASE_RECURSOS + "Factura.jasper";
    private static final String NOMBRE_LOGO = RUTA_BASE_RECURSOS + "LogoEmpresaHotel.png"; 

    /**
     * Genera la factura en PDF para un número de factura específico.
     * @param connection Conexión activa a MySQL.
     * @param numFactura El número de factura a cargar (ej: 1001).
     * @return String Ruta del PDF generado, o null si hay error.
     */
    public String generarFacturaPDF(Connection connection, int numFactura) {
    String pdfPath = "factura_" + numFactura + ".pdf";
    JasperPrint jasperPrint = null;
    InputStream jasperStream = null;
    InputStream logoStream = null;
    
    try {
    // --- 1. CARGAR RECURSOS COMO STREAM (MÉTODO PORTABLE) ---
    jasperStream = getClass().getClassLoader().getResourceAsStream(NOMBRE_JASPER);
    if (jasperStream == null) {
    throw new JRException("El archivo compilado " + NOMBRE_JASPER + " no se encuentra en el classpath.");
    }
    
    logoStream = getClass().getClassLoader().getResourceAsStream(NOMBRE_LOGO);
    if (logoStream == null) {
    System.err.println("Advertencia: El archivo de logo " + NOMBRE_LOGO + " no se encontró.");
    }
    
    // --- 2. PREPARAR PARÁMETROS ---
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("P_NUMERO_FACTURA", numFactura);


    // Pasa el logo como InputStream (asegúrate de que este parámetro exista en tu JRXML)
    parameters.put("LOGO_STREAM", logoStream);
    
    // --- 3. LLENAR EL INFORME ---
    System.out.println("Llenando informe para Factura N° " + numFactura);
    jasperPrint = JasperFillManager.fillReport(
    jasperStream,
    parameters,
    connection
    );
    
    // --- 4. EXPORTAR A PDF ---
    JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPath);
    System.out.println("✅ Factura exportada exitosamente: " + pdfPath);
    return pdfPath;
    
    } catch (JRException | NullPointerException e) {
    System.err.println("❌ Error en GeneradorFactura: " + e.getMessage());
    e.printStackTrace();
    return null;
    } finally {
    try {
    if (jasperStream != null) jasperStream.close();
    if (logoStream != null) logoStream.close();
    } catch (IOException e) {
    e.printStackTrace();
    }
    }
    }
}