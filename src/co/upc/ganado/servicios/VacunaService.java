package co.upc.ganado.servicios;

import co.upc.ganado.entidades.Ganado;
import co.upc.ganado.entidades.Vacuna;
import co.upc.ganado.datos.VacunaData;
import co.upc.ganado.entidades.AplicacionVacuna;

import java.util.ArrayList;
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
        lista.add(nuevaVacuna);
        guardar();
    }


    public void eliminar(int idVacuna) {
        boolean eliminado = lista.removeIf(v -> v.getIdVacuna() == idVacuna);
        if (eliminado) {
            guardar();
        }
    }


    public Vacuna buscarPorId(int idVacuna) {
        return lista.stream()
                .filter(v -> v.getIdVacuna() == idVacuna)
                .findFirst()
                .orElse(null);
    }


    public List<Vacuna> mostrarTodo() {
        return this.lista;
    }


    public void actualizarDetalle(Vacuna vacunaActualizada) {
        int idVacuna = vacunaActualizada.getIdVacuna();

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getIdVacuna() == idVacuna) {
                lista.set(i, vacunaActualizada);
                guardar();
                return;
            }
        }
    }


    // ===== CONSULTAS =====

    //Q4: Control de vacunacion (requiere aplicacionService y ganadoService)
    public List<String[]> getControlVacunacion() {
        List<String[]> datos = new ArrayList<>();
        
        
        for (int i = 0; i < lista.size(); i++) {
            //Extraer los datos de la vacuna.
            String nombreVacuna = lista.get(i).getNombreVacuna();
            
            //Obtener las aplicaciones de esa vacuna.
            List<AplicacionVacuna> aplicacion = aplicacionService.buscarPorVacuna(lista.get(i).getIdVacuna());
            
            //Extraer los datos de esas aplicaciones.
            for (AplicacionVacuna ap : aplicacion)  {
                String fechaAplicacion = ap.getFechaAplicacion();
                String dosis = ap.getDosisAplicada();
                String responsable = ap.getResponsableVacunacion();
                
                //Extraer el numero de marca.
                String numeroMarca = ganadoService.buscarNumeroMarca(ap.getIdGanado());
                
                //Meter todo a la lista.
                datos.add(new String[]{numeroMarca, nombreVacuna, fechaAplicacion, dosis, responsable});
            }
             
        }
        
        return datos;
    }
}
