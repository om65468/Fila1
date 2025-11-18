package Modelo;

public class Factura {
    private int id;
    private char tipo;
    private int numFactura;
    private String fechaEmision;
    private int idSecundario;
    private String concepto;
    private double base;
    private double iva;
    private double total;
    private String estado;
    private String observaciones;

    public Factura(int id, char tipo, int numFactura, int idSecundario, String concepto, double base, double iva, double total, String estado, String observaciones) {
        this.id = id;
        this.tipo = tipo;
        this.numFactura = numFactura;
        //La fecha de emisión será el día de hoy.
        this.idSecundario = idSecundario;
        this.concepto = concepto;
        this.base = base;
        this.iva = iva;
        this.total = total;
        this.estado = estado;
        this.observaciones = observaciones;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    public int getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(int numFactura) {
        this.numFactura = numFactura;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public int getIdSecundario() {
        return idSecundario;
    }

    public void setIdSecundario(int idSecundario) {
        this.idSecundario = idSecundario;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public double getBase() {
        return base;
    }

    public void setBase(double base) {
        this.base = base;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
