package co.upc.ganado.entidades;

import co.upc.ganado.entidades.enums.EnumSexo;
import co.upc.ganado.entidades.enums.EnumEstadoSalud;
import co.upc.ganado.entidades.enums.EnumEstadoVida;
import co.upc.ganado.entidades.enums.EnumMotivoSalida;

/**
 *Clase base para todo animal bovino del sistema.
 *Es abstracta. Representa el estado actual del animal en el hato.
 *No se puede instanciar directamente.
 */
public abstract class Ganado {
    private int idGanado;
    private String numeroMarca; //Es único para cada animal.
    private String fechaNacimiento; //NULLABLE
    private EnumSexo tipoSexo;
    private double peso; //En kg
    private EnumEstadoSalud estadoSalud;
    private EnumEstadoVida estadoVida; //Por defecto Activo
    private String fechaSalida; //NULLABLE
    private EnumMotivoSalida motivoSalida; //NULLABLE
    private int idFinca; //Para tener constancia de la ubicación actual de cada animal.
    private int idMadre; //NULL por defecto. Hace referencia a otra instancia de esta misma clase.
    
    //Constructor vacío. Para crear el objeto y luego setear atributo por atributo al leer datos del CSV.
    public Ganado() {}
    
    //Constructor completo.
    public Ganado(int idGanado, String numeroMarca, 
            String fechaNacimiento, EnumSexo tipoSexo, 
            double peso, EnumEstadoSalud estadoSalud,
            EnumEstadoVida estadoVida,
            String fechaSalida, EnumMotivoSalida motivoSalida, 
            int idFinca, int idMadre) {
        
        this.idGanado = idGanado;
        this.numeroMarca = numeroMarca;
        this.fechaNacimiento = fechaNacimiento;
        this.tipoSexo = tipoSexo;
        this.peso = peso;
        this.estadoSalud = estadoSalud;
        this.estadoVida = estadoVida;
        this.fechaSalida = fechaSalida;
        this.motivoSalida = motivoSalida;
        this.idFinca = idFinca;
        this.idMadre = idMadre;
    }
    
    /*Constructor sin:
      - Fecha de nacimiento
      - Fecha de salida
      - Motivo de salida
      Y con el valor del id de la madre por defecto.
    */

    public Ganado(int idGanado, String numeroMarca, EnumSexo tipoSexo, double peso, EnumEstadoSalud estadoSalud, int idFinca) {
        this.idGanado = idGanado;
        this.numeroMarca = numeroMarca;
        this.tipoSexo = tipoSexo;
        this.peso = peso;
        this.estadoSalud = estadoSalud;
        this.estadoVida = EnumEstadoVida.Activo;
        this.idFinca = idFinca;
        this.idMadre = 0; //Significa que no hay madre registrada, el animal entro al sistema por compra.
    }
    
    //GETTERS y SETTERS
    public int getIdGanado() {return idGanado;}
    public void setIdGanado(int idGanado) {this.idGanado = idGanado;}

    public String getNumeroMarca() {return numeroMarca;}
    public void setNumeroMarca(String numeroMarca) {this.numeroMarca = numeroMarca;}

    public String getFechaNacimiento() {return fechaNacimiento;}
    public void setFechaNacimiento(String fechaNacimiento) {this.fechaNacimiento = fechaNacimiento;}

    public EnumSexo getTipoSexo() {return tipoSexo;}
    public void setTipoSexo(EnumSexo tipoSexo) {this.tipoSexo = tipoSexo;}

    public double getPeso() {return peso;}
    public void setPeso(double peso) {this.peso = peso;}

    public EnumEstadoSalud getEstadoSalud() {return estadoSalud;}
    public void setEstadoSalud(EnumEstadoSalud estadoSalud) {this.estadoSalud = estadoSalud;}

    public EnumEstadoVida getEstadoVida() {return estadoVida;}
    public void setEstadoVida(EnumEstadoVida estadoVida) {this.estadoVida = estadoVida;}

    public String getFechaSalida() {return fechaSalida;}
    public void setFechaSalida(String fechaSalida) {this.fechaSalida = fechaSalida;}

    public EnumMotivoSalida getMotivoSalida() {return motivoSalida;}
    public void setMotivoSalida(EnumMotivoSalida motivoSalida) {this.motivoSalida = motivoSalida;}

    public int getIdFinca() {return idFinca;}
    public void setIdFinca(int idFinca) {this.idFinca = idFinca;}

    public int getIdMadre() {return idMadre;}
    public void setIdMadre(int idMadre) {this.idMadre = idMadre;}
    
    //MÉTODOS
    
    //Para mostrar en consola. Para pruebas de funcionamiento.
    @Override
    public String toString() {
        return "Ganado{" +
                "idGanado=" + idGanado +
                ", numeroMarca='" + numeroMarca + '\'' +
                ", tipoSexo=" + tipoSexo +
                ", estadoVida=" + estadoVida +
                ", idFinca=" + idFinca +
                '}';
    }
}
