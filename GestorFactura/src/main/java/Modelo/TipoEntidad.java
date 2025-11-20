package Modelo;


import java.util.HashMap;
import java.util.Map;

// Esta clase está mal, he de cambiarla tanto aquí como en la base de datos.

public class TipoEntidad {

    private int id;
    private int tipo;

    private static final Map<Integer, String> NUM_TO_STRING = new HashMap<>();
    private static final Map<String, Integer> STRING_TO_NUM = new HashMap<>();

    static {
        NUM_TO_STRING.put(1, "empresa");
        NUM_TO_STRING.put(2, "cliente");
        NUM_TO_STRING.put(3, "proveedor");

        STRING_TO_NUM.put("empresa", 1);
        STRING_TO_NUM.put("cliente", 2);
        STRING_TO_NUM.put("proveedor", 3);}

    public TipoEntidad(int id, int tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public static String tipoANombre(int tipo) {
        return NUM_TO_STRING.get(tipo);
    }

    public static int nombreATipo(String nombre) {
        return STRING_TO_NUM.getOrDefault(nombre, 0); // 0 = desconocido
    }
}

