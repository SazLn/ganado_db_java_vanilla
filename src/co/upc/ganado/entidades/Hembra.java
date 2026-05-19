package co.upc.ganado.entidades;

import co.upc.ganado.entidades.enums.EnumEstadoReproductivo;
import co.upc.ganado.entidades.enums.EnumEstadoSalud;
import co.upc.ganado.entidades.enums.EnumEstadoVida;
import co.upc.ganado.entidades.enums.EnumMotivoSalida;
import co.upc.ganado.entidades.enums.EnumSexo;

/**
 *Representa un bovino hembra. Hereda los atributos de Ganado.
 *Incluye información sobre su estado reproductivo e historial de partos.
 */
public class Hembra extends Ganado {
    //Atributos propios de la clase.
    private EnumEstadoReproductivo estadoReproductivoActual;
    private String fechaUltimoParto; //NULLABLE por si es vaca de vientre.
    private int numeroPartos; //0 por defecto.
    private boolean aptaParaReproduccion; //'false' por defecto.
    
    
    //Constructor vacío.
    public Hembra() {}
    
    //Constructor completo. Atributos propios y heredados.
    public Hembra(EnumEstadoReproductivo estadoReproductivoActual, 
            String fechaUltimoParto, int numeroPartos, 
            boolean aptaParaReproduccion, int idGanado, 
            String numeroMarca, String fechaNacimiento, 
            EnumSexo tipoSexo, double peso, 
            EnumEstadoSalud estadoSalud, EnumEstadoVida estadoVida, 
            String fechaSalida, EnumMotivoSalida motivoSalida, 
            int idFinca, int idMadre) {
        
        super(idGanado, numeroMarca, fechaNacimiento, tipoSexo, peso, estadoSalud, estadoVida, fechaSalida, motivoSalida, idFinca, idMadre);
        this.estadoReproductivoActual = estadoReproductivoActual;
        this.fechaUltimoParto = fechaUltimoParto;
        this.numeroPartos = numeroPartos;
        this.aptaParaReproduccion = aptaParaReproduccion;
    }
    
     /*Constructor sin:
      - Fecha de nacimiento
      - Fecha de salida
      - Motivo de salida
      Y con el valor del id de la madre por defecto.
    */
    public Hembra(EnumEstadoReproductivo estadoReproductivoActual, String fechaUltimoParto, 
            int numeroPartos, boolean aptaParaReproduccion, 
            int idGanado, String numeroMarca, 
            EnumSexo tipoSexo, double peso, 
            EnumEstadoSalud estadoSalud, int idFinca) {
        
        super(idGanado, numeroMarca, tipoSexo, peso, estadoSalud, idFinca);
        this.estadoReproductivoActual = estadoReproductivoActual;
        this.fechaUltimoParto = fechaUltimoParto;
        this.numeroPartos = numeroPartos;
        this.aptaParaReproduccion = aptaParaReproduccion;
    }
    
    //GETTERS y SETTERS
    public EnumEstadoReproductivo getEstadoReproductivoActual() {return estadoReproductivoActual;}
    public void setEstadoReproductivoActual(EnumEstadoReproductivo estadoReproductivoActual) {this.estadoReproductivoActual = estadoReproductivoActual;}

    public String getFechaUltimoParto() {return fechaUltimoParto;}
    public void setFechaUltimoParto(String fechaUltimoParto) {this.fechaUltimoParto = fechaUltimoParto;}

    public int getNumeroPartos() {return numeroPartos;}
    public void setNumeroPartos(int numeroPartos) {this.numeroPartos = numeroPartos;}

    public boolean isAptaParaReproduccion() {return aptaParaReproduccion;}
    public void setAptaParaReproduccion(boolean aptaParaReproduccion) {this.aptaParaReproduccion = aptaParaReproduccion;}
    
    //MÉTODOS
    
    //Para mostrar en consola. Para pruebas de funcionamiento.
    @Override
    public String toString() {
        return "Hembra{" +
                "numeroMarca='" + getNumeroMarca() + '\'' +
                ", estadoReproductivoActual=" + estadoReproductivoActual +
                ", aptaParaReproduccion=" + aptaParaReproduccion +
                '}';
    }
}
