package co.upc.ganado.entidades;

import co.upc.ganado.entidades.enums.EnumMotivoTraslado;

/**
 * Cada cierto tiempo se hace un traslado a una determinada cantidad de vacas y toros
 * de una finca a otra.
 */
public class Traslado {
    private int idTraslado;
    private String fechaTraslado;
    private EnumMotivoTraslado motivoTraslado;
    private String medioTransporte; //"Camion" por defecto.
    private String responsableTraslado; //NULLABLE
    private double costoTraslado;
    
    //Relación con las fincas. Ambos id's deben ser diferentes.
    private int idFincaOrigen;
    private int idFincaDestino;
    
    //Constructor vacío. Para crear el objeto y luego setear atributo por atributo al leer datos del CSV.
    public Traslado() {}
    
    //Constructor completo.
    public Traslado(int idTraslado, String fechaTraslado, 
                    EnumMotivoTraslado motivoTraslado, String medioTransporte, 
                    String responsableTraslado, double costoTraslado, 
                    int idFincaOrigen, int idFincaDestino) {
        
        this.idTraslado = idTraslado;
        this.fechaTraslado = fechaTraslado;
        this.motivoTraslado = motivoTraslado;
        this.medioTransporte = medioTransporte;
        this.responsableTraslado = responsableTraslado;
        this.costoTraslado = costoTraslado;
        this.idFincaOrigen = idFincaOrigen;
        this.idFincaDestino = idFincaDestino;
    }
    
    //Constructor sin responsable de traslado y con valor del transporte por defecto.
    public Traslado(int idTraslado, String fechaTraslado, 
                    EnumMotivoTraslado motivoTraslado, double costoTraslado, 
                    int idFincaOrigen, int idFincaDestino) {
        
        this.idTraslado = idTraslado;
        this.fechaTraslado = fechaTraslado;
        this.motivoTraslado = motivoTraslado;
        this.medioTransporte = "Camion";
        this.costoTraslado = costoTraslado;
        this.idFincaOrigen = idFincaOrigen;
        this.idFincaDestino = idFincaDestino;
    }
    
    //GETTERS y SETTERS
    public int getIdTraslado() {return idTraslado;}
    public void setIdTraslado(int idTraslado) {this.idTraslado = idTraslado;}

    public String getFechaTraslado() {return fechaTraslado;}
    public void setFechaTraslado(String fechaTraslado) {this.fechaTraslado = fechaTraslado;}

    public EnumMotivoTraslado getMotivoTraslado() {return motivoTraslado;}
    public void setMotivoTraslado(EnumMotivoTraslado motivoTraslado) {this.motivoTraslado = motivoTraslado;}

    public String getMedioTransporte() {return medioTransporte;}
    public void setMedioTransporte(String medioTransporte) {this.medioTransporte = medioTransporte;}

    public String getResponsableTraslado() {return responsableTraslado;}
    public void setResponsableTraslado(String responsableTraslado) {this.responsableTraslado = responsableTraslado;}

    public double getCostoTraslado() {return costoTraslado;}
    public void setCostoTraslado(double costoTraslado) {this.costoTraslado = costoTraslado;}

    public int getIdFincaOrigen() {return idFincaOrigen;}
    public void setIdFincaOrigen(int idFincaOrigen) {this.idFincaOrigen = idFincaOrigen;}

    public int getIdFincaDestino() {return idFincaDestino;}
    public void setIdFincaDestino(int idFincaDestino) {this.idFincaDestino = idFincaDestino;}
    
    //MÉTODOS
    
    //Para mostrar en consola. Para pruebas de funcionamiento.
    @Override
    public String toString() {
        return "Traslado{" +
                "idTraslado=" + idTraslado +
                ", fechaTraslado='" + fechaTraslado + '\'' +
                ", motivoTraslado=" + motivoTraslado +
                ", idFincaOrigen=" + idFincaOrigen +
                ", idFincaDestino=" + idFincaDestino +
                '}';
    }
}
