package Modelo;

public class Entidad {
    private int id;
    private char tipo;
    private String nombre;
    private String nif;
    private String email;
    private String telefono;

    public Entidad(int id, char tipo, String nombre, String nif, String email, String telefono) {
        this.id = id;
        this.tipo = tipo;
        this.nombre = nombre;
        this.nif = nif;
        this.email = email;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public char getTipo() {
        return tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNif() {
        return nif;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
       
}
