package co.upc.ganado.datos;

import co.upc.ganado.entidades.Finca;  // Clase de dominio que esta capa persiste
import java.util.List;                 // Interface para retornar colecciones
import java.util.ArrayList;            // Implementación concreta de Lista

/**
 * Capa de acceso a datos para la entidad Finca. Gestiona las operaciones
 * CRUD sobre el archivo data/finca.csv usando CsvUtil como intermediario.
 *
 * @author Santiago Rafael Zuleta Neira
 * @version 1.0
 * @since 2026-05-16
 */
public class FincaData {

    private final String RUTA_ARCHIVO = "data/finca.csv";

    //MÉTODOS
    /**
     * Lee el archivo CSV de fincas y convierte cada línea en un objeto Finca.
     * Omite la primera línea (header). Si el archivo no existe, retorna lista
     * vacía.
     *
     * @return Lista de todas las fincas almacenadas en el archivo CSV
     */
    public List<Finca> cargarTodo() {
        //Lista de instancias de 'Finca'.
        List<Finca> listaFincas = new ArrayList<>();

        //Obtiene lista de arreglos. Líneas del archivo parseadas.
        List<String[]> lineasArchivo = CsvUtil.leerCsv(RUTA_ARCHIVO);

        //Recorrer lista de arreglos (cada línea del archivo). Inicia desde 1 para ignorar los encabezados del archivo.
        for (int i = 1; i < lineasArchivo.size(); i++) {
            //Arreglo estático auxiliar para crear la instancia de 'Finca'.
            String[] auxiliarFinca = lineasArchivo.get(i);

            //Atributos de la instancia.
            int idFinca = Integer.parseInt(auxiliarFinca[0]);
            String nombreFinca = auxiliarFinca[1];
            String ubicacion = auxiliarFinca[2];

            //Crear instancia y meterla a la lista.
            listaFincas.add(new Finca(idFinca, nombreFinca, ubicacion));
        }

        return listaFincas;
    }

    /**
     * Escribe una lista completa de fincas al archivo CSV. Sobrescribe el
     * contenido anterior. Incluye el header como primera línea.
     *
     * @param listaFincas Lista de fincas a persistir en el archivo
     */
    public void guardarTodo(List<Finca> listaFincas) {
        //Arreglo para las líneas del CSV. Inicializar con la cabezera o header del archivo.
        List<String[]> lineasEscribir = new ArrayList<>();
        lineasEscribir.add(new String[]{"idFinca", "nombreFinca", "ubicacion"});

        //Recorrer todas las líneas a escribir.
        for (int i = 0; i < listaFincas.size(); i++) {
            String idFincaEscribir = String.valueOf(listaFincas.get(i).getIdFinca()); //Se convierte el id a 'String'.
            String nombreFincaEscribir = listaFincas.get(i).getNombreFinca();
            String ubicacionFincaEscribir = listaFincas.get(i).getUbicacion();

            //Añadir a la lista de fincas a escribir.
            lineasEscribir.add(new String[]{idFincaEscribir, nombreFincaEscribir, ubicacionFincaEscribir});
        }

        //Escribir todo el archivo.
        CsvUtil.escribirCsv(RUTA_ARCHIVO, lineasEscribir);
    }

    /**
     * Agrega una nueva finca al archivo CSV. Carga los datos existentes, agrega
     * la finca y persiste el resultado.
     *
     * @param nuevaFinca Finca a insertar
     */
    public void insertarFinca(Finca nuevaFinca) {
        //Cargar fincas existentes.
        List<Finca> fincas = cargarTodo();

        //Añadir la nueva finca.
        fincas.add(nuevaFinca);

        //Guarda los cambios.
        guardarTodo(fincas);
    }

    /**
     * Reemplaza una finca existente por una versión actualizada. Busca por id,
     * reemplaza en la lista y persiste los cambios.
     *
     * @param fincaActualizada Finca con los datos actualizados
     */
    public void actualizarFinca(Finca fincaActualizada) {
        //Cargar fincas existentes.
        List<Finca> fincas = cargarTodo();

        //Recorre la lista de fincas hasta encontrar una coincidencia.
        for (int i = 0; i < fincas.size(); i++) {
            Finca fincaAntigua = fincas.get(i);

            if (fincaAntigua.getIdFinca() == fincaActualizada.getIdFinca()) {
                fincas.set(i, fincaActualizada);
                break;
            }
        }

        //Guardar cambios.
        guardarTodo(fincas);
    }

    
    /**
     * Elimina una finca del archivo CSV buscando por su id. Carga los datos,
     * remueve la finca y persiste el resultado.
     *
     * @param idFinca Identificador de la finca a eliminar
     */
    public void eliminarFinca(int idFinca) {
        //Carga todas las fincas existentes.
        List<Finca> fincas = cargarTodo();
        
        //Recorre la lista en busca de coincidencias.
        fincas.removeIf(f -> f.getIdFinca() == idFinca);
        
        //Guardar cambios.
        guardarTodo(fincas);
    }
}
