package co.upc.ganado.datos;

import co.upc.ganado.entidades.Macho;
import co.upc.ganado.entidades.enums.EnumSexo;
import co.upc.ganado.entidades.enums.EnumEstadoSalud;
import co.upc.ganado.entidades.enums.EnumEstadoVida;
import co.upc.ganado.entidades.enums.EnumMotivoSalida;
import co.upc.ganado.entidades.enums.EnumCalidadReproductiva;
import java.util.List;
import java.util.ArrayList;

/**
 * Capa de acceso a datos para la entidad Macho. Gestiona las operaciones
 * CRUD sobre el archivo data/macho.csv usando CsvUtil como intermediario.
 * El CSV contiene todos los campos (base de Ganado + específicos de Macho).
 *
 * @author Santiago Rafael Zuleta Neira
 * @version 1.0
 * @since 2026-05-16
 */
public class MachoData {
    private final String RUTA_ARCHIVO = "data/macho.csv";

    //MÉTODOS
    /**
     * Lee el archivo CSV y convierte cada línea en un objeto Macho.
     * Omite la primera línea (header). Si el archivo no existe, retorna lista vacía.
     *
     * @return Lista de todos los machos almacenados en el archivo CSV
     */
    public List<Macho> cargarTodo() {
        List<Macho> lista = new ArrayList<>();
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
            // Campos propios de Macho
            EnumCalidadReproductiva calidadReproductiva = aux[11].isEmpty() ? null : EnumCalidadReproductiva.valueOf(aux[11]);
            boolean esPadrote = aux[12].isEmpty() ? false : Boolean.parseBoolean(aux[12]);
            String fechaInicioPadrote = aux[13];
            String fechaFinPadrote = aux[14];

            if (fechaNacimiento.isEmpty() && fechaSalida.isEmpty() && motivoSalida == null && idMadre == 0) {
                lista.add(new Macho(calidadReproductiva, esPadrote, fechaInicioPadrote, fechaFinPadrote,
                        idGanado, numeroMarca, tipoSexo, peso, estadoSalud, idFinca));
            } else {
                lista.add(new Macho(calidadReproductiva, esPadrote, fechaInicioPadrote, fechaFinPadrote,
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
     * @param lista Lista de machos a persistir
     */
    public void guardarTodo(List<Macho> lista) {
        List<String[]> lineasEscribir = new ArrayList<>();
        lineasEscribir.add(new String[]{"idGanado", "numeroMarca", "fechaNacimiento", "tipoSexo", "peso",
                "estadoSalud", "estadoVida", "fechaSalida", "motivoSalida", "idFinca", "idMadre",
                "calidadReproductiva", "esPadrote", "fechaInicioPadrote", "fechaFinPadrote"});

        for (int i = 0; i < lista.size(); i++) {
            Macho m = lista.get(i);
            String idGanado = String.valueOf(m.getIdGanado());
            String numeroMarca = m.getNumeroMarca();
            String fechaNacimiento = m.getFechaNacimiento() != null ? m.getFechaNacimiento() : "";
            String tipoSexo = m.getTipoSexo() != null ? m.getTipoSexo().toString() : "";
            String peso = String.format("%.2f", m.getPeso());
            String estadoSalud = m.getEstadoSalud() != null ? m.getEstadoSalud().toString() : "";
            String estadoVida = m.getEstadoVida() != null ? m.getEstadoVida().toString() : "";
            String fechaSalida = m.getFechaSalida() != null ? m.getFechaSalida() : "";
            String motivoSalida = m.getMotivoSalida() != null ? m.getMotivoSalida().toString() : "";
            String idFinca = String.valueOf(m.getIdFinca());
            String idMadre = m.getIdMadre() != 0 ? String.valueOf(m.getIdMadre()) : "";
            String calidadReproductiva = m.getCalidadReproductiva() != null ? m.getCalidadReproductiva().toString() : "";
            String esPadrote = String.valueOf(m.isEsPadrote());
            String fechaInicioPadrote = m.getFechaInicioPadrote() != null ? m.getFechaInicioPadrote() : "";
            String fechaFinPadrote = m.getFechaFinPadrote() != null ? m.getFechaFinPadrote() : "";

            lineasEscribir.add(new String[]{idGanado, numeroMarca, fechaNacimiento, tipoSexo, peso,
                    estadoSalud, estadoVida, fechaSalida, motivoSalida, idFinca, idMadre,
                    calidadReproductiva, esPadrote, fechaInicioPadrote, fechaFinPadrote});
        }

        CsvUtil.escribirCsv(RUTA_ARCHIVO, lineasEscribir);
    }

    /**
     * Agrega un nuevo macho al archivo CSV.
     *
     * @param nuevoMacho Macho a insertar
     */
    public void insertarMacho(Macho nuevoMacho) {
        List<Macho> lista = cargarTodo();
        lista.add(nuevoMacho);
        guardarTodo(lista);
    }

    /**
     * Reemplaza un macho existente por una versión actualizada.
     *
     * @param machoActualizado Macho con los datos actualizados
     */
    public void actualizarMacho(Macho machoActualizado) {
        List<Macho> lista = cargarTodo();

        for (int i = 0; i < lista.size(); i++) {
            Macho antiguo = lista.get(i);
            if (antiguo.getIdGanado() == machoActualizado.getIdGanado()) {
                lista.set(i, machoActualizado);
                break;
            }
        }

        guardarTodo(lista);
    }

    /**
     * Elimina un macho del archivo CSV buscando por su id.
     *
     * @param idGanado Identificador del macho a eliminar
     * @return true si se eliminó, false si no se encontró
     */
    public boolean eliminarMacho(int idGanado) {
        List<Macho> lista = cargarTodo();
        boolean eliminado = lista.removeIf(m -> m.getIdGanado() == idGanado);
        
        //Si se eliminó un registro se guardan los cambios.
        if (eliminado) {
            guardarTodo(lista);
        }
        
        return eliminado;
    }
}
