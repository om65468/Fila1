package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoEntidadDAO {

    private Connection conn;

    public TipoEntidadDAO() {
        
        try {
            conn = ConexionBBDD.get();
        } catch (Exception e) {
            System.out.println("No funciona la conexioin de TipoEntidadDAO a la BBDD, mira el constructor");
            e.printStackTrace();
        }

    }

    // Insertar relación (asignar un tipo a una entidad)
    public boolean insertar(int idEntidad, int idTipo) {
        String sql = "INSERT INTO entidad_tipo_relacion (id_entidad, id_tipo) VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEntidad);
            stmt.setInt(2, idTipo);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar relación (quitar un tipo a una entidad)
    public boolean eliminar(int idEntidad, int idTipo) {
        String sql = "DELETE FROM entidad_tipo_relacion WHERE id_entidad=? AND id_tipo=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEntidad);
            stmt.setInt(2, idTipo);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener los tipos asociados a una entidad
    public List<Integer> obtenerTiposDeEntidad(int idEntidad) {

        String sql = "SELECT id_tipo FROM entidad_tipo_relacion WHERE id_entidad=?";
        List<Integer> lista = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEntidad);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(rs.getInt("id_tipo"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
