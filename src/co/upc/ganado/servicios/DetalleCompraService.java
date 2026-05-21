package co.upc.ganado.servicios;

import co.upc.ganado.entidades.DetalleCompra;
import co.upc.ganado.datos.DetalleCompraData;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio que gestiona la lógica de negocio de los detalles de compra.
 * Opera sobre un ArrayList en memoria y persiste los cambios al archivo CSV
 * a través de DetalleCompraData.
 *
 * @author Santiago Rafael Zuleta Neira
 * @version 1.0
 * @since 2026-05-20
 */
public class DetalleCompraService {

    private List<DetalleCompra> listaDetalles;
    private DetalleCompraData data;

    //Constructor
    public DetalleCompraService(DetalleCompraData data) {
        this.data = data;
        this.listaDetalles = data.cargarTodo();
    }

    //Guarda los cambios en el archivo.
    private void guardar() {
        data.guardarTodo(listaDetalles);
    }

    
    // ===== CRUD =====

    
    public void insertar(DetalleCompra nuevoDetalle) {
        listaDetalles.add(nuevoDetalle);
        
        guardar();
    }

    
    //Elimina un registro del archivo.
    public void eliminar(int idCompra, int idGanado) {
        boolean eliminado = listaDetalles.removeIf(
            d -> d.getIdCompra() == idCompra && d.getIdGanado() == idGanado
        );
        
        if (eliminado) {
            guardar();
        }
    }

    //Busca un detalle de compra específico.
    public DetalleCompra buscarPorId(int idCompra, int idGanado) {
        DetalleCompra resultado = listaDetalles.stream()
                                  .filter(
                                    d -> d.getIdCompra() == idCompra && d.getIdGanado() == idGanado
                                  )
                                  .findFirst()
                                  .orElse(null);
        return resultado;
    }

    //Retorna todos los detalles que pertenecen a una misma compra.
    public List<DetalleCompra> buscarPorCompra(int idCompra) {
        List<DetalleCompra> detalles = listaDetalles.stream()
                                       .filter(d -> d.getIdCompra() == idCompra)
                                       .collect(Collectors.toList());
        return detalles;
    }

    
    public List<DetalleCompra> mostrarTodo() {
        return this.listaDetalles;
    }

    //Actualiza un registro del archivo.
    public void actualizarDetalle(DetalleCompra detalleActualizado) {
        int idCompra = detalleActualizado.getIdCompra();
        int idGanado = detalleActualizado.getIdGanado();

        for (int i = 0; i < listaDetalles.size(); i++) {
            DetalleCompra d = listaDetalles.get(i);
            if (d.getIdGanado() == idGanado && d.getIdCompra()== idCompra) {
                listaDetalles.set(i, detalleActualizado);
                guardar();
                return;
            }
        }
    }
}
