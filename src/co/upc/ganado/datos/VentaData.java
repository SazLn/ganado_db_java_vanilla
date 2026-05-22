package co.upc.ganado.datos;

import co.upc.ganado.entidades.Venta;
import co.upc.ganado.entidades.enums.EnumTipoTransaccion;
import java.util.List;
import java.util.ArrayList;

/**
 * Capa de acceso a datos para la entidad Venta. Gestiona las operaciones
 * CRUD sobre el archivo data/venta.csv usando CsvUtil como intermediario.
 *
 * @author Santiago Rafael Zuleta Neira
 * @version 1.0
 * @since 2026-05-16
 */
public class VentaData {
    private final String RUTA_ARCHIVO = "data/venta.csv";

    //MÉTODOS
    /**
     * Lee el archivo CSV y convierte cada línea en un objeto Venta.
     * Omite la primera línea (header). Si el archivo no existe, retorna lista vacía.
     *
     * @return Lista de todas las ventas almacenadas en el archivo CSV
     */
    public List<Venta> cargarTodo() {
        List<Venta> lista = new ArrayList<>();
        List<String[]> lineasArchivo = CsvUtil.leerCsv(RUTA_ARCHIVO);

        for (int i = 1; i < lineasArchivo.size(); i++) {
            String[] aux = lineasArchivo.get(i);
            int idVenta = aux[0].isEmpty() ? 0 : Integer.parseInt(aux[0]);
            String fechaVenta = aux[1];
            EnumTipoTransaccion tipoVenta = aux[2].isEmpty() ? null : EnumTipoTransaccion.valueOf(aux[2]);
            String comprador = aux[3];
            String documentoReferencia = aux[4];
            double valorTotalVenta = aux[5].isEmpty() ? 0.0 : Double.parseDouble(aux[5]);
            String metodoPago = aux[6];
            String responsableVenta = aux[7];

            lista.add(new Venta(idVenta, fechaVenta, tipoVenta, comprador, documentoReferencia, valorTotalVenta, metodoPago, responsableVenta));
        }

        return lista;
    }

    /**
     * Escribe una lista completa al archivo CSV. Sobrescribe el contenido anterior.
     * Incluye el header como primera línea.
     *
     * @param lista Lista de ventas a persistir
     */
    public void guardarTodo(List<Venta> lista) {
        List<String[]> lineasEscribir = new ArrayList<>();
        lineasEscribir.add(new String[]{"idVenta", "fechaVenta", "tipoVenta", "comprador", "documentoReferencia", "valorTotalVenta", "metodoPago", "responsableVenta"});

        for (int i = 0; i < lista.size(); i++) {
            String idVenta = String.valueOf(lista.get(i).getIdVenta());
            String fechaVenta = lista.get(i).getFechaVenta();
            String tipoVenta = lista.get(i).getTipoVenta() != null ? lista.get(i).getTipoVenta().toString() : "";
            String comprador = lista.get(i).getComprador();
            String documentoReferencia = lista.get(i).getDocumentoReferencia();
            String valorTotalVenta = String.format("%.2f", lista.get(i).getValorTotalVenta());
            String metodoPago = lista.get(i).getMetodoPago();
            String responsableVenta = lista.get(i).getResponsableVenta();

            lineasEscribir.add(new String[]{idVenta, fechaVenta, tipoVenta, comprador, documentoReferencia, valorTotalVenta, metodoPago, responsableVenta});
        }

        CsvUtil.escribirCsv(RUTA_ARCHIVO, lineasEscribir);
    }

    /**
     * Agrega una nueva venta al archivo CSV.
     *
     * @param nuevaVenta Venta a insertar
     */
    public void insertarVenta(Venta nuevaVenta) {
        List<Venta> lista = cargarTodo();
        lista.add(nuevaVenta);
        guardarTodo(lista);
    }

    /**
     * Reemplaza una venta existente por una versión actualizada.
     *
     * @param ventaActualizada Venta con los datos actualizados
     */
    public void actualizarVenta(Venta ventaActualizada) {
        List<Venta> lista = cargarTodo();

        for (int i = 0; i < lista.size(); i++) {
            Venta antigua = lista.get(i);
            if (antigua.getIdVenta() == ventaActualizada.getIdVenta()) {
                lista.set(i, ventaActualizada);
                break;
            }
        }

        guardarTodo(lista);
    }

    /**
     * Elimina una venta del archivo CSV.
     *
     * @param idVenta Identificador de la venta
     */
    public void eliminarVenta(int idVenta) {
        List<Venta> lista = cargarTodo();
        lista.removeIf(v -> v.getIdVenta() == idVenta);
        guardarTodo(lista);
    }
}
