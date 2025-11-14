package Modelo;

public class Entidad {
    
    private int id;
    private String nombre;
    private String nif;
    private Direccion direccion;
    private String email;
    private String telefono;
    private int tipo;
    
    public Entidad(int id, String nombre, String nif, Direccion direccion, String email, String telefono, int tipo) {
        this.id = id;
        this.nombre = nombre;
        this.nif = nif;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNif() {
        return nif;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public int getTipo(){
        return tipo;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void settipo(int tipo){
        this.tipo = tipo;
    }
       
}
