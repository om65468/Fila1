package Modelo;

public class EmpresaEntidadRelacion {
    private int idEmpresa;
    private int idEntidad;

    public EmpresaEntidadRelacion() {}

    public EmpresaEntidadRelacion(int idEmpresa, int idEntidad) {
        this.idEmpresa = idEmpresa;
        this.idEntidad = idEntidad;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public int getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(int idEntidad) {
        this.idEntidad = idEntidad;
    }
}
