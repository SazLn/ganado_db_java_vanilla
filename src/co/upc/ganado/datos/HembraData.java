package co.upc.ganado.datos;

import co.upc.ganado.entidades.Hembra;
import co.upc.ganado.entidades.enums.EnumSexo;
import co.upc.ganado.entidades.enums.EnumEstadoSalud;
import co.upc.ganado.entidades.enums.EnumEstadoVida;
import co.upc.ganado.entidades.enums.EnumMotivoSalida;
import co.upc.ganado.entidades.enums.EnumEstadoReproductivo;
import java.util.List;
import java.util.ArrayList;

/**
 * Capa de acceso a datos para la entidad Hembra. Gestiona las operaciones
 * CRUD sobre el archivo data/hembra.csv usando CsvUtil como intermediario.
 * El CSV contiene todos los campos (base de Ganado + específicos de Hembra).
 *
 * @author Santiago Rafael Zuleta Neira
 * @version 1.0
 * @since 2026-05-16
 */
public class HembraData {
    private final String RUTA_ARCHIVO = "data/hembra.csv";

    //MÉTODOS
    /**
     * Lee el archivo CSV y convierte cada línea en un objeto Hembra.
     * Omite la primera línea (header). Si el archivo no existe, retorna lista vacía.
     *
     * @return Lista de todas las hembras almacenadas en el archivo CSV
     */
    public List<Hembra> cargarTodo() {
        List<Hembra> lista = new ArrayList<>();
        List<String[]> lineasArchivo = CsvUtil.leerCsv(RUTA_ARCHIVO);

        for (int i = 1; i < lineasArchivo.size(); i++) {
            String[] aux = lineasArchivo.get(i);
            // Campos heredados de Ganado
            int idGanado = aux[0].isEmpty() ? 0 : Integer.parseInt(aux[0]);
            String numeroMarca = aux[1];
            String fechaNacimiento = aux[2];
            EnumSexo tipoSexo = aux[3].isEmpty() ? null : EnumSexo.valueOf(aux[3]);
            double peso = aux[4].isEmpty() ? 0.0 : Double.parseDouble(aux[4]);
            EnumEstadoSalud estadoSalud = aux[5].isEmpty() ? null : EnumEstadoSalud.valueOf(aux[5]);
            EnumEstadoVida estadoVida = aux[6].isEmpty() ? null : EnumEstadoVida.valueOf(aux[6]);
            String fechaSalida = aux[7];
            EnumMotivoSalida motivoSalida = aux[8].isEmpty() ? null : EnumMotivoSalida.valueOf(aux[8]);
            int idFinca = aux[9].isEmpty() ? 0 : Integer.parseInt(aux[9]);
            int idMadre = aux[10].isEmpty() ? 0 : Integer.parseInt(aux[10]);
            // Campos propios de Hembra
            EnumEstadoReproductivo estadoReproductivoActual = aux[11].isEmpty() ? null : EnumEstadoReproductivo.valueOf(aux[11]);
            String fechaUltimoParto = aux[12];
            int numeroPartos = aux[13].isEmpty() ? 0 : Integer.parseInt(aux[13]);
            boolean aptaParaReproduccion = aux[14].isEmpty() ? false : Boolean.parseBoolean(aux[14]);
            
            //Si hay atributos nulos, se usa el  constructor corto.
            if (fechaNacimiento.isEmpty() && fechaSalida.isEmpty() && motivoSalida == null && idMadre == 0) {
                lista.add(new Hembra(estadoReproductivoActual, fechaUltimoParto, numeroPartos, aptaParaReproduccion,
                        idGanado, numeroMarca, tipoSexo, peso, estadoSalud, idFinca));
            } else {
                //En otro caso se usa el constructor completo.
                lista.add(new Hembra(estadoReproductivoActual, fechaUltimoParto, numeroPartos, aptaParaReproduccion,
                        idGanado, numeroMarca, fechaNacimiento, tipoSexo, peso, estadoSalud,
                        estadoVida, fechaSalida, motivoSalida, idFinca, idMadre));
            }
        }

        return lista;
    }

