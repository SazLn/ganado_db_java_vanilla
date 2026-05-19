package co.upc.ganado.entidades;

/**
 * ENTIDAD ASOCIATIVA VENTA-GANADO
 * Registra de manera individual qué animales se vendieron en cada venta,
 * su precio, peso al momento de la venta y observaciones.
 */
public class DetalleVenta {
    private int idVenta;
    private int idganado;
    private double precioIndividual;
    private double pesoMomentoVenta; //en kg.
    private String observaciones; //NULLABLE
    
    //Constructor vacío. Para crear el objeto y luego setear atributo por atributo al leer datos del CSV.
    public DetalleVenta() {}
    
    //Constructor completo
    public DetalleVenta(int idVenta, int idganado, double precioIndividual, double pesoMomentoVenta, String observaciones) {
        this.idVenta = idVenta;
        this.idganado = idganado;
        this.precioIndividual = precioIndividual;
        this.pesoMomentoVenta = pesoMomentoVenta;
        this.observaciones = observaciones;
    }
    
    //Constructor sin observaciones.
    public DetalleVenta(int idVenta, int idganado, double precioIndividual, double pesoMomentoVenta) {
        this.idVenta = idVenta;
        this.idganado = idganado;
        this.precioIndividual = precioIndividual;
        this.pesoMomentoVenta = pesoMomentoVenta;
    }
    
    //GETTERS y SETTERS
    public int getIdVenta() {return idVenta;}
    public void setIdVenta(int idVenta) {this.idVenta = idVenta;}

    public int getIdganado() {return idganado;}
    public void setIdganado(int idganado) {this.idganado = idganado;}

    public double getPrecioIndividual() {return precioIndividual;}
    public void setPrecioIndividual(double precioIndividual) {this.precioIndividual = precioIndividual;}

    public double getPesoMomentoVenta() {return pesoMomentoVenta;}
    public void setPesoMomentoVenta(double pesoMomentoVenta) {this.pesoMomentoVenta = pesoMomentoVenta;}

    public String getObservaciones() {return observaciones;}
    public void setObservaciones(String observaciones) {this.observaciones = observaciones;}
    
    //MÉTODOS
    
    //Para mostrar en consola. Para pruebas de funcionamiento.
    @Override
    public String toString() {
        return "DetalleVenta{" +
                "idVenta=" + idVenta +
                ", idganado=" + idganado +
                ", precioIndividual=" + precioIndividual +
                ", pesoMomentoVenta=" + pesoMomentoVenta +
                '}';
    }
}
