package co.upc.ganado.entidades;

import co.upc.ganado.entidades.enums.EnumCalidadReproductiva;
import co.upc.ganado.entidades.enums.EnumEstadoSalud;
import co.upc.ganado.entidades.enums.EnumEstadoVida;
import co.upc.ganado.entidades.enums.EnumMotivoSalida;
import co.upc.ganado.entidades.enums.EnumSexo;

/**
 *Representa un bovino macho. Hereda los atributos de Ganado.
 *Incluye información sobre su calidad reproductiva y si es padrote.
 * En todo el ganado solo puede haber un padrote.
 */
public class Macho extends Ganado {
    //Atributos propios de la clase heredada.
    private EnumCalidadReproductiva calidadReproductiva;
    private boolean esPadrote;
    private String fechaInicioPadrote; //NULLABLE
    private String fechaFinPadrote; //NULLABLE
    
    //Constructor vacío. Para crear el objeto y luego setear atributo por atributo al leer datos del CSV.
    public Macho() {}
    
    //Constructor completo. Atributos propios y heredados.
    public Macho(EnumCalidadReproductiva calidadReproductiva, boolean esPadrote, String fechaInicioPadrote, String fechaFinPadrote, int idGanado, String numeroMarca, String fechaNacimiento, EnumSexo tipoSexo, double peso, EnumEstadoSalud estadoSalud, EnumEstadoVida estadoVida, String fechaSalida, EnumMotivoSalida motivoSalida, int idFinca, int idMadre) {
        super(idGanado, numeroMarca, fechaNacimiento, tipoSexo, peso, estadoSalud, estadoVida, fechaSalida, motivoSalida, idFinca, idMadre);
        this.calidadReproductiva = calidadReproductiva;
        this.esPadrote = esPadrote;
        this.fechaInicioPadrote = fechaInicioPadrote;
        this.fechaFinPadrote = fechaFinPadrote;
    }
    
    
    
    /*Constructor sin:
      - Fecha de nacimiento
      - Fecha de salida
      - Motivo de salida
      Y con el valor del id de la madre por defecto.
    */
    public Macho(EnumCalidadReproductiva calidadReproductiva, boolean esPadrote,
            String fechaInicioPadrote, String fechaFinPadrote, 
            int idGanado, String numeroMarca, 
            EnumSexo tipoSexo, double peso, 
            EnumEstadoSalud estadoSalud, int idFinca) {
        
        super(idGanado, numeroMarca, tipoSexo, peso, estadoSalud, idFinca);
        this.calidadReproductiva = calidadReproductiva;
        this.esPadrote = esPadrote;
        this.fechaInicioPadrote = fechaInicioPadrote;
        this.fechaFinPadrote = fechaFinPadrote;
    }
    
    //GETTERS y SETTERS
    public EnumCalidadReproductiva getCalidadReproductiva() {return calidadReproductiva;}
    public void setCalidadReproductiva(EnumCalidadReproductiva calidadReproductiva) {this.calidadReproductiva = calidadReproductiva;}

    public boolean isEsPadrote() {return esPadrote;}
    public void setEsPadrote(boolean esPadrote) {this.esPadrote = esPadrote;}

    public String getFechaInicioPadrote() {return fechaInicioPadrote;}
    public void setFechaInicioPadrote(String fechaInicioPadrote) {this.fechaInicioPadrote = fechaInicioPadrote;}

    public String getFechaFinPadrote() {return fechaFinPadrote;}
    public void setFechaFinPadrote(String fechaFinPadrote) {this.fechaFinPadrote = fechaFinPadrote;}
    
    //MÉTODOS
    
    //Para mostrar en consola. Para pruebas de funcionamiento.
    @Override
    public String toString() {
        return "Macho{" +
                "numeroMarca='" + getNumeroMarca() + '\'' +
                ", calidadReproductiva=" + calidadReproductiva +
                ", esPadrote=" + esPadrote +
                '}';
    }
}
