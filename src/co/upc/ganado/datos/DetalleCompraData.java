package co.upc.ganado.datos;

import co.upc.ganado.entidades.DetalleCompra;
import java.util.List;
import java.util.ArrayList;

/**
 * Capa de acceso a datos para la entidad DetalleCompra. Gestiona las operaciones
 * CRUD sobre el archivo data/detalleCompra.csv usando CsvUtil como intermediario.
 *
 * @author Santiago Rafael Zuleta Neira
 * @version 1.0
 * @since 2026-05-16
 */
public class DetalleCompraData {
    private final String RUTA_ARCHIVO = "data/detalleCompra.csv";

    //MÉTODOS
    /**
     * Lee el archivo CSV y convierte cada línea en un objeto DetalleCompra.
     * Omite la primera línea (header). Si el archivo no existe, retorna lista vacía.
     *
     * @return Lista de todos los detalles de compra almacenados en el archivo CSV
     */
    public List<DetalleCompra> cargarTodo() {
        List<DetalleCompra> lista = new ArrayList<>();
        List<String[]> lineasArchivo = CsvUtil.leerCsv(RUTA_ARCHIVO);

        for (int i = 1; i < lineasArchivo.size(); i++) {
            String[] aux = lineasArchivo.get(i);
            int idCompra = aux[0].isEmpty() ? 0 : Integer.parseInt(aux[0]);
            int idGanado = aux[1].isEmpty() ? 0 : Integer.parseInt(aux[1]);
            double precioIndividual = aux[2].isEmpty() ? 0.0 : Double.parseDouble(aux[2]);
            String observaciones = aux[3];

            lista.add(new DetalleCompra(idCompra, idGanado, precioIndividual, observaciones));
        }

        return lista;
    }

    /**
     * Escribe una lista completa al archivo CSV. Sobrescribe el contenido anterior.
     * Incluye el header como primera línea.
     *
     * @param lista Lista de detalles de compra a persistir
     */
    public void guardarTodo(List<DetalleCompra> lista) {
        List<String[]> lineasEscribir = new ArrayList<>();
        lineasEscribir.add(new String[]{"idCompra", "idGanado", "precioIndividual", "observaciones"});

        for (int i = 0; i < lista.size(); i++) {
            String idCompra = String.valueOf(lista.get(i).getIdCompra());
            String idGanado = String.valueOf(lista.get(i).getIdGanado());
            String precio = String.format("%.2f", lista.get(i).getPrecioIndividual());
            String observaciones = lista.get(i).getObservaciones();

            lineasEscribir.add(new String[]{idCompra, idGanado, precio, observaciones});
        }

        CsvUtil.escribirCsv(RUTA_ARCHIVO, lineasEscribir);
    }

    /**
     * Agrega un nuevo detalle al archivo CSV.
     *
     * @param nuevoDetalle Detalle a insertar
     */
    public void insertarDetalle(DetalleCompra nuevoDetalle) {
        List<DetalleCompra> lista = cargarTodo();
        lista.add(nuevoDetalle);
        guardarTodo(lista);
    }

    /**
     * Reemplaza un detalle existente por una versión actualizada.
     *
     * @param detalleActualizado Detalle con los datos actualizados
     */
    public void actualizarDetalle(DetalleCompra detalleActualizado) {
        List<DetalleCompra> lista = cargarTodo();

        for (int i = 0; i < lista.size(); i++) {
            DetalleCompra antiguo = lista.get(i);
            if (antiguo.getIdCompra() == detalleActualizado.getIdCompra()
                    && antiguo.getIdGanado() == detalleActualizado.getIdGanado()) {
                lista.set(i, detalleActualizado);
                break;
            }
        }

        guardarTodo(lista);
    }

    /**
     * Elimina un detalle del archivo CSV.
     *
     * @param idCompra Identificador de la compra
     * @param idGanado Identificador del ganado
     */
    public void eliminarDetalle(int idCompra, int idGanado) {
        List<DetalleCompra> lista = cargarTodo();
        lista.removeIf(d -> d.getIdCompra() == idCompra && d.getIdGanado() == idGanado);
        guardarTodo(lista);
    }
}
