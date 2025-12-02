package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpresaEntidadRelacionDAO {

    private Connection conn;

    public EmpresaEntidadRelacionDAO() {
        try {
            this.conn = ConexionBBDD.get();
        } catch (Exception e) {
            System.out.println("No funciona la conexioin de EntidadDao a la BBDD, mira el constructor");
            e.printStackTrace();
        }
    }

    /**
     * Inserta la relación empresa - entidad
     */
    public boolean insertarRelacion(int idEmpresa, int idEntidad) {
        String sql = "INSERT INTO empresa_entidad_relacion (id_empresa, id_entidad) VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEmpresa);
            stmt.setInt(2, idEntidad);

            return stmt.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina una relación concreta
     */
    public boolean eliminarRelacion(int idEmpresa, int idEntidad) {
        String sql = "DELETE FROM empresa_entidad_relacion WHERE id_empresa = ? AND id_entidad = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEmpresa);
            stmt.setInt(2, idEntidad);

            return stmt.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Lista todas las entidades asociadas a una empresa
     */
    public List<Entidad> obtenerEntidadesDeEmpresa(int idEmpresa) {
        List<Entidad> lista = new ArrayList<>();

        String sql = "SELECT e.* FROM entidades e JOIN empresa_entidad_relacion rel ON e.id_entidad = rel.id_entidad  WHERE rel.id_empresa = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEmpresa);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                String direccion = rs.getString("direccion");
                String[] partes = direccion != null ? direccion.split(" ") : new String[0];

                String calle = partes.length > 0 ? partes[0] : "";
                String codPost = partes.length > 1 ? partes[1] : "";
                String ciudad = partes.length > 2 ? partes[2] : "";

                Entidad e = new Entidad(
                        rs.getInt("id_entidad"),
                        rs.getString("nombre"),
                        rs.getString("nif"),
                        calle, codPost, ciudad,
                        rs.getString("email"),
                        rs.getString("telefono")
                );

                lista.add(e);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lista;
    }
}
