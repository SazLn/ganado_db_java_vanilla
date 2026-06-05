package co.upc.ganado.servicios;

import co.upc.ganado.entidades.Compra;
import co.upc.ganado.datos.CompraData;
import co.upc.ganado.entidades.DetalleCompra;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para gestionar la logica de negocio de las compras.
 * Opera sobre un ArrayList en memoria y persiste los cambios al archivo CSV
 * a traves de CompraData.
 *
 * @author Santiago Rafael Zuleta Neira
 * @version 1.0
 * @since 2026-05-20
 */
public class CompraService {

    private List<Compra> lista;
    private CompraData data;
    private DetalleCompraService detalleService;
    private GanadoService ganadoService;

    //Constructor
    public CompraService(DetalleCompraService detalleService, GanadoService ganadoService) {
        this.data = new CompraData();
        this.detalleService = detalleService;
        this.ganadoService = ganadoService;
        this.lista = data.cargarTodo();
    }

    //Guarda los cambios en el archivo.
    private void guardar() {
        data.guardarTodo(lista);
    }

    
    // ===== CRUD ===== //
    

    public void insertar(Compra nuevaCompra) {
        lista.add(nuevaCompra);
        guardar();
    }


    public void eliminar(int idCompra) {
        boolean eliminado = lista.removeIf(c -> c.getIdCompra() == idCompra);
        if (eliminado) {
            guardar();
        }
    }


    public Compra buscarPorId(int idCompra) {
        return lista.stream()
                .filter(c -> c.getIdCompra() == idCompra)
                .findFirst()
                .orElse(null);
    }


    public List<Compra> mostrarTodo() {
        return this.lista;
    }


    public void actualizarDetalle(Compra compraActualizada) {
        int idCompra = compraActualizada.getIdCompra();

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getIdCompra() == idCompra) {
                lista.set(i, compraActualizada);
                guardar();
                return;
            }
        }
    }

    
    // ===== CONSULTAS =====

    //Q6: Historial de compras (requiere DetalleCompraService y GanadoService)
    public List<String[]> getHistorialCompras() {
        //Lista de arreglos con los datos de la consulta.
        List<String[]> datos = new ArrayList<>();
        
        for (int i = 0; i < lista.size(); i++) {
            //Extraer los datos de la compra.
            String idCompra = String.valueOf(lista.get(i).getIdCompra());
            String fechaCompra = lista.get(i).getFechaCompra();
            String proveedor = lista.get(i).getProveedor();
            String responsable = lista.get(i).getResponsableCompra();
            String valorTotal = String.format("%,.2f", lista.get(i).getValorTotalCompra());
            
            //Obtener los detalles de la compra.
            List<DetalleCompra> detalles = detalleService.buscarPorCompra(lista.get(i).getIdCompra());
            
            //Recorrer cada detalle.
            for (DetalleCompra detalle : detalles) {
                //Extraer los datos del detalle.
                String precioIndividual = String.format("%,.2f", detalle.getPrecioIndividual());
                int idGanado = detalle.getIdGanado();
                
                //Extraer el número de marca del animal.
               String numeroMarca = ganadoService.buscarNumeroMarca(idGanado);
               
               //Meter todo a la lista.
               datos.add(new String[]{idCompra, fechaCompra, proveedor, numeroMarca, precioIndividual, valorTotal, responsable});
            }
        }
        
        
        return datos;
    }
}
