package co.upc.ganado.servicios;

import co.upc.ganado.entidades.DetalleTraslado;
import co.upc.ganado.datos.DetalleTrasladoData;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar la lógica de negocio de los detalles de traslado.
 * 
 * @author Santiago Rafael Zuleta Neira
 */
public class DetalleTrasladoService {

    private List<DetalleTraslado> listaDetalles;
    private DetalleTrasladoData data;

    //Constructor
    public DetalleTrasladoService() {
        this.data = new DetalleTrasladoData();
        this.listaDetalles = data.cargarTodo();
    }

    //guarda los cambios en el archivo.
    private void guardar() {
        data.guardarTodo(listaDetalles);
    }

    
    // ===== CRUD =====
    

    public void insertar(DetalleTraslado nuevoDetalle) {
        listaDetalles.add(nuevoDetalle);
        guardar();
    }
    

    public void eliminar(int idTraslado, int idGanado) {
        boolean eliminado = listaDetalles.removeIf(
            d -> d.getIdGanado() == idGanado && d.getIdTraslado() == idTraslado
        );
        if (eliminado) {
            guardar();
        }
    }

    //Busca un detalle de traslado especifico.
    public DetalleTraslado buscarPorId(int idTraslado, int idGanado) {
        return listaDetalles.stream()
                .filter(d -> d.getIdGanado() == idGanado)
                .filter(d -> d.getIdTraslado() == idTraslado)
                .findFirst()
                .orElse(null);
    }

    //Retorna todos los detalles que pertenecen a un mismo traslado.
    public List<DetalleTraslado> buscarTraslado(int idTraslado) {
        return listaDetalles.stream()
                .filter(d -> d.getIdTraslado() == idTraslado)
                .collect(Collectors.toList());
    }

    
    public List<DetalleTraslado> mostrarTodo() {
        return this.listaDetalles;
    }

    //Actualiza un registro del archivo.
    public void actualizarDetalle(DetalleTraslado detalleActualizado) {
        int idGanado = detalleActualizado.getIdGanado();
        int idTraslado = detalleActualizado.getIdTraslado();

        for (int i = 0; i < listaDetalles.size(); i++) {
            DetalleTraslado d = listaDetalles.get(i);
            if (d.getIdGanado() == idGanado && d.getIdTraslado() == idTraslado) {
                listaDetalles.set(i, detalleActualizado);
                guardar();
                return;
            }
        }
    }
}
