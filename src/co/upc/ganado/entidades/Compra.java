package co.upc.ganado.entidades;

import co.upc.ganado.entidades.enums.EnumTipoTransaccion;

/**
 * Una compra es un evento administrativo mediante el cual la finca adquiere
 * ganado. Puede ser verbal o documentada.
 */
public class Compra {
    private int idCompra;
    private String fechaCompra;
    private EnumTipoTransaccion tipoCompra;
    private String documentoReferencia; //Nullable porque en compras verbales no hay factura
    private double valorTotalCompra;
    private String responsableCompra;
    private String proveedor;
    
    //Constructor vacío. Para crear el objeto y luego setear atributo por atributo al leer datos del CSV.
    public Compra() {}
    
    //Constructor completo
    public Compra(int idCompra, String fechaCompra, EnumTipoTransaccion tipoCompra, String documentoReferencia, double valorTotalCompra, String responsableCompra, String proveedor) {
        this.idCompra = idCompra;
        this.fechaCompra = fechaCompra;
        this.tipoCompra = tipoCompra;
        this.documentoReferencia = documentoReferencia;
        this.valorTotalCompra = valorTotalCompra;
        this.responsableCompra = responsableCompra;
        this.proveedor = proveedor;
    }
    
    //Constructor sin documento de referencia. Para compras verbales.
    public Compra(int idCompra, String fechaCompra, EnumTipoTransaccion tipoCompra, double valorTotalCompra, String responsableCompra, String proveedor) {
        this.idCompra = idCompra;
        this.fechaCompra = fechaCompra;
        this.tipoCompra = tipoCompra;
        this.valorTotalCompra = valorTotalCompra;
        this.responsableCompra = responsableCompra;
        this.proveedor = proveedor;
    }

    
    //GETTERS y SETTERS
    public int getIdCompra() {return idCompra;}
    public void setIdCompra(int idCompra) {this.idCompra = idCompra;}

    public String getFechaCompra() {return fechaCompra;}
    public void setFechaCompra(String fechaCompra) {this.fechaCompra = fechaCompra;}

    public EnumTipoTransaccion getTipoCompra() {return tipoCompra;}
    public void setTipoCompra(EnumTipoTransaccion tipoCompra) {this.tipoCompra = tipoCompra;}

    public String getDocumentoReferencia() {return documentoReferencia;}
    public void setDocumentoReferencia(String documentoReferencia) {this.documentoReferencia = documentoReferencia;}

    public double getValorTotalCompra() {return valorTotalCompra;}
    public void setValorTotalCompra(double valorTotalCompra) {this.valorTotalCompra = valorTotalCompra;}

    public String getResponsableCompra() {return responsableCompra;}
    public void setResponsableCompra(String responsableCompra) {this.responsableCompra = responsableCompra;}
    
    public String getProveedor() {return proveedor;}
    public void setProveedor(String proveedor) {this.proveedor = proveedor;}
    
    //MÉTODOS
    
    //Para mostrar en consola. Para pruebas de funcionamiento.
    @Override
    public String toString() {
        return "Compra{" +
                "idCompra = " + idCompra +
                ", fechaCompra = '" + fechaCompra + '\'' +
                ", valorTotalCompra = " + valorTotalCompra +
                ", proveedor = '" + proveedor + '\'' +
                '}';
    }
}
