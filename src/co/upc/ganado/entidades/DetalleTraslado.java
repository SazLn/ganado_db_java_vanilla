package co.upc.ganado.entidades;

/**
 * ENTIDAD ASOCIATIVA GANADO-TRASLADO
 * sirve para registrar de manera individual qué animales participaron en cada
 * traslado entre fincas, permitiendo llevar un historial completo de movimientos de
 * cada vaca o toro.
 */
public class DetalleTraslado {
    private int idTraslado;
    private int idGanado;
    private String observaciones; //NULLABLE
    
    //Constructor vacío. Para crear el objeto y luego setear atributo por atributo al leer datos del CSV.
    public DetalleTraslado() {}
    
    //Constructor completo.
    public DetalleTraslado(int idTraslado, int idGanado, String observaciones) {
        this.idTraslado = idTraslado;
        this.idGanado = idGanado;
        this.observaciones = observaciones;
    }
    
    //Constructor sin observaciones.
    public DetalleTraslado(int idTraslado, int idGanado) {
        this.idTraslado = idTraslado;
        this.idGanado = idGanado;
    }
    
    //GETTERS y SETTERS
    public int getIdTraslado() {return idTraslado;}
    public void setIdTraslado(int idTraslado) {this.idTraslado = idTraslado;}

    public int getIdGanado() {return idGanado;}
    public void setIdGanado(int idGanado) {this.idGanado = idGanado;}

    public String getObservaciones() {return observaciones;}
    public void setObservaciones(String observaciones) {this.observaciones = observaciones;}
    
    //MÉTODOS
    
    //Para mostrar en consola. Para pruebas de funcionamiento.
    @Override
    public String toString() {
        return "DetalleTraslado{" +
                "idTraslado=" + idTraslado +
                ", idGanado=" + idGanado +
                '}';
    }
}
