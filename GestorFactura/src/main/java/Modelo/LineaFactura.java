package Modelo;

import java.sql.SQLException;

public class LineaFactura {
   
    private int idLinea;
    private int idFactura;
    private int idProducto;
    private int cantidad;

    public LineaFactura(int id, int idFactura, int idProducto, int cantidad) {
        this.idLinea = id;
        this.idFactura = idFactura;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
    }

    public int getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(int idLinea) {
        this.idLinea = idLinea;
    }
    
    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    public double getTotal(){
        ProductoDAO pDAO = new ProductoDAO();
        try{
        Producto p = pDAO.obtenerPorId(idProducto);
            return cantidad * p.getPvp();
        }catch(SQLException e){
            return cantidad;
        }
    }
}
