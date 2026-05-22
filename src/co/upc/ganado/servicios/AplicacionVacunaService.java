package co.upc.ganado.servicios;

import co.upc.ganado.entidades.AplicacionVacuna;
import co.upc.ganado.datos.AplicacionVacunaData;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar la logica de negocio de las aplicaciones de vacuna.
 * 
 * @author Santiago Rafael Zuleta Neira
 */
public class AplicacionVacunaService {

    private List<AplicacionVacuna> listaAplicaciones;
    private AplicacionVacunaData data;

    //Constructor
    public AplicacionVacunaService() {
        this.data = new AplicacionVacunaData();
        this.listaAplicaciones = data.cargarTodo();
    }

    //Guarda los cambios en el archivo.
    private void guardar() {
        data.guardarTodo(listaAplicaciones);
    }

    
    // ===== CRUD =====
    

    public void insertar(AplicacionVacuna nuevaAplicacion) {
        listaAplicaciones.add(nuevaAplicacion);
        guardar();
    }

    
    //Elimina un registro del archivo (clave triple: idGanado, idVacuna, fechaAplicacion).
    public void eliminar(int idGanado, int idVacuna, String fechaAplicacion) {
        boolean eliminado = listaAplicaciones.removeIf(
            a -> a.getIdGanado() == idGanado
              && a.getIdVacuna() == idVacuna
              && a.getFechaAplicacion().equals(fechaAplicacion)
        );
        if (eliminado) {
            guardar();
        }
    }

    //Busca una aplicacion especifica por clave triple.
    public AplicacionVacuna buscarPorId(int idGanado, int idVacuna, String fechaAplicacion) {
        return listaAplicaciones.stream()
                .filter(a -> a.getIdGanado() == idGanado
                          && a.getIdVacuna() == idVacuna
                          && a.getFechaAplicacion().equals(fechaAplicacion))
                .findFirst()
                .orElse(null);
    }

    //Retorna todas las aplicaciones de una misma vacuna.
    public List<AplicacionVacuna> buscarPorVacuna(int idVacuna) {
        return listaAplicaciones.stream()
                .filter(a -> a.getIdVacuna() == idVacuna)
                .collect(Collectors.toList());
    }

    //Retorna todas las aplicaciones recibidas por un mismo animal.
    public List<AplicacionVacuna> buscarPorGanado(int idGanado) {
        return listaAplicaciones.stream()
                .filter(a -> a.getIdGanado() == idGanado)
                .collect(Collectors.toList());
    }

    
    public List<AplicacionVacuna> mostrarTodo() {
        return this.listaAplicaciones;
    }

    //Actualiza un registro del archivo (clave triple).
    public void actualizarDetalle(AplicacionVacuna aplicacionActualizada) {
        int idGanado = aplicacionActualizada.getIdGanado();
        int idVacuna = aplicacionActualizada.getIdVacuna();
        String fecha = aplicacionActualizada.getFechaAplicacion();

        for (int i = 0; i < listaAplicaciones.size(); i++) {
            AplicacionVacuna a = listaAplicaciones.get(i);
            if (a.getIdGanado() == idGanado
                    && a.getIdVacuna() == idVacuna
                    && a.getFechaAplicacion().equals(fecha)) {
                listaAplicaciones.set(i, aplicacionActualizada);
                guardar();
                return;
            }
        }
    }
}
