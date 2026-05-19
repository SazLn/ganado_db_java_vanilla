package co.upc.ganado.entidades;
import co.upc.ganado.entidades.enums.EnumTipoPalpacion; //Para poder usar el enum.

/**
 *Es un procedimiento que se le hace a múltiples vacas en una misma fecha, es un
 * procedimiento que se repite cada cierto tiempo.
 * 
 * Con este se determina el estado reproductivo de cada vaca.
 */
public class Palpacion {
    private int idPalpacion;
    private String fechaPalpacion;
    private EnumTipoPalpacion tipoPalpacion;
    private String observaciones; //Opcional, NULLABLE
    
    //Constructor vacío. Para crear el objeto y luego setear atributo por atributo al leer datos del CSV.
    public Palpacion() {}
    
    //constructor completo
    public Palpacion(int idPalpacion, String fechaPalpacion, EnumTipoPalpacion tipoPalpacion, String observaciones) {
        this.idPalpacion = idPalpacion;
        this.fechaPalpacion = fechaPalpacion;
        this.tipoPalpacion = tipoPalpacion;
        this.observaciones = observaciones;
    }
    
    //Constructor sin observaciones
    public Palpacion(int idPalpacion, String fechaPalpacion, EnumTipoPalpacion tipoPalpacion) {
        this.idPalpacion = idPalpacion;
        this.fechaPalpacion = fechaPalpacion;
        this.tipoPalpacion = tipoPalpacion;
    }

    //GETTERS y SETTERS
    public int getIdPalpacion() {return idPalpacion;}
    public void setIdPalpacion(int idPalpacion) {this.idPalpacion = idPalpacion;}

    public String getFechaPalpacion() {return fechaPalpacion;}
    public void setFechaPalpacion(String fechaPalpacion) {this.fechaPalpacion = fechaPalpacion;}

    public EnumTipoPalpacion getTipoPalpacion() {return tipoPalpacion;}
    public void setTipoPalpacion(EnumTipoPalpacion tipoPalpacion) {this.tipoPalpacion = tipoPalpacion;}

    public String getObservaciones() {return observaciones;}
    public void setObservaciones(String observaciones) {this.observaciones = observaciones;}
    
    //MÉTODOS
    
    //Para mostrar en consola. Para pruebas de funcionamiento.
    @Override
    public String toString() {
        return "Palpacion{" +
                "idPalpacion=" + idPalpacion +
                ", fechaPalpacion='" + fechaPalpacion + '\'' +
                ", tipoPalpacion=" + tipoPalpacion +
                '}';
    }
}
