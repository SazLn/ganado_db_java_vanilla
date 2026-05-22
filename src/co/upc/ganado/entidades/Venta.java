package co.upc.ganado.entidades;
import co.upc.ganado.entidades.enums.EnumTipoTransaccion;

/**
 * Evento administrativo mediante el cual la finca vende ganado. Esto implica la
 * salida de ganado. Puede incluir uno o varios animales.
 */
public class Venta {
    private int id_venta;
    private String fechaVenta;
    private EnumTipoTransaccion tipoVenta;
    private String comprador;
    private String documentoReferencia; //NULLABLE si es verbal.
    private double valorTotalVenta;
    private String metodoPago;
    private String responsableVenta;
    
    //Constructor vacío. Para crear el objeto y luego setear atributo por atributo al leer datos del CSV.
    public Venta() {}
    
    //Constructor completo.
    public Venta(int id_venta, String fechaVenta, EnumTipoTransaccion tipoVenta, 
                 String comprador, String documentoReferencia, 
                 double valorTotalVenta, 
                 String metodoPago, String responsableVenta) {
        
        this.id_venta = id_venta;
        this.fechaVenta = fechaVenta;
        this.tipoVenta = tipoVenta;
        this.comprador = comprador;
        this.documentoReferencia = documentoReferencia;
        this.valorTotalVenta = valorTotalVenta;
        this.metodoPago = metodoPago;
        this.responsableVenta = responsableVenta;
    }
    
    //Constructor sin documento de referencia.

    public Venta(int id_venta, String fechaVenta, 
                 EnumTipoTransaccion tipoVenta, String comprador, 
                 double valorTotalVenta, String metodoPago, String responsableVenta) {
        
        this.id_venta = id_venta;
        this.fechaVenta = fechaVenta;
        this.tipoVenta = tipoVenta;
        this.comprador = comprador;
        this.valorTotalVenta = valorTotalVenta;
        this.metodoPago = metodoPago;
        this.responsableVenta = responsableVenta;
    }
    
    //GETTERS y SETTERS
    public int getIdVenta() {return id_venta;}
    public void setIdVenta(int id_venta) {this.id_venta = id_venta;}

    public String getFechaVenta() {return fechaVenta;}
    public void setFechaVenta(String fechaVenta) {this.fechaVenta = fechaVenta;}

    public EnumTipoTransaccion getTipoVenta() {return tipoVenta;}
    public void setTipoVenta(EnumTipoTransaccion tipoVenta) {this.tipoVenta = tipoVenta;}

    public String getComprador() {return comprador;}
    public void setComprador(String comprador) {this.comprador = comprador;}

    public String getDocumentoReferencia() {return documentoReferencia;}
    public void setDocumentoReferencia(String documentoReferencia) {this.documentoReferencia = documentoReferencia;}

    public double getValorTotalVenta() {return valorTotalVenta;}
    public void setValorTotalVenta(double valorTotalVenta) {this.valorTotalVenta = valorTotalVenta;}

    public String getMetodoPago() {return metodoPago;}
    public void setMetodoPago(String metodoPago) {this.metodoPago = metodoPago;}

    public String getResponsableVenta() {return responsableVenta;}
    public void setResponsableVenta(String responsableVenta) {this.responsableVenta = responsableVenta;}
    
    //MÉTODOS
    
    //Para mostrar en consola. Para pruebas de funcionamiento.
    @Override
    public String toString() {
        return "Venta{" +
                "id_venta=" + id_venta +
                ", fechaVenta='" + fechaVenta + '\'' +
                ", comprador='" + comprador + '\'' +
                ", valorTotalVenta=" + valorTotalVenta +
                '}';
    }
}
