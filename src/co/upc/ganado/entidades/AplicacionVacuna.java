package co.upc.ganado.entidades;

/**
 * ENTIDAD ASOCIATIVA GANADO-VACUNA
 * Registra la aplicación de una vacuna a un animal específico en una fecha
 * determinada, con su dosis y responsable.
 */
public class AplicacionVacuna {
    private int idGanado;
    private int idVacuna;
    private String fechaAplicacion;
    private String dosisAplicada;
    private String responsableVacunacion; //NULLABLE
    
    //Constructor vacío. Para crear el objeto y luego setear atributo por atributo al leer datos del CSV.
    public AplicacionVacuna() {}
    
    //Constructor completo.
    public AplicacionVacuna(int idGanado, int idVacuna, String fechaAplicacion, String dosisAplicada, String responsableVacunacion) {
        this.idGanado = idGanado;
        this.idVacuna = idVacuna;
        this.fechaAplicacion = fechaAplicacion;
        this.dosisAplicada = dosisAplicada;
        this.responsableVacunacion = responsableVacunacion;
    }
    
    //Constructor sin responsable de vacunación.
    public AplicacionVacuna(int idGanado, int idVacuna, String fechaAplicacion, String dosisAplicada) {
        this.idGanado = idGanado;
        this.idVacuna = idVacuna;
        this.fechaAplicacion = fechaAplicacion;
        this.dosisAplicada = dosisAplicada;
    }
    
    //GETTERS y SETTERS
    public int getIdGanado() {return idGanado;}
    public void setIdGanado(int idGanado) {this.idGanado = idGanado;}

    public int getIdVacuna() {return idVacuna;}
    public void setIdVacuna(int idVacuna) {this.idVacuna = idVacuna;}

    public String getFechaAplicacion() {return fechaAplicacion;}
    public void setFechaAplicacion(String fechaAplicacion) {this.fechaAplicacion = fechaAplicacion;}

    public String getDosisAplicada() {return dosisAplicada;}
    public void setDosisAplicada(String dosisAplicada) {this.dosisAplicada = dosisAplicada;}

    public String getResponsableVacunacion() {return responsableVacunacion;}
    public void setResponsableVacunacion(String responsableVacunacion) {this.responsableVacunacion = responsableVacunacion;}
    
    //MÉTODOS
    
    //Para mostrar en consola. Para pruebas de funcionamiento.
    @Override
    public String toString() {
        return "AplicacionVacuna{" +
                "idGanado=" + idGanado +
                ", idVacuna=" + idVacuna +
                ", fechaAplicacion='" + fechaAplicacion + '\'' +
                ", dosisAplicada='" + dosisAplicada + '\'' +
                '}';
    }
}