    /**
     * Escribe una lista completa al archivo CSV. Sobrescribe el contenido anterior.
     * Incluye el header como primera línea.
     *
     * @param lista Lista de hembras a persistir
     */
    public void guardarTodo(List<Hembra> lista) {
        List<String[]> lineasEscribir = new ArrayList<>();
        lineasEscribir.add(new String[]{"idGanado", "numeroMarca", "fechaNacimiento", "tipoSexo", "peso",
                "estadoSalud", "estadoVida", "fechaSalida", "motivoSalida", "idFinca", "idMadre",
                "estadoReproductivoActual", "fechaUltimoParto", "numeroPartos", "apraParaReproduccion"});

        for (int i = 0; i < lista.size(); i++) {
            Hembra h = lista.get(i);
            String idGanado = String.valueOf(h.getIdGanado());
            String numeroMarca = h.getNumeroMarca();
            String fechaNacimiento = h.getFechaNacimiento() != null ? h.getFechaNacimiento() : "";
            String tipoSexo = h.getTipoSexo() != null ? h.getTipoSexo().toString() : "";
            String peso = String.format("%.2f", h.getPeso());
            String estadoSalud = h.getEstadoSalud() != null ? h.getEstadoSalud().toString() : "";
            String estadoVida = h.getEstadoVida() != null ? h.getEstadoVida().toString() : "";
            String fechaSalida = h.getFechaSalida() != null ? h.getFechaSalida() : "";
            String motivoSalida = h.getMotivoSalida() != null ? h.getMotivoSalida().toString() : "";
            String idFinca = String.valueOf(h.getIdFinca());
            String idMadre = h.getIdMadre() != 0 ? String.valueOf(h.getIdMadre()) : "";
            String estadoReproductivoActual = h.getEstadoReproductivoActual() != null ? h.getEstadoReproductivoActual().toString() : "";
            String fechaUltimoParto = h.getFechaUltimoParto() != null ? h.getFechaUltimoParto() : "";
            String numeroPartos = String.valueOf(h.getNumeroPartos());
            String aptaParaReproduccion = String.valueOf(h.isAptaParaReproduccion());

            lineasEscribir.add(new String[]{idGanado, numeroMarca, fechaNacimiento, tipoSexo, peso,
                    estadoSalud, estadoVida, fechaSalida, motivoSalida, idFinca, idMadre,
                    estadoReproductivoActual, fechaUltimoParto, numeroPartos, aptaParaReproduccion});
        }

        CsvUtil.escribirCsv(RUTA_ARCHIVO, lineasEscribir);
    }

    /**
     * Agrega una nueva hembra al archivo CSV.
     *
     * @param nuevaHembra Hembra a insertar
     */
    public void insertarHembra(Hembra nuevaHembra) {
        List<Hembra> lista = cargarTodo();
        lista.add(nuevaHembra);
        guardarTodo(lista);
    }

    /**
     * Reemplaza una hembra existente por una versión actualizada.
     *
     * @param hembraActualizada Hembra con los datos actualizados
     */
    public void actualizarHembra(Hembra hembraActualizada) {
        List<Hembra> lista = cargarTodo();

        for (int i = 0; i < lista.size(); i++) {
            Hembra antigua = lista.get(i);
            if (antigua.getIdGanado() == hembraActualizada.getIdGanado()) {
                lista.set(i, hembraActualizada);
                break;
            }
        }

        guardarTodo(lista);
    }

    /**
     * Elimina una hembra del archivo CSV buscando por su id.
     *
     * @param idGanado Identificador de la hembra a eliminar
     * @return true si se eliminó, false si no se encontró
     */
    public boolean eliminarHembra(int idGanado) {
        List<Hembra> lista = cargarTodo();
        boolean eliminado = lista.removeIf(h -> h.getIdGanado() == idGanado);
        
        //Si se eliminó un registro se guardan los cambios.
        if (eliminado) {
            guardarTodo(lista);
        }
        
        return eliminado;
    }
}
