package co.upc.ganado.servicios;

import co.upc.ganado.entidades.Traslado;
import co.upc.ganado.entidades.DetalleTraslado;
import co.upc.ganado.entidades.Finca;
import co.upc.ganado.datos.TrasladoData;

import java.util.HashMap;
import java.util.Map;
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

        //Crear el hashmap para los traslados. Clave = idTraslado, Valor = objeto Traslado.
        Map<Integer, Traslado> trasladosMap = new HashMap<>();

        //Llenar el hashmap con cada traslado de la lista.
        for (Traslado t : lista) {
            trasladosMap.put(t.getIdTraslado(), t);
        }

        //Crear el hashmap para las fincas. Clave = idFinca, Valor = nombreFinca.
        Map<Integer, String> fincasMap = new HashMap<>();

        //Llenar el hashmap con cada finca de la lista.
        for (Finca f : fincaService.mostrarTodo()) {
            fincasMap.put(f.getIdFinca(), f.getNombreFinca());
        }

        //Obtener el numero de marca del animal para mostrarlo en la trazabilidad.
        String numeroMarca = ganadoService.buscarNumeroMarca(idGanado);

        //Recorrer todos los detalles de traslado.
        for (DetalleTraslado d : detalleService.mostrarTodo()) {

            //Saltar los detalles que no pertenezcan al animal buscado. Filtro.
            if (d.getIdGanado() != idGanado) continue;

            //Obtener el traslado asociado al detalle mediante el hashmap.
            Traslado t = trasladosMap.get(d.getIdTraslado());

            //Por si el detalle apunta a un traslado que ya no existe. Registro huerfano.
            if (t == null) continue;

            //Obtener los nombres de las fincas desde el hashmap. "?" por si no existen.
            String fincaOrigen = fincasMap.getOrDefault(t.getIdFincaOrigen(), "?");
            String fincaDestino = fincasMap.getOrDefault(t.getIdFincaDestino(), "?");

            //Meter los datos a la lista. [numeroMarca, fecha, motivo, fincaOrigen, fincaDestino, observaciones].
            datos.add(new String[]{
                numeroMarca,
                t.getFechaTraslado(),
                t.getMotivoTraslado().toString(),
                fincaOrigen,
                fincaDestino,
                d.getObservaciones()
            });
        }

        return datos;
    }
}
