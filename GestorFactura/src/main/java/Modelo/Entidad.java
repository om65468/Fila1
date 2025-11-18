package Modelo;

public class Entidad {
    
    private int id;
    private String nombre;
    private String nif;
    private String calle;
    private String codPost;
    private String ciudad;
    private String telfono;
    private String email;
    private String telefono;


    public Entidad(int id, String nombre, String nif, String calle, String codPost, String ciudad, String telfono, String email, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.nif = nif;
        this.calle = calle;
        this.codPost = codPost;
        this.ciudad = ciudad;
        this.telfono = telfono;
        this.email = email;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCodPost() {
        return codPost;
    }

    public void setCodPost(String codPost) {
        this.codPost = codPost;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getTelfono() {
        return telfono;
    }

    public void setTelfono(String telfono) {
        this.telfono = telfono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

        
}
