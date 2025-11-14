package Modelo;

public class Direccion {
    private int id;
    private String calle;
    private String codPost;
    private String ciudad;
    private String telfono;

    public Direccion(int id, String calle, String codPost, String ciudad, String telfono) {
        this.id = id;
        this.calle = calle;
        this.codPost = codPost;
        this.ciudad = ciudad;
        this.telfono = telfono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    
    
}
