package co.upc.ganado.datos;

import co.upc.ganado.entidades.Traslado;
import co.upc.ganado.entidades.enums.EnumMotivoTraslado;
import java.util.List;
import java.util.ArrayList;

/**
 * Capa de acceso a datos para la entidad Traslado. Gestiona las operaciones
 * CRUD sobre el archivo data/traslado.csv usando CsvUtil como intermediario.
 *
 * @author Santiago Rafael Zuleta Neira
 * @version 1.0
 * @since 2026-05-16
 */
public class TrasladoData {
    private final String RUTA_ARCHIVO = "data/traslado.csv";

    //MÉTODOS
    /**
     * Lee el archivo CSV y convierte cada línea en un objeto Traslado.
     * Omite la primera línea (header). Si el archivo no existe, retorna lista vacía.
     *
     * @return Lista de todos los traslados almacenados en el archivo CSV
     */
    public List<Traslado> cargarTodo() {
        List<Traslado> lista = new ArrayList<>();
        List<String[]> lineasArchivo = CsvUtil.leerCsv(RUTA_ARCHIVO);

        for (int i = 1; i < lineasArchivo.size(); i++) {
            String[] aux = lineasArchivo.get(i);
            int idTraslado = aux[0].isEmpty() ? 0 : Integer.parseInt(aux[0]);
            String fechaTraslado = aux[1];
            EnumMotivoTraslado motivoTraslado = aux[2].isEmpty() ? null : EnumMotivoTraslado.valueOf(aux[2]); //Sensible a mayúsculas y minúsculas
            String medioTransporte = aux[3];
            String responsableTraslado = aux[4];
            double costoTraslado = aux[5].isEmpty() ? 0.0 : Double.parseDouble(aux[5]);
            int idFincaOrigen = aux[6].isEmpty() ? 0 : Integer.parseInt(aux[6]);
            int idFincaDestino = aux[7].isEmpty() ? 0 : Integer.parseInt(aux[7]);

            lista.add(new Traslado(idTraslado, fechaTraslado, motivoTraslado, medioTransporte, responsableTraslado, costoTraslado, idFincaOrigen, idFincaDestino));
        }

        return lista;
    }

    /**
     * Escribe una lista completa al archivo CSV. Sobrescribe el contenido anterior.
     * Incluye el header como primera línea.
     *
     * @param lista Lista de traslados a persistir
     */
    public void guardarTodo(List<Traslado> lista) {
        List<String[]> lineasEscribir = new ArrayList<>();
        lineasEscribir.add(new String[]{"idTraslado", "fechaTraslado", "motivoTraslado", "medioTransporte", "responsableTraslado", "costoTraslado", "idFincaOrigen", "idFincaDestino"});

        for (int i = 0; i < lista.size(); i++) {
            String idTraslado = String.valueOf(lista.get(i).getIdTraslado());
            String fechaTraslado = lista.get(i).getFechaTraslado();
            String motivoTraslado = lista.get(i).getMotivoTraslado() != null ? lista.get(i).getMotivoTraslado().toString() : "";
            String medioTransporte = lista.get(i).getMedioTransporte();
            String responsableTraslado = lista.get(i).getResponsableTraslado();
            String costoTraslado = String.format("%.2f", lista.get(i).getCostoTraslado());
            String idFincaOrigen = String.valueOf(lista.get(i).getIdFincaOrigen());
            String idFincaDestino = String.valueOf(lista.get(i).getIdFincaDestino());

            lineasEscribir.add(new String[]{idTraslado, fechaTraslado, motivoTraslado, medioTransporte, responsableTraslado, costoTraslado, idFincaOrigen, idFincaDestino});
        }

        CsvUtil.escribirCsv(RUTA_ARCHIVO, lineasEscribir);
    }

    /**
     * Agrega un nuevo traslado al archivo CSV.
     *
     * @param nuevoTraslado Traslado a insertar
     */
    public void insertarTraslado(Traslado nuevoTraslado) {
        List<Traslado> lista = cargarTodo();
        lista.add(nuevoTraslado);
        guardarTodo(lista);
    }

    /**
     * Reemplaza un traslado existente por una versión actualizada.
     *
     * @param trasladoActualizado Traslado con los datos actualizados
     */
    public void actualizarTraslado(Traslado trasladoActualizado) {
        List<Traslado> lista = cargarTodo();

        for (int i = 0; i < lista.size(); i++) {
            Traslado antiguo = lista.get(i);
            if (antiguo.getIdTraslado() == trasladoActualizado.getIdTraslado()) {
                lista.set(i, trasladoActualizado);
                break;
            }
        }

        guardarTodo(lista);
    }

    /**
     * Elimina un traslado del archivo CSV.
     *
     * @param idTraslado Identificador del traslado
     */
    public void eliminarTraslado(int idTraslado) {
        List<Traslado> lista = cargarTodo();
        lista.removeIf(t -> t.getIdTraslado() == idTraslado);
        guardarTodo(lista);
    }
}
