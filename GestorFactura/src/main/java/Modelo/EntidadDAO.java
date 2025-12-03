package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntidadDAO {

    private Connection conn;

    public EntidadDAO() {
        try {
            this.conn = ConexionBBDD.get();
        } catch (Exception e) {
            System.out.println("No funciona la conexioin de EntidadDao a la BBDD, mira el constructor");
            e.printStackTrace();
        }
    }

    public boolean insertar(Entidad e) {
        String sql = "INSERT INTO entidades (nombre, nif, direccion, telefono, email) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, e.getNombre());
            stmt.setString(2, e.getNif());
            stmt.setString(3, e.getDireccionCompleta());
            stmt.setString(4, e.getTelefono());
            stmt.setString(5, e.getEmail());

            return stmt.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Entidad e) {
        String sql = "UPDATE entidades SET nombre=?, nif=?, direccion=?, telefono=?, email=? WHERE id_entidad=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, e.getNombre());
            stmt.setString(2, e.getNif());
            stmt.setString(3, e.getDireccionCompleta());
            stmt.setString(4, e.getTelefono());
            stmt.setString(5, e.getEmail());
            stmt.setInt(6, e.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // BORRAR ENTIDAD
    public boolean eliminar(int id) {
        String sql = "DELETE FROM entidades WHERE id_entidad=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Entidad buscarPorNif(String nif) {
        String sql = "SELECT * FROM entidades WHERE nif = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nif);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String direccion = rs.getString("direccion"); // completa
                String email = rs.getString("email");
                String telefono = rs.getString("telefono");

                return new Entidad(
                        rs.getInt("id_entidad"),
                        rs.getString("nombre"),
                        rs.getString("nif"),
                        "", // calle si quieres separarlo después
                        "", // codPost
                        "", // ciudad
                        email,
                        telefono
                );
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    // BUSCAR POR ID
    public Entidad buscarPorId(int id) {
        String sql = "SELECT * FROM entidades WHERE id_entidad=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                // Tendrás que separar la dirección al revés si lo necesitas
                String direccion = rs.getString("direccion");
                String[] partes = direccion.split(" ");

                String calle = partes.length > 0 ? partes[0] : "";
                String codPost = partes.length > 1 ? partes[1] : "";
                String ciudad = partes.length > 2 ? partes[2] : "";

                return new Entidad(
                        rs.getInt("id_entidad"),
                        rs.getString("nombre"),
                        rs.getString("nif"),
                        calle,
                        codPost,
                        ciudad,
                        rs.getString("email"),
                        rs.getString("telefono")
                );
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    // LISTAR TODAS LAS ENTIDADES
    public List<Entidad> listarTodas() {
        List<Entidad> lista = new ArrayList<>();
        String sql = "SELECT * FROM entidades";

        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                String direccion = rs.getString("direccion");
                String[] partes = direccion.split(" ");

                String calle = partes.length > 0 ? partes[0] : "";
                String codPost = partes.length > 1 ? partes[1] : "";
                String ciudad = partes.length > 2 ? partes[2] : "";

                Entidad e = new Entidad(
                        rs.getInt("id_entidad"),
                        rs.getString("nombre"),
                        rs.getString("nif"),
                        calle,
                        codPost,
                        ciudad,
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

    // Obtener todos los tipos de una entidad
    public List<TipoEntidad> getTiposDeEntidad(int idEntidad) {
        List<TipoEntidad> tipos = new ArrayList<>();
        String sql = "SELECT t.id_tipo, t.tipo FROM tipo_entidad t JOIN entidad_tipo_relacion etr ON t.id_tipo = etr.id_tipo WHERE etr.id_entidad = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEntidad);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tipos.add(new TipoEntidad(rs.getInt("id_tipo"), rs.getString("tipo")));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return tipos;
    }

// Asignar un tipo a una entidad
    public boolean asignarTipo(int idEntidad, int idTipo) {
        String sql = "INSERT INTO entidad_tipo_relacion (id_entidad, id_tipo) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEntidad);
            stmt.setInt(2, idTipo);
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

// Quitar un tipo de una entidad
    public boolean quitarTipo(int idEntidad, int idTipo) {
        String sql = "DELETE FROM entidad_tipo_relacion  WHERE id_entidad = ? AND id_tipo = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEntidad);
            stmt.setInt(2, idTipo);
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<Entidad> obtenerEmpresas() {
        List<Entidad> lista = new ArrayList<>();

        String sql
                = "SELECT e.* FROM entidades e "
                + "JOIN entidad_tipo_relacion rel ON e.id_entidad = rel.id_entidad "
                + "JOIN tipo_entidad t ON t.id_tipo = rel.id_tipo "
                + "WHERE t.tipo = 'empresa'";

        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

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
                        calle,
                        codPost,
                        ciudad,
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
