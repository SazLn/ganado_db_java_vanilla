package co.upc.ganado.datos;

import co.upc.ganado.entidades.Vacuna;  // Clase de dominio que esta capa persiste
import java.util.List;                 // Interface para retornar colecciones
import java.util.ArrayList;            // Implementación concreta de Lista

/**
 * Capa de acceso a datos para la entidad Vacuna. Gestiona las operaciones
 * CRUD sobre el archivo data/vacuna.csv usando CsvUtil como intermediario.
 *
 * @author Santiago Rafael Zuleta Neira
 * @version 1.0
 * @since 2026-05-16
 */
public class VacunaData {
    private final String RUTA_ARCHIVO = "data/vacuna.csv";

    //MÉTODOS

    /**
     * Lee el archivo CSV de vacunas y convierte cada línea en un objeto Vacuna.
     * Omite la primera línea (header). Si el archivo no existe, retorna lista vacía.
     *
     * @return Lista de todas las vacunas almacenadas en el archivo CSV
     */
    public List<Vacuna> cargarTodo() {
        //Lista de instancias.
        List<Vacuna> listaVacunas = new ArrayList<>();
        
        //Lista de arreglos.
        List<String[]> lineasArchivo = CsvUtil.leerCsv(RUTA_ARCHIVO);
        
        //Recorrer lista de arreglos (cada línea del archivo). Inicia desde 1 para ignorar los encabezados del archivo.
        for (int i = 1; i < lineasArchivo.size(); i++) {
            //Arreglo estático auxiliar para crear la instancia de 'Finca'.
            String[] auxiliarVacuna = lineasArchivo.get(i);

            //Atributos de la instancia.
            int idVacuna = Integer.parseInt(auxiliarVacuna[0]);
            String nombreVacuna = auxiliarVacuna[1];
            String descripcion = auxiliarVacuna[2];
            String dosisEstandar = auxiliarVacuna[3];

            //Crear instancia y meterla a la lista.
            listaVacunas.add(new Vacuna(idVacuna, nombreVacuna, descripcion, dosisEstandar));
        }

        return listaVacunas;
    }

    /**
     * Escribe una lista completa de vacunas al archivo CSV. Sobrescribe el
     * contenido anterior. Incluye el header como primera línea.
     *
     * @param listaVacunas Lista de vacunas a persistir en el archivo
     */
    public void guardarTodo(List<Vacuna> listaVacunas) {
         //Arreglo para las líneas del CSV. Inicializar con la cabezera o header del archivo.
        List<String[]> lineasEscribir = new ArrayList<>();
        lineasEscribir.add(new String[]{"idVacuna", "nombreVacuna", "descripcion", "dosisEstandar"});

        //Recorrer todas las líneas a escribir.
        for (int i = 0; i < listaVacunas.size(); i++) {
            String idVacunaEscribir = String.valueOf(listaVacunas.get(i).getIdVacuna()); //Se convierte el id a 'String'.
            String nombreVacunaEscribir = listaVacunas.get(i).getNombreVacuna();
            String descripcionVacunaEscribir = listaVacunas.get(i).getDescripcion();
            String dosisEstandarEscribir = listaVacunas.get(i).getDosisEstandar();

            //Añadir a la lista de fincas a escribir.
            lineasEscribir.add(new String[]{idVacunaEscribir,nombreVacunaEscribir, descripcionVacunaEscribir, dosisEstandarEscribir});
        }

        //Escribir todo el archivo.
        CsvUtil.escribirCsv(RUTA_ARCHIVO, lineasEscribir);
    }

    /**
     * Agrega una nueva vacuna al archivo CSV. Carga los datos existentes,
     * agrega la vacuna y persiste el resultado.
     *
     * @param nuevaVacuna Vacuna a insertar
     */
    public void insertarVacuna(Vacuna nuevaVacuna) {
        //Cargar vacunas existentes.
        List<Vacuna> vacunas = cargarTodo();
        
        //Insertar nueva vacuna.
        vacunas.add(nuevaVacuna);
        
        //Guardar cambios.
        guardarTodo(vacunas);
    }

    /**
     * Reemplaza una vacuna existente por una versión actualizada. Busca por id,
     * reemplaza en la lista y persiste los cambios.
     *
     * @param vacunaActualizada Vacuna con los datos actualizados
     */
    public void actualizarVacuna(Vacuna vacunaActualizada) {
        //Cargar vacunas existentes.
        List<Vacuna> vacunas = cargarTodo();

        //Recorre la lista de fincas hasta encontrar una coincidencia.
        for (int i = 0; i < vacunas.size(); i++) {
            Vacuna vacunaAntigua = vacunas.get(i);

            if (vacunaAntigua.getIdVacuna()== vacunaActualizada.getIdVacuna()) {
                vacunas.set(i, vacunaActualizada);
                break;
            }
        }

        //Guardar cambios.
        guardarTodo(vacunas);
        
        
    }

    /**
     * Elimina una vacuna del archivo CSV buscando por su id. Carga los datos,
     * remueve la vacuna y persiste el resultado.
     *
     * @param idVacuna Identificador de la vacuna a eliminar
     */
    public void eliminarVacuna(int idVacuna) {
        //Carga todas las fincas existentes.
        List<Vacuna> vacunas = cargarTodo();
        
        //Recorre la lista en busca de coincidencias.
        vacunas.removeIf(v -> v.getIdVacuna()== idVacuna);
        
        //Guardar cambios.
        guardarTodo(vacunas);
    }
}
