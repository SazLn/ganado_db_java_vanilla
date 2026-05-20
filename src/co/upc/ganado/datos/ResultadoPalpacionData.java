package co.upc.ganado.datos;

import co.upc.ganado.entidades.ResultadoPalpacion;
import co.upc.ganado.entidades.enums.EnumResultadoPalpacion;
import java.util.List;
import java.util.ArrayList;

/**
 * Capa de acceso a datos para la entidad ResultadoPalpacion. Gestiona las operaciones
 * CRUD sobre el archivo data/resultadoPalpacion.csv usando CsvUtil como intermediario.
 *
 * @author Santiago Rafael Zuleta Neira
 * @version 1.0
 * @since 2026-05-16
 */
public class ResultadoPalpacionData {
    private final String RUTA_ARCHIVO = "data/resultadoPalpacion.csv";

    //MÉTODOS
    /**
     * Lee el archivo CSV y convierte cada línea en un objeto ResultadoPalpacion.
     * Omite la primera línea (header). Si el archivo no existe, retorna lista vacía.
     *
     * @return Lista de todos los resultados de palpación almacenados en el archivo CSV
     */
    public List<ResultadoPalpacion> cargarTodo() {
        List<ResultadoPalpacion> lista = new ArrayList<>();
        List<String[]> lineasArchivo = CsvUtil.leerCsv(RUTA_ARCHIVO);

        for (int i = 1; i < lineasArchivo.size(); i++) {
            String[] aux = lineasArchivo.get(i);
            int idGanado = aux[0].isEmpty() ? 0 : Integer.parseInt(aux[0]);
            int idPalpacion = aux[1].isEmpty() ? 0 : Integer.parseInt(aux[1]);
            EnumResultadoPalpacion resultado = aux[2].isEmpty() ? null : EnumResultadoPalpacion.valueOf(aux[2]);
            String fechaConcepcionAprox = aux[3];
            String fechaPartoEstimada = aux[4];

            lista.add(new ResultadoPalpacion(idGanado, idPalpacion, resultado, fechaConcepcionAprox, fechaPartoEstimada));
        }

        return lista;
    }

    /**
     * Escribe una lista completa al archivo CSV. Sobrescribe el contenido anterior.
     * Incluye el header como primera línea.
     *
     * @param lista Lista de resultados de palpación a persistir
     */
    public void guardarTodo(List<ResultadoPalpacion> lista) {
        List<String[]> lineasEscribir = new ArrayList<>();
        lineasEscribir.add(new String[]{"idGanado", "idPalpacion", "resultados", "fechaConcepcionAprox", "fechaPartoEstimada"});

        for (int i = 0; i < lista.size(); i++) {
            String idGanado = String.valueOf(lista.get(i).getIdGanado());
            String idPalpacion = String.valueOf(lista.get(i).getIdPalpacion());
            String resultado = lista.get(i).getResultadoPalpacion() != null ? lista.get(i).getResultadoPalpacion().toString() : "";
            String fechaConcepcionAprox = lista.get(i).getFechaConcepcionAprox();
            String fechaPartoEstimada = lista.get(i).getFechaPartoEstimada();

            lineasEscribir.add(new String[]{idGanado, idPalpacion, resultado, fechaConcepcionAprox, fechaPartoEstimada});
        }

        CsvUtil.escribirCsv(RUTA_ARCHIVO, lineasEscribir);
    }

    /**
     * Agrega un nuevo resultado al archivo CSV.
     *
     * @param nuevoResultado Resultado a insertar
     */
    public void insertarResultado(ResultadoPalpacion nuevoResultado) {
        List<ResultadoPalpacion> lista = cargarTodo();
        lista.add(nuevoResultado);
        guardarTodo(lista);
    }

    /**
     * Reemplaza un resultado existente por una versión actualizada.
     *
     * @param resultadoActualizado Resultado con los datos actualizados
     */
    public void actualizarResultado(ResultadoPalpacion resultadoActualizado) {
        List<ResultadoPalpacion> lista = cargarTodo();

        for (int i = 0; i < lista.size(); i++) {
            ResultadoPalpacion antiguo = lista.get(i);
            if (antiguo.getIdGanado() == resultadoActualizado.getIdGanado()
                    && antiguo.getIdPalpacion() == resultadoActualizado.getIdPalpacion()) {
                lista.set(i, resultadoActualizado);
                break;
            }
        }

        guardarTodo(lista);
    }

    /**
     * Elimina un resultado del archivo CSV.
     *
     * @param idGanado Identificador del ganado
     * @param idPalpacion Identificador de la palpación
     */
    public void eliminarResultado(int idGanado, int idPalpacion) {
        List<ResultadoPalpacion> lista = cargarTodo();
        lista.removeIf(r -> r.getIdGanado() == idGanado && r.getIdPalpacion() == idPalpacion);
        guardarTodo(lista);
    }
}
