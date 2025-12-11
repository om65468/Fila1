package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LineaFacturaDAO {

    private Connection conn;

    public LineaFacturaDAO() {
        try {
            this.conn = ConexionBBDD.get();
        } catch (Exception e) {
            System.out.println("No funciona la conexioin de LineaFacturaDAO a la BBDD, mira el constructor");
            e.printStackTrace();
        }
    }

    // Insertar línea
    public void insertar(LineaFactura l) throws SQLException {
        String sql = "INSERT INTO lineas_factura (id_factura, id_producto, cantidad) VALUES (?, ?, ?)";
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
        String sql = "SELECT * FROM lineas_factura WHERE id_linea = ?";
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
        String sql = "SELECT * FROM lineas_factura WHERE id_factura = ?";
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
        String sql = "UPDATE lineas_factura SET id_factura=?, id_producto=?, cantidad=? WHERE id_linea=?";
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
        String sql = "DELETE FROM lineas_factura WHERE id_linea=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idLinea);
            ps.executeUpdate();
        }
    }

    // Mapear ResultSet a LineaFactura
    private LineaFactura mapearLinea(ResultSet rs) throws SQLException {
        return new LineaFactura(
            rs.getInt("id_linea"),
            rs.getInt("id_factura"),
            rs.getInt("id_producto"),
            rs.getInt("cantidad")
        );
    }
    
    
    public static class LineaCalculoDTO {
        public int idLinea;
        public int cantidad;
        public double pvp;
        public double iva; // Tasa de IVA del producto (ej: 0.21)

        public LineaCalculoDTO(int idLinea, int cantidad, double pvp, double iva) {
            this.idLinea = idLinea;
            this.cantidad = cantidad;
            this.pvp = pvp;
            this.iva = iva;
        }
    }
    
    public List<LineaCalculoDTO> obtenerDetallesParaCalculo(int idFactura) throws SQLException {
    List<LineaCalculoDTO> lista = new ArrayList<>();
    
    String sql = "SELECT lf.id_linea, lf.cantidad, p.pvp, p.iva " +
                 "FROM lineas_factura lf " +
                 "JOIN productos p ON lf.id_producto = p.id " +
                 "WHERE lf.id_factura = ?";
    
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, idFactura);
        
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                // p.pvp y p.iva son los campos clave
                lista.add(new LineaCalculoDTO(
                    rs.getInt("id_linea"),
                    rs.getInt("cantidad"),
                    rs.getDouble("pvp"),
                    rs.getDouble("iva")
                ));
            }
        }
    }
    return lista;
}
    
}
