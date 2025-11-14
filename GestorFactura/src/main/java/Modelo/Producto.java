package Modelo;

public class Producto {
    private int id;
    private String descripcion;
    private String refProveedor;
    private int idProveedor;
    private double coste;
    private double pvp;
    private double iva;
    private int stock;

    public Producto(int id, String descripcion, String refProveedor, int idProveedor, double coste, double pvp, double iva, int stock) {
        this.id = id;
        this.descripcion = descripcion;
        this.refProveedor = refProveedor;
        this.idProveedor = idProveedor;
        this.coste = coste;
        this.pvp = pvp;
        this.iva = iva;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRefProveedor() {
        return refProveedor;
    }

    public void setRefProveedor(String refProveedor) {
        this.refProveedor = refProveedor;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public double getCoste() {
        return coste;
    }

    public void setCoste(double coste) {
        this.coste = coste;
    }

    public double getPvp() {
        return pvp;
    }

    public void setPvp(double pvp) {
        this.pvp = pvp;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
    
    
    
}
