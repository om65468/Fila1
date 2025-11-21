package Modelo;

public class TipoEntidad {

    private int id;
    private String tipo; // cliente, proveedor, empresa

    public TipoEntidad(int id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "TipoEntidad{id=" + id + ", tipo='" + tipo + "'}";
    }
}

