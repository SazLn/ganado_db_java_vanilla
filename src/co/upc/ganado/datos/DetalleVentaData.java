package co.upc.ganado.datos;

import co.upc.ganado.entidades.DetalleVenta;
import java.util.List;
import java.util.ArrayList;

/**
 * Capa de acceso a datos para la entidad DetalleVenta. Gestiona las operaciones
 * CRUD sobre el archivo data/detalleVenta.csv usando CsvUtil como intermediario.
 *
 * @author Santiago Rafael Zuleta Neira
 * @version 1.0
 * @since 2026-05-16
 */
public class DetalleVentaData {
    private final String RUTA_ARCHIVO = "data/detalleVenta.csv";

    //MÉTODOS
    /**
     * Lee el archivo CSV y convierte cada línea en un objeto DetalleVenta.
     * Omite la primera línea (header). Si el archivo no existe, retorna lista vacía.
     *
     * @return Lista de todos los detalles de venta almacenados en el archivo CSV
     */
    public List<DetalleVenta> cargarTodo() {
        List<DetalleVenta> lista = new ArrayList<>();
        List<String[]> lineasArchivo = CsvUtil.leerCsv(RUTA_ARCHIVO);

        for (int i = 1; i < lineasArchivo.size(); i++) {
            String[] aux = lineasArchivo.get(i);
            int idVenta = aux[0].isEmpty() ? 0 : Integer.parseInt(aux[0]);
            int idganado = aux[1].isEmpty() ? 0 : Integer.parseInt(aux[1]);
            double precioIndividual = aux[2].isEmpty() ? 0.0 : Double.parseDouble(aux[2]);
            double pesoMomentoVenta = aux[3].isEmpty() ? 0.0 : Double.parseDouble(aux[3]);
            String observaciones = aux[4];

            lista.add(new DetalleVenta(idVenta, idganado, precioIndividual, pesoMomentoVenta, observaciones));
        }

        return lista;
    }

    /**
     * Escribe una lista completa al archivo CSV. Sobrescribe el contenido anterior.
     * Incluye el header como primera línea.
     *
     * @param lista Lista de detalles de venta a persistir
     */
    public void guardarTodo(List<DetalleVenta> lista) {
        List<String[]> lineasEscribir = new ArrayList<>();
        lineasEscribir.add(new String[]{"idVenta", "idganado", "precioIndividual", "pesoMomentoVenta", "observaciones"});

        for (int i = 0; i < lista.size(); i++) {
            String idVenta = String.valueOf(lista.get(i).getIdVenta());
            String idganado = String.valueOf(lista.get(i).getIdganado());
            String precio = String.format("%.2f", lista.get(i).getPrecioIndividual());
            String peso = String.format("%.2f", lista.get(i).getPesoMomentoVenta());
            String observaciones = lista.get(i).getObservaciones();

            lineasEscribir.add(new String[]{idVenta, idganado, precio, peso, observaciones});
        }

        CsvUtil.escribirCsv(RUTA_ARCHIVO, lineasEscribir);
    }

    /**
     * Agrega un nuevo detalle al archivo CSV.
     *
     * @param nuevoDetalle Detalle a insertar
     */
    public void insertarDetalle(DetalleVenta nuevoDetalle) {
        List<DetalleVenta> lista = cargarTodo();
        lista.add(nuevoDetalle);
        guardarTodo(lista);
    }

    /**
     * Reemplaza un detalle existente por una versión actualizada.
     *
     * @param detalleActualizado Detalle con los datos actualizados
     */
    public void actualizarDetalle(DetalleVenta detalleActualizado) {
        List<DetalleVenta> lista = cargarTodo();

        for (int i = 0; i < lista.size(); i++) {
            DetalleVenta antiguo = lista.get(i);
            if (antiguo.getIdVenta() == detalleActualizado.getIdVenta()
                    && antiguo.getIdganado() == detalleActualizado.getIdganado()) {
                lista.set(i, detalleActualizado);
                break;
            }
        }

        guardarTodo(lista);
    }

    /**
     * Elimina un detalle del archivo CSV.
     *
     * @param idVenta Identificador de la venta
     * @param idganado Identificador del ganado
     */
    public void eliminarDetalle(int idVenta, int idganado) {
        List<DetalleVenta> lista = cargarTodo();
        lista.removeIf(d -> d.getIdVenta() == idVenta && d.getIdganado() == idganado);
        guardarTodo(lista);
    }
}
