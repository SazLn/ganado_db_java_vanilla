package co.upc.ganado.servicios;

import co.upc.ganado.entidades.Traslado;
import co.upc.ganado.entidades.DetalleTraslado;
import co.upc.ganado.datos.TrasladoData;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para gestionar la logica de negocio de los traslados.
 *
 * @author Santiago Rafael Zuleta Neira
 */
public class TrasladoService {

    private List<Traslado> lista;
    private TrasladoData data;
    private DetalleTrasladoService detalleService;
    private GanadoService ganadoService;
    private FincaService fincaService;

    //Constructor
    public TrasladoService(DetalleTrasladoService detalleService, GanadoService ganadoService, FincaService fincaService) {
        this.data = new TrasladoData();
        this.detalleService = detalleService;
        this.ganadoService = ganadoService;
        this.fincaService = fincaService;
        this.lista = data.cargarTodo();
    }

    //Guarda los cambios en el archivo.
    private void guardar() {
        data.guardarTodo(lista);
    }


    // ===== CRUD =====


    public void insertar(Traslado nuevoTraslado) {
        lista.add(nuevoTraslado);
        guardar();
    }


    public void eliminar(int idTraslado) {
        boolean eliminado = lista.removeIf(t -> t.getIdTraslado() == idTraslado);
        if (eliminado) {
            guardar();
        }
    }


    public Traslado buscarPorId(int idTraslado) {
        return lista.stream()
                .filter(t -> t.getIdTraslado() == idTraslado)
                .findFirst()
                .orElse(null);
    }


    public List<Traslado> mostrarTodo() {
        return this.lista;
    }


    public void actualizarDetalle(Traslado trasladoActualizado) {
        int idTraslado = trasladoActualizado.getIdTraslado();

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getIdTraslado() == idTraslado) {
                lista.set(i, trasladoActualizado);
                guardar();
                return;
            }
        }
    }


    // ===== CONSULTAS =====

    //Q1: Trazabilidad de animal (requiere detalleService, ganadoService, fincaService)
    public List<String[]> getTrazabilidadAnimal(int idGanado) {
        List<String[]> datos = new ArrayList<>();
        //TODO: implementar logica de la consulta utilizando hashmaps
        return datos;
    }
}
