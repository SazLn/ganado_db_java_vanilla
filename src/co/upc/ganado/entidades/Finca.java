package co.upc.ganado.entidades;

/**
 * Representa una finca o predio donde se ubica el ganado.
 */
public class Finca {

    private int idFinca;
    private String nombreFinca;
    private String ubicacion;

    //Constructor vacío. Para crear el objeto y luego setear atributo por atributo al leer datos del CSV.
    public Finca() {
    }

    //Constructor completo
    public Finca(int idFinca, String nombreFinca, String ubicacion) {
        this.idFinca = idFinca;
        this.nombreFinca = nombreFinca;
        this.ubicacion = ubicacion;
    }

    //GETTERS Y SETTERS
    public int getIdFinca() {return idFinca;}
    public void setIdFinca(int idFinca) {this.idFinca = idFinca;}

    public String getNombreFinca() {return nombreFinca;}
    public void setNombreFinca(String nombreFinca) {this.nombreFinca = nombreFinca;}

    public String getUbicacion() {return ubicacion;}
    public void setUbicacion(String ubicacion) {this.ubicacion = ubicacion;}

    
    //MÉTODOS
    
    
    //Para pruebas por consola.
    @Override
    public String toString() {
        return "Finca{" +
                "idFinca=" + idFinca +
                ", nombreFinca='" + nombreFinca + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                '}';
    }
}
