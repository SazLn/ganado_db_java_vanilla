package co.upc.ganado.entidades;

/**
 * Representa una vacuna del catálogo veterinario.
 *
 */
public class Vacuna {

    private int idVacuna;
    private String nombreVacuna;
    private String descripcion;
    private String dosisEstandar;

    //Constructor vacío. Para crear el objeto y luego setear atributo por atributo al leer datos del CSV.
    public Vacuna() {
    }

    public Vacuna(int idVacuna, String nombreVacuna, String descripcion, String dosisEstandar) {
        this.idVacuna = idVacuna;
        this.nombreVacuna = nombreVacuna;
        this.descripcion = descripcion;
        this.dosisEstandar = dosisEstandar;
    }

    public int getIdVacuna() { return idVacuna; }
    public void setIdVacuna(int idVacuna) { this.idVacuna = idVacuna; }
    public String getNombreVacuna() { return nombreVacuna; }
    public void setNombreVacuna(String nombreVacuna) { this.nombreVacuna = nombreVacuna; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getDosisEstandar() { return dosisEstandar; }
    public void setDosisEstandar(String dosisEstandar) { this.dosisEstandar = dosisEstandar; }

    //Para mostrar en consola. Para pruebas de funcionamiento.
    @Override
    public String toString() {
        return "Vacuna{" + "idVacuna=" + idVacuna + ", nombreVacuna='" + nombreVacuna + '\'' + '}';
    }
}
