package co.upc.ganado.datos;

import co.upc.ganado.entidades.DetalleTraslado;
import java.util.List;
import java.util.ArrayList;

/**
 * Capa de acceso a datos para la entidad DetalleTraslado. Gestiona las operaciones
 * CRUD sobre el archivo data/detalleTraslado.csv usando CsvUtil como intermediario.
 *
 * @author Santiago Rafael Zuleta Neira
 * @version 1.0
 * @since 2026-05-16
 */
public class DetalleTrasladoData {
    private final String RUTA_ARCHIVO = "data/detalleTraslado.csv";

    //MÉTODOS
    /**
     * Lee el archivo CSV y convierte cada línea en un objeto DetalleTraslado.
     * Omite la primera línea (header). Si el archivo no existe, retorna lista vacía.
     *
     * @return Lista de todos los detalles de traslado almacenados en el archivo CSV
     */
    public List<DetalleTraslado> cargarTodo() {
        List<DetalleTraslado> lista = new ArrayList<>();
        List<String[]> lineasArchivo = CsvUtil.leerCsv(RUTA_ARCHIVO);

        for (int i = 1; i < lineasArchivo.size(); i++) {
            String[] aux = lineasArchivo.get(i);
            int idTraslado = Integer.parseInt(aux[0]);
            int idGanado = Integer.parseInt(aux[1]);
            String observaciones = aux[2];

            lista.add(new DetalleTraslado(idTraslado, idGanado, observaciones));
        }

        return lista;
    }

    /**
     * Escribe una lista completa al archivo CSV. Sobrescribe el contenido anterior.
     * Incluye el header como primera línea.
     *
     * @param lista Lista de detalles de traslado a persistir
     */
    public void guardarTodo(List<DetalleTraslado> lista) {
        List<String[]> lineasEscribir = new ArrayList<>();
        lineasEscribir.add(new String[]{"idTraslado", "idGanado", "observaciones"});

        for (int i = 0; i < lista.size(); i++) {
            String idTraslado = String.valueOf(lista.get(i).getIdTraslado());
            String idGanado = String.valueOf(lista.get(i).getIdGanado());
            String observaciones = lista.get(i).getObservaciones();

            lineasEscribir.add(new String[]{idTraslado, idGanado, observaciones});
        }

        CsvUtil.escribirCsv(RUTA_ARCHIVO, lineasEscribir);
    }

    /**
     * Agrega un nuevo detalle al archivo CSV.
     *
     * @param nuevoDetalle Detalle a insertar
     */
    public void insertarDetalle(DetalleTraslado nuevoDetalle) {
        List<DetalleTraslado> lista = cargarTodo();
        lista.add(nuevoDetalle);
        guardarTodo(lista);
    }

    /**
     * Reemplaza un detalle existente por una versión actualizada. Busca por idTraslado e idGanado.
     *
     * @param detalleActualizado Detalle con los datos actualizados
     */
    public void actualizarDetalle(DetalleTraslado detalleActualizado) {
        List<DetalleTraslado> lista = cargarTodo();

        for (int i = 0; i < lista.size(); i++) {
            DetalleTraslado antiguo = lista.get(i);
            if (antiguo.getIdTraslado() == detalleActualizado.getIdTraslado()
                    && antiguo.getIdGanado() == detalleActualizado.getIdGanado()) {
                lista.set(i, detalleActualizado);
                break;
            }
        }

        guardarTodo(lista);
    }

    /**
     * Elimina un detalle del archivo CSV buscando por idTraslado e idGanado.
     *
     * @param idTraslado Identificador del traslado
     * @param idGanado Identificador del ganado
     */
    public void eliminarDetalle(int idTraslado, int idGanado) {
        List<DetalleTraslado> lista = cargarTodo();
        lista.removeIf(d -> d.getIdTraslado() == idTraslado && d.getIdGanado() == idGanado);
        guardarTodo(lista);
    }
}
