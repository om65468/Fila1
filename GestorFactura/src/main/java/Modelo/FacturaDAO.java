package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacturaDAO {

    private Connection conn;

    public FacturaDAO(Connection conn) {
        this.conn = conn;
    }

    // Insertar una factura
    public void insertar(Factura f) throws SQLException {
        String sql = "INSERT INTO facturas (tipo, numFactura, fechaEmision, idSecundario, concepto, base, iva, total, estado, observaciones) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, String.valueOf(f.getTipo()));
            ps.setInt(2, f.getNumFactura());
            ps.setDate(3, Date.valueOf(f.getFechaEmision()));
            ps.setInt(4, f.getIdSecundario());
            ps.setString(5, f.getConcepto());
            ps.setDouble(6, f.getBase());
            ps.setDouble(7, f.getIva());
            ps.setDouble(8, f.getTotal());
            ps.setString(9, f.getEstado());
            ps.setString(10, f.getObservaciones());

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
        String sql = "UPDATE facturas SET tipo=?, numFactura=?, fechaEmision=?, idSecundario=?, concepto=?, base=?, iva=?, total=?, estado=?, observaciones=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, String.valueOf(f.getTipo()));
            ps.setInt(2, f.getNumFactura());
            ps.setDate(3, Date.valueOf(f.getFechaEmision()));
            ps.setInt(4, f.getIdSecundario());
            ps.setString(5, f.getConcepto());
            ps.setDouble(6, f.getBase());
            ps.setDouble(7, f.getIva());
            ps.setDouble(8, f.getTotal());
            ps.setString(9, f.getEstado());
            ps.setString(10, f.getObservaciones());
            ps.setInt(11, f.getId());

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
        f.setConcepto(rs.getString("concepto"));
        f.setBase(rs.getDouble("base"));
        f.setIva(rs.getDouble("iva"));
        f.setTotal(rs.getDouble("total"));
        f.setEstado(rs.getString("estado"));
        f.setObservaciones(rs.getString("observaciones"));
        return f;
    }
}

