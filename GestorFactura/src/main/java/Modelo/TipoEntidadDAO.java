package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoEntidadDAO {

    private Connection conn;

    public TipoEntidadDAO(Connection conn) {
        this.conn = conn;
    }

    // Insertar tipo de entidad
    public void insertar(TipoEntidad t) throws SQLException {
        String sql = "INSERT INTO tipo_entidad (tipo) VALUES (?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, TipoEntidad.tipoANombre(t.getTipo()));
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    t.setId(rs.getInt(1));
                }
            }
        }
    }

    // Obtener por ID
    public TipoEntidad obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM tipo_entidad WHERE id_tipo=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new TipoEntidad(rs.getInt("id_tipo"),
                            TipoEntidad.nombreATipo(rs.getString("tipo")));
                }
            }
        }
        return null;
    }

    // Listar todos
    public List<TipoEntidad> listarTodos() throws SQLException {
        List<TipoEntidad> lista = new ArrayList<>();
        String sql = "SELECT * FROM tipo_entidad";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new TipoEntidad(rs.getInt("id_tipo"),
                        TipoEntidad.nombreATipo(rs.getString("tipo"))));
            }
        }
        return lista;
    }

    // Actualizar
    public void actualizar(TipoEntidad t) throws SQLException {
        String sql = "UPDATE tipo_entidad SET tipo=? WHERE id_tipo=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, TipoEntidad.tipoANombre(t.getTipo()));
            ps.setInt(2, t.getId());
            ps.executeUpdate();
        }
    }

    // Eliminar
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM tipo_entidad WHERE id_tipo=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}

