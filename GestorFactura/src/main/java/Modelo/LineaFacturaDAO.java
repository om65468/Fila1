package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LineaFacturaDAO {

    private Connection conn;

    public LineaFacturaDAO(Connection conn) {
        this.conn = conn;
    }

    // Insertar línea
    public void insertar(LineaFactura l) throws SQLException {
        String sql = "INSERT INTO lineas_factura (idFactura, idProducto, cantidad) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, l.getIdFactura());
            ps.setInt(2, l.getIdProducto());
            ps.setInt(3, l.getCantidad());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    l.setIdLinea(rs.getInt(1));
                }
            }
        }
    }

    // Obtener línea por ID
    public LineaFactura obtenerPorId(int idLinea) throws SQLException {
        String sql = "SELECT * FROM lineas_factura WHERE idLinea = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idLinea);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearLinea(rs);
                }
            }
        }
        return null;
    }

    // Listar todas las líneas de una factura
    public List<LineaFactura> listarPorFactura(int idFactura) throws SQLException {
        List<LineaFactura> lista = new ArrayList<>();
        String sql = "SELECT * FROM lineas_factura WHERE idFactura = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idFactura);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearLinea(rs));
                }
            }
        }
        return lista;
    }

    // Actualizar línea
    public void actualizar(LineaFactura l) throws SQLException {
        String sql = "UPDATE lineas_factura SET idFactura=?, idProducto=?, cantidad=? WHERE idLinea=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, l.getIdFactura());
            ps.setInt(2, l.getIdProducto());
            ps.setInt(3, l.getCantidad());
            ps.setInt(4, l.getIdLinea());

            ps.executeUpdate();
        }
    }

    // Eliminar línea
    public void eliminar(int idLinea) throws SQLException {
        String sql = "DELETE FROM lineas_factura WHERE idLinea=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idLinea);
            ps.executeUpdate();
        }
    }

    // Mapear ResultSet a LineaFactura
    private LineaFactura mapearLinea(ResultSet rs) throws SQLException {
        return new LineaFactura(
            rs.getInt("idLinea"),
            rs.getInt("idFactura"),
            rs.getInt("idProducto"),
            rs.getInt("cantidad")
        );
    }
}

