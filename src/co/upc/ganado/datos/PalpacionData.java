package co.upc.ganado.datos;

import co.upc.ganado.entidades.Palpacion;
import co.upc.ganado.entidades.enums.EnumTipoPalpacion;
import java.util.List;
import java.util.ArrayList;

/**
 * Capa de acceso a datos para la entidad Palpacion. Gestiona las operaciones
 * CRUD sobre el archivo data/palpacion.csv usando CsvUtil como intermediario.
 *
 * @author Santiago Rafael Zuleta Neira
 * @version 1.0
 * @since 2026-05-16
 */
public class PalpacionData {
    private final String RUTA_ARCHIVO = "data/palpacion.csv";

    //MÉTODOS
    /**
     * Lee el archivo CSV y convierte cada línea en un objeto Palpacion.
     * Omite la primera línea (header). Si el archivo no existe, retorna lista vacía.
     *
     * @return Lista de todas las palpaciones almacenadas en el archivo CSV
     */
    public List<Palpacion> cargarTodo() {
        List<Palpacion> lista = new ArrayList<>();
        List<String[]> lineasArchivo = CsvUtil.leerCsv(RUTA_ARCHIVO);

        for (int i = 1; i < lineasArchivo.size(); i++) {
            String[] aux = lineasArchivo.get(i);
            int idPalpacion = aux[0].isEmpty() ? 0 : Integer.parseInt(aux[0]);
            String fechaPalpacion = aux[1];
            EnumTipoPalpacion tipoPalpacion = aux[2].isEmpty() ? null : EnumTipoPalpacion.valueOf(aux[2]);
            String observaciones = aux[3];

            lista.add(new Palpacion(idPalpacion, fechaPalpacion, tipoPalpacion, observaciones));
        }

        return lista;
    }

    /**
     * Escribe una lista completa al archivo CSV. Sobrescribe el contenido anterior.
     * Incluye el header como primera línea.
     *
     * @param lista Lista de palpaciones a persistir
     */
    public void guardarTodo(List<Palpacion> lista) {
        List<String[]> lineasEscribir = new ArrayList<>();
        lineasEscribir.add(new String[]{"idPalpacion", "fechaPalpacion", "tipoPalpacion", "observaciones"});

        for (int i = 0; i < lista.size(); i++) {
            String idPalpacion = String.valueOf(lista.get(i).getIdPalpacion());
            String fechaPalpacion = lista.get(i).getFechaPalpacion();
            String tipoPalpacion = lista.get(i).getTipoPalpacion() != null ? lista.get(i).getTipoPalpacion().toString() : "";
            String observaciones = lista.get(i).getObservaciones();

            lineasEscribir.add(new String[]{idPalpacion, fechaPalpacion, tipoPalpacion, observaciones});
        }

        CsvUtil.escribirCsv(RUTA_ARCHIVO, lineasEscribir);
    }

    /**
     * Agrega una nueva palpación al archivo CSV.
     *
     * @param nuevaPalpacion Palpación a insertar
     */
    public void insertarPalpacion(Palpacion nuevaPalpacion) {
        List<Palpacion> lista = cargarTodo();
        lista.add(nuevaPalpacion);
        guardarTodo(lista);
    }

    /**
     * Reemplaza una palpación existente por una versión actualizada.
     *
     * @param palpacionActualizada Palpación con los datos actualizados
     */
    public void actualizarPalpacion(Palpacion palpacionActualizada) {
        List<Palpacion> lista = cargarTodo();

        for (int i = 0; i < lista.size(); i++) {
            Palpacion antigua = lista.get(i);
            if (antigua.getIdPalpacion() == palpacionActualizada.getIdPalpacion()) {
                lista.set(i, palpacionActualizada);
                break;
            }
        }

        guardarTodo(lista);
    }

    /**
     * Elimina una palpación del archivo CSV.
     *
     * @param idPalpacion Identificador de la palpación
     */
    public void eliminarPalpacion(int idPalpacion) {
        List<Palpacion> lista = cargarTodo();
        lista.removeIf(p -> p.getIdPalpacion() == idPalpacion);
        guardarTodo(lista);
    }
}
