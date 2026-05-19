package co.upc.ganado.entidades;

import co.upc.ganado.entidades.enums.EnumResultadoPalpacion;

/**
 * ENTIDAD ASOCIATIVA GANADO-PALPACION
 * Registra el resultado individual de una palpación para cada hembra,
 * incluyendo si está preñada, fecha de concepción y parto estimado.
 */
public class ResultadoPalpacion {
    private int idGanado;
    private int idPalpacion;
    private EnumResultadoPalpacion resultadoPalpacion;
    private String fechaConcepcionAprox; //NULLABLE
    private String fechaPartoEstimada; //NULLABLE
    
    //Constructor vacío. Para crear el objeto y luego setear atributo por atributo al leer datos del CSV.
    public ResultadoPalpacion() {}
    
    //Constructor completo.
    public ResultadoPalpacion(int idGanado, int idPalpacion, EnumResultadoPalpacion resultadoPalpacion, String fechaConcepcionAprox, String fechaPartoEstimada) {
        this.idGanado = idGanado;
        this.idPalpacion = idPalpacion;
        this.resultadoPalpacion = resultadoPalpacion;
        this.fechaConcepcionAprox = fechaConcepcionAprox;
        this.fechaPartoEstimada = fechaPartoEstimada;
    }
    
    //Constructor sin fechas.
    public ResultadoPalpacion(int idGanado, int idPalpacion, EnumResultadoPalpacion resultadoPalpacion) {
        this.idGanado = idGanado;
        this.idPalpacion = idPalpacion;
        this.resultadoPalpacion = resultadoPalpacion;
    }
    
    //GETTERS y SETTERS
    public int getIdGanado() {return idGanado;}
    public void setIdGanado(int idGanado) {this.idGanado = idGanado;}

    public int getIdPalpacion() {return idPalpacion;}
    public void setIdPalpacion(int idPalpacion) {this.idPalpacion = idPalpacion;}

    public EnumResultadoPalpacion getResultadoPalpacion() {return resultadoPalpacion;}
    public void setResultadoPalpacion(EnumResultadoPalpacion resultadoPalpacion) {this.resultadoPalpacion = resultadoPalpacion;}

    public String getFechaConcepcionAprox() {return fechaConcepcionAprox;}
    public void setFechaConcepcionAprox(String fechaConcepcionAprox) {this.fechaConcepcionAprox = fechaConcepcionAprox;}

    public String getFechaPartoEstimada() {return fechaPartoEstimada;}
    public void setFechaPartoEstimada(String fechaPartoEstimada) {this.fechaPartoEstimada = fechaPartoEstimada;}
    
    //MÉTODOS
    
    //Para mostrar en consola. Para pruebas de funcionamiento.
    @Override
    public String toString() {
        return "ResultadoPalpacion{" +
                "idGanado=" + idGanado +
                ", idPalpacion=" + idPalpacion +
                ", resultadoPalpacion=" + resultadoPalpacion +
                '}';
    }
}
