package co.upc.ganado.entidades;

/**
 * ENTIDAD ASOCIATIVA COMPRA-GANADO
 * Registra de manera individual qué animales se adquirieron en cada compra,
 * su precio individual y observaciones.
 */
public class DetalleCompra {
    private int idCompra;
    private int idGanado;
    private double precioIndividual;
    private String observaciones; //NULLABLE
    
    //Constructor vacío. Para crear el objeto y luego setear atributo por atributo al leer datos del CSV.
    public DetalleCompra() {}
    
    //Constructor completo.
    public DetalleCompra(int idCompra, int idGanado, double precioIndividual, String observaciones) {
        this.idCompra = idCompra;
        this.idGanado = idGanado;
        this.precioIndividual = precioIndividual;
        this.observaciones = observaciones;
    }
    
    //Constructor sin observaciones.
    public DetalleCompra(int idCompra, int idGanado, double precioIndividual) {
        this.idCompra = idCompra;
        this.idGanado = idGanado;
        this.precioIndividual = precioIndividual;
    }
    
    //GETTERS y SETTERS
    public int getIdCompra() {return idCompra;}
    public void setIdCompra(int idCompra) {this.idCompra = idCompra;}

    public int getIdGanado() {return idGanado;}
    public void setIdGanado(int idGanado) {this.idGanado = idGanado;}

    public double getPrecioIndividual() {return precioIndividual;}
    public void setPrecioIndividual(double precioIndividual) {this.precioIndividual = precioIndividual;}

    public String getObservaciones() {return observaciones;}
    public void setObservaciones(String observaciones) {this.observaciones = observaciones;}
    
    //MÉTODOS
    
    //Para mostrar en consola. Para pruebas de funcionamiento.
    @Override
    public String toString() {
        return "DetalleCompra{" +
                "idCompra=" + idCompra +
                ", idGanado=" + idGanado +
                ", precioIndividual=" + precioIndividual +
                '}';
    }
}
