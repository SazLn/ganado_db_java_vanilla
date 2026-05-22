package co.upc.ganado.servicios;

import co.upc.ganado.entidades.DetalleVenta;
import co.upc.ganado.datos.DetalleVentaData;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar la lógica de negocio de los detalles de venta.
 * 
 * @author Santiago Rafael Zuleta Neira
 */
public class DetalleVentaService {

    private List<DetalleVenta> listaDetalles;
    private DetalleVentaData data;

    //Constructor
    public DetalleVentaService() {
        this.data = new DetalleVentaData();
        this.listaDetalles = data.cargarTodo();
    }

    //Guarda los cambios en el archivo.
    private void guardar() {
        data.guardarTodo(listaDetalles);
    }

    
    // ===== CRUD =====
    

    public void insertar(DetalleVenta nuevoDetalle) {
        listaDetalles.add(nuevoDetalle);
        guardar();
    }

    
    //Elimina un registro del archivo.
    public void eliminar(int idVenta, int idganado) {
        boolean eliminado = listaDetalles.removeIf(
            d -> d.getIdVenta() == idVenta && d.getIdganado() == idganado
        );
        if (eliminado) {
            guardar();
        }
    }

    //Busca un detalle de venta específico.
    public DetalleVenta buscarPorId(int idVenta, int idganado) {
        return listaDetalles.stream()
                .filter(d -> d.getIdVenta() == idVenta && d.getIdganado() == idganado)
                .findFirst()
                .orElse(null);
    }

    //Retorna todos los detalles que pertenecen a una misma venta.
    public List<DetalleVenta> buscarPorVenta(int idVenta) {
        return listaDetalles.stream()
                .filter(d -> d.getIdVenta() == idVenta)
                .collect(Collectors.toList());
    }

    
    public List<DetalleVenta> mostrarTodo() {
        return this.listaDetalles;
    }

    //Actualiza un registro del archivo.
    public void actualizarDetalle(DetalleVenta detalleActualizado) {
        int idVenta = detalleActualizado.getIdVenta();
        int idganado = detalleActualizado.getIdganado();

        for (int i = 0; i < listaDetalles.size(); i++) {
            DetalleVenta d = listaDetalles.get(i);
            if (d.getIdVenta() == idVenta && d.getIdganado() == idganado) {
                listaDetalles.set(i, detalleActualizado);
                guardar();
                return;
            }
        }
    }
}
