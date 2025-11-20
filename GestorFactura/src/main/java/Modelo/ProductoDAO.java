package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    private Connection conn;

    public ProductoDAO(Connection conn) {
        this.conn = conn;
    }

    // Insertar producto
    public void insertar(Producto p) throws SQLException {
        String sql = "INSERT INTO productos (descripcion, refProveedor, idProveedor, coste, pvp, iva, stock) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getDescripcion());
            ps.setString(2, p.getRefProveedor());
            ps.setInt(3, p.getIdProveedor());
            ps.setDouble(4, p.getCoste());
            ps.setDouble(5, p.getPvp());
            ps.setDouble(6, p.getIva());
            ps.setInt(7, p.getStock());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    p.setId(rs.getInt(1));
                }
            }
        }
    }

    // Obtener producto por ID
    public Producto obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM productos WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearProducto(rs);
                }
            }
        }
        return null;
    }

    // Listar todos los productos
    public List<Producto> listarTodos() throws SQLException {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearProducto(rs));
            }
        }
        return lista;
    }

    // Actualizar producto
    public void actualizar(Producto p) throws SQLException {
        String sql = "UPDATE productos SET descripcion=?, refProveedor=?, idProveedor=?, coste=?, pvp=?, iva=?, stock=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getDescripcion());
            ps.setString(2, p.getRefProveedor());
            ps.setInt(3, p.getIdProveedor());
            ps.setDouble(4, p.getCoste());
            ps.setDouble(5, p.getPvp());
            ps.setDouble(6, p.getIva());
            ps.setInt(7, p.getStock());
            ps.setInt(8, p.getId());

            ps.executeUpdate();
        }
    }

    // Eliminar producto
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM productos WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // Mapear ResultSet a Producto
    private Producto mapearProducto(ResultSet rs) throws SQLException {
        return new Producto(
            rs.getInt("id"),
            rs.getString("descripcion"),
            rs.getString("refProveedor"),
            rs.getInt("idProveedor"),
            rs.getDouble("coste"),
            rs.getDouble("pvp"),
            rs.getDouble("iva"),
            rs.getInt("stock")
        );
    }
}

