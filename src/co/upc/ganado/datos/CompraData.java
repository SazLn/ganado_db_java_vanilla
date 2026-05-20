package co.upc.ganado.datos;

import co.upc.ganado.entidades.Compra;
import co.upc.ganado.entidades.enums.EnumTipoTransaccion;
import java.util.List;
import java.util.ArrayList;

/**
 * Capa de acceso a datos para la entidad Compra. Gestiona las operaciones
 * CRUD sobre el archivo data/compra.csv usando CsvUtil como intermediario.
 *
 * @author Santiago Rafael Zuleta Neira
 * @version 1.0
 * @since 2026-05-16
 */
public class CompraData {
    private final String RUTA_ARCHIVO = "data/compra.csv";

    //MÉTODOS
    /**
     * Lee el archivo CSV y convierte cada línea en un objeto Compra.
     * Omite la primera línea (header). Si el archivo no existe, retorna lista vacía.
     *
     * @return Lista de todas las compras almacenadas en el archivo CSV
     */
    public List<Compra> cargarTodo() {
        List<Compra> lista = new ArrayList<>();
        List<String[]> lineasArchivo = CsvUtil.leerCsv(RUTA_ARCHIVO);

        for (int i = 1; i < lineasArchivo.size(); i++) {
            String[] aux = lineasArchivo.get(i);
            
            int idCompra = aux[0].isEmpty() ? 0 : Integer.parseInt(aux[0]);
            String fechaCompra = aux[1];
            EnumTipoTransaccion tipoCompra = aux[2].isEmpty() ? null : EnumTipoTransaccion.valueOf(aux[2]);
            String documentoReferencia = aux[3];
            double valorTotalCompra = aux[4].isEmpty() ? 0.0 : Double.parseDouble(aux[4]);
            String responsableCompra = aux[5];
            String proveedor = aux[6];

            lista.add(new Compra(idCompra, fechaCompra, tipoCompra, documentoReferencia, valorTotalCompra, responsableCompra, proveedor));
        }

        return lista;
    }

    /**
     * Escribe una lista completa al archivo CSV. Sobrescribe el contenido anterior.
     * Incluye el header como primera línea.
     *
     * @param lista Lista de compras a persistir
     */
    public void guardarTodo(List<Compra> lista) {
        List<String[]> lineasEscribir = new ArrayList<>();
        lineasEscribir.add(new String[]{"idCompra", "fechaCompra", "tipoCompra", "documentoReferencia", "valorTotalCompra", "responsableCompra", "proveedor"});

        for (int i = 0; i < lista.size(); i++) {
            String idCompra = String.valueOf(lista.get(i).getIdCompra());
            String fechaCompra = lista.get(i).getFechaCompra();
            String tipoCompra = lista.get(i).getTipoCompra() != null ? lista.get(i).getTipoCompra().toString() : "";
            String documentoReferencia = lista.get(i).getDocumentoReferencia();
            String valorTotalCompra = String.format("%.2f", lista.get(i).getValorTotalCompra());
            String responsableCompra = lista.get(i).getResponsableCompra();
            String proveedor = lista.get(i).getProveedor();

            lineasEscribir.add(new String[]{idCompra, fechaCompra, tipoCompra, documentoReferencia, valorTotalCompra, responsableCompra, proveedor});
        }

        CsvUtil.escribirCsv(RUTA_ARCHIVO, lineasEscribir);
    }

    /**
     * Agrega una nueva compra al archivo CSV.
     *
     * @param nuevaCompra Compra a insertar
     */
    public void insertarCompra(Compra nuevaCompra) {
        List<Compra> lista = cargarTodo();
        lista.add(nuevaCompra);
        guardarTodo(lista);
    }

    /**
     * Reemplaza una compra existente por una versión actualizada.
     *
     * @param compraActualizada Compra con los datos actualizados
     */
    public void actualizarCompra(Compra compraActualizada) {
        List<Compra> lista = cargarTodo();

        for (int i = 0; i < lista.size(); i++) {
            Compra antigua = lista.get(i);
            if (antigua.getIdCompra() == compraActualizada.getIdCompra()) {
                lista.set(i, compraActualizada);
                break;
            }
        }

        guardarTodo(lista);
    }

    /**
     * Elimina una compra del archivo CSV.
     *
     * @param idCompra Identificador de la compra
     */
    public void eliminarCompra(int idCompra) {
        List<Compra> lista = cargarTodo();
        lista.removeIf(c -> c.getIdCompra() == idCompra);
        guardarTodo(lista);
    }
}
