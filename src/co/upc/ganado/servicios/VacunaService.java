package co.upc.ganado.servicios;

import co.upc.ganado.entidades.Vacuna;
import co.upc.ganado.datos.VacunaData;

import java.util.List;

/**
 * Servicio para gestionar la logica de negocio de las vacunas.
 *
 * @author Santiago Rafael Zuleta Neira
 */
public class VacunaService {

    private List<Vacuna> lista;
    private VacunaData data;
    private AplicacionVacunaService aplicacionService;
    private GanadoService ganadoService;

    //Constructor
    public VacunaService(AplicacionVacunaService aplicacionService, GanadoService ganadoService) {
        this.data = new VacunaData();
        this.aplicacionService = aplicacionService;
        this.ganadoService = ganadoService;
        this.lista = data.cargarTodo();
    }

    //Guarda los cambios en el archivo.
    private void guardar() {
        data.guardarTodo(lista);
    }


    // ===== CRUD =====


    public void insertar(Vacuna nuevaVacuna) {
        // TODO: implementar
    }


    public void eliminar(int idVacuna) {
        // TODO: implementar
    }


    public Vacuna buscarPorId(int idVacuna) {
        // TODO: implementar
        return null;
    }


    public List<Vacuna> mostrarTodo() {
        // TODO: implementar
        return null;
    }


    public void actualizarDetalle(Vacuna vacunaActualizada) {
        // TODO: implementar
    }


    // ===== CONSULTAS =====

    //Q4: Control de vacunacion (requiere aplicacionService y ganadoService)
    public List<String[]> getControlVacunacion() {
        // TODO: implementar
        return null;
    }
}
