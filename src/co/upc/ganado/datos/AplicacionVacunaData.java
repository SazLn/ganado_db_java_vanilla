package co.upc.ganado.datos;

import co.upc.ganado.entidades.AplicacionVacuna;
import java.util.List;
import java.util.ArrayList;

/**
 * Capa de acceso a datos para la entidad AplicacionVacuna. Gestiona las operaciones
 * CRUD sobre el archivo data/aplicacionVacuna.csv usando CsvUtil como intermediario.
 *
 * @author Santiago Rafael Zuleta Neira
 * @version 1.0
 * @since 2026-05-16
 */
public class AplicacionVacunaData {
    private final String RUTA_ARCHIVO = "data/aplicacionVacuna.csv";

    //MÉTODOS
    /**
     * Lee el archivo CSV y convierte cada línea en un objeto AplicacionVacuna.
     * Omite la primera línea (header). Si el archivo no existe, retorna lista vacía.
     *
     * @return Lista de todas las aplicaciones de vacuna almacenadas en el archivo CSV
     */
    public List<AplicacionVacuna> cargarTodo() {
        List<AplicacionVacuna> lista = new ArrayList<>();
        List<String[]> lineasArchivo = CsvUtil.leerCsv(RUTA_ARCHIVO);

        for (int i = 1; i < lineasArchivo.size(); i++) {
            String[] aux = lineasArchivo.get(i);
            int idGanado = aux[0].isEmpty() ? 0 : Integer.parseInt(aux[0]);
            int idVacuna = aux[1].isEmpty() ? 0 : Integer.parseInt(aux[1]);
            String fechaAplicacion = aux[2];
            String dosisAplicada = aux[3];
            String responsableVacunacion = aux[4];

            lista.add(new AplicacionVacuna(idGanado, idVacuna, fechaAplicacion, dosisAplicada, responsableVacunacion));
        }

        return lista;
    }

    /**
     * Escribe una lista completa al archivo CSV. Sobrescribe el contenido anterior.
     * Incluye el header como primera línea.
     *
     * @param lista Lista de aplicaciones de vacuna a persistir
     */
    public void guardarTodo(List<AplicacionVacuna> lista) {
        List<String[]> lineasEscribir = new ArrayList<>();
        lineasEscribir.add(new String[]{"idGanado", "idVacuna", "fechaAplicacion", "dosisAplicada", "responsableVacunacion"});

        for (int i = 0; i < lista.size(); i++) {
            String idGanado = String.valueOf(lista.get(i).getIdGanado());
            String idVacuna = String.valueOf(lista.get(i).getIdVacuna());
            String fechaAplicacion = lista.get(i).getFechaAplicacion();
            String dosisAplicada = lista.get(i).getDosisAplicada();
            String responsableVacunacion = lista.get(i).getResponsableVacunacion();

            lineasEscribir.add(new String[]{idGanado, idVacuna, fechaAplicacion, dosisAplicada, responsableVacunacion});
        }

        CsvUtil.escribirCsv(RUTA_ARCHIVO, lineasEscribir);
    }

    /**
     * Agrega una nueva aplicación al archivo CSV.
     *
     * @param nuevaAplicacion Aplicación a insertar
     */
    public void insertarAplicacion(AplicacionVacuna nuevaAplicacion) {
        List<AplicacionVacuna> lista = cargarTodo();
        lista.add(nuevaAplicacion);
        guardarTodo(lista);
    }

    /**
     * Reemplaza una aplicación existente por una versión actualizada.
     *
     * @param aplicacionActualizada Aplicación con los datos actualizados
     */
    public void actualizarAplicacion(AplicacionVacuna aplicacionActualizada) {
        List<AplicacionVacuna> lista = cargarTodo();

        for (int i = 0; i < lista.size(); i++) {
            AplicacionVacuna antigua = lista.get(i);
            
            if (antigua.getIdGanado() == aplicacionActualizada.getIdGanado()
                    && antigua.getIdVacuna() == aplicacionActualizada.getIdVacuna()
                    && antigua.getFechaAplicacion().equals(aplicacionActualizada.getFechaAplicacion())) {
                
                lista.set(i, aplicacionActualizada);
                break;
            }
        }

        guardarTodo(lista);
    }

    /**
     * Elimina una aplicación del archivo CSV.
     *
     * @param idGanado Identificador del ganado
     * @param idVacuna Identificador de la vacuna
     * @param fechaAplicacion Fecha de aplicación
     */
    public void eliminarAplicacion(int idGanado, int idVacuna, String fechaAplicacion) {
        List<AplicacionVacuna> lista = cargarTodo();
        lista.removeIf(a -> a.getIdGanado() == idGanado
                && a.getIdVacuna() == idVacuna
                && a.getFechaAplicacion().equals(fechaAplicacion));
        
        guardarTodo(lista);
    }
}
