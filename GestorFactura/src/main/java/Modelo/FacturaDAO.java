package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacturaDAO {

    private Connection conn;

    public FacturaDAO() {
        try {
            this.conn = ConexionBBDD.get();
        } catch (Exception e) {
            System.out.println("No funciona la conexioin de EntidadDao a la BBDD, mira el constructor");
            e.printStackTrace();
        }
    }

    // Insertar una factura
    public void insertar(Factura f) throws SQLException {
        // La sentencia SQL ahora incluye id_empresa
        String sql = "INSERT INTO facturas (tipo, numFactura, fechaEmision, idSecundario, id_empresa, concepto, base, iva, total, estado, observaciones) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, String.valueOf(f.getTipo()));
            ps.setInt(2, f.getNumFactura());
            ps.setDate(3, Date.valueOf(f.getFechaEmision()));
            ps.setInt(4, f.getIdSecundario());
            ps.setInt(5, f.getIdEmpresa()); // <--- ¡NUEVO CAMPO!
            ps.setString(6, f.getConcepto());
            ps.setDouble(7, f.getBase());
            ps.setDouble(8, f.getIva());
            ps.setDouble(9, f.getTotal());
            ps.setString(10, f.getEstado());
            ps.setString(11, f.getObservaciones());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    f.setId(rs.getInt(1));
                }
            }
        }
    }

    // Obtener factura por ID
    public Factura obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM facturas WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearFactura(rs);
                }
            }
        }
        return null;
    }

    // Listar todas las facturas
    public List<Factura> listarTodas() throws SQLException {
        List<Factura> lista = new ArrayList<>();
        String sql = "SELECT * FROM facturas";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapearFactura(rs));
            }
        }
        return lista;
    }

    // Actualizar factura
    public void actualizar(Factura f) throws SQLException {
        // La sentencia SQL de UPDATE ahora incluye id_empresa
        String sql = "UPDATE facturas SET tipo=?, numFactura=?, fechaEmision=?, idSecundario=?, id_empresa=?, concepto=?, base=?, iva=?, total=?, estado=?, observaciones=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, String.valueOf(f.getTipo()));
            ps.setInt(2, f.getNumFactura());
            ps.setDate(3, Date.valueOf(f.getFechaEmision()));
            ps.setInt(4, f.getIdSecundario());
            ps.setInt(5, f.getIdEmpresa()); // <--- ¡NUEVO CAMPO!
            ps.setString(6, f.getConcepto());
            ps.setDouble(7, f.getBase());
            ps.setDouble(8, f.getIva());
            ps.setDouble(9, f.getTotal());
            ps.setString(10, f.getEstado());
            ps.setString(11, f.getObservaciones());
            ps.setInt(12, f.getId()); // ID al final

            ps.executeUpdate();
        }
    }

    // Eliminar factura
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM facturas WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // Método auxiliar para mapear ResultSet a Factura
    private Factura mapearFactura(ResultSet rs) throws SQLException {
        Factura f = new Factura();
        f.setId(rs.getInt("id"));
        f.setTipo(rs.getString("tipo").charAt(0));
        f.setNumFactura(rs.getInt("numFactura"));
        f.setFechaEmision(rs.getDate("fechaEmision").toString());
        f.setIdSecundario(rs.getInt("idSecundario"));
        f.setIdEmpresa(rs.getInt("id_empresa")); // <--- ¡NUEVO CAMPO LEÍDO!
        f.setConcepto(rs.getString("concepto"));
        f.setBase(rs.getDouble("base"));
        f.setIva(rs.getDouble("iva"));
        f.setTotal(rs.getDouble("total"));
        f.setEstado(rs.getString("estado"));
        f.setObservaciones(rs.getString("observaciones"));
        return f;
    }
    
    public List<Factura> obtenerTodasLasFacturas() throws SQLException {
        List<Factura> lista = new ArrayList<>();
        // En un entorno multi-empresa, esta consulta usaría un JOIN a 'empresa_entidad_relacion'
        // para filtrar por clientes/proveedores de la 'idEmpresa'.
        String sql = "SELECT * FROM facturas ORDER BY numFactura DESC"; 
        
        try (PreparedStatement ps = conn.prepareStatement(sql); 
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                lista.add(mapearFactura(rs));
            }
        }
        return lista;
    }
    
    public List<Factura> obtenerFacturasPorEmpresa(int idEmpresa) throws SQLException {
        List<Factura> lista = new ArrayList<>();
        // Consulta SQL que filtra por la nueva columna id_empresa
        String sql = "SELECT * FROM facturas WHERE id_empresa = ? ORDER BY numFactura DESC"; 
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEmpresa); // Establecer el parámetro id_empresa
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearFactura(rs));
                }
            }
        }
        return lista;
    }
    
}

