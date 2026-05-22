package co.upc.ganado.servicios;

import co.upc.ganado.entidades.Venta;
import co.upc.ganado.datos.VentaData;

import java.util.List;

/**
 * Servicio para gestionar la lógica de negocio de las ventas.
 * Opera sobre un ArrayList en memoria y persiste los cambios al archivo CSV
 * a través de VentaData.
 *
 * @author Santiago Rafael Zuleta Neira
 * @version 1.0
 * @since 2026-05-20
 */
public class VentaService {
    private List<Venta> lista;
    private VentaData data;
    
    //Constructor
    public VentaService() {
        this.data = new VentaData();
        this.lista = data.cargarTodo();
    }
    
    //------MÉTODOS------//
    
    //Método auxiliar.
    private void guardar() {
        data.guardarTodo(lista);
    }
    
    

    //Métodos para CRUD
    
    public void insertar(Venta nuevaVenta) {
        lista.add(nuevaVenta);
        
        guardar();
    }
    
    
    public void eliminar(int idVenta) {
        boolean eliminado = lista.removeIf(v -> v.getIdVenta() == idVenta);
        
        if (eliminado) {
            guardar();
        }
    }
    
    
    public Venta buscarPorId(int idBuscar) {
        return lista.stream()
                    .filter(v -> v.getIdVenta() == idBuscar)
                    .findFirst()
                    .orElse(null);
        
    }
    
    
    public List<Venta> mostrarTodo() {
        return this.lista;
    }
    
    
    public void actualizarDetalle(Venta ventaActualizada) {
        int idVenta = ventaActualizada.getIdVenta();
        
        for (int i = 0; i < lista.size(); i++) {
            int idAntiguo = lista.get(i).getIdVenta();
            
            if (idAntiguo == idVenta) {
                lista.set(i, ventaActualizada);
                guardar();
                
                return;
            }
        }
    }
}
