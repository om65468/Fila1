package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntidadDAO {

    private Connection conn;

    public EntidadDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean insertar(Entidad e) {
        String sql = "INSERT INTO entidades (nombre, nif, direccion, telefono, email) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, e.getNombre());
            stmt.setString(2, e.getNif());

            // Unimos los campos porque tu SQL usa una sola columna
            String direccion = e.getCalle() + " " + e.getCodPost() + " " + e.getCiudad();
            stmt.setString(3, direccion);

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

            String direccion = e.getCalle() + " " + e.getCodPost() + " " + e.getCiudad();
            stmt.setString(3, direccion);

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
                //tipos.add(new TipoEntidad(rs.getInt("id_tipo"), rs.getString("tipo")));
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

}
