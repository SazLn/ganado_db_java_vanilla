package co.upc.ganado.servicios;

import co.upc.ganado.entidades.ResultadoPalpacion;
import co.upc.ganado.datos.ResultadoPalpacionData;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar la l\u00f3gica de negocio de los resultados de palpaci\u00f3n.
 * 
 * @author Santiago Rafael Zuleta Neira
 */
public class ResultadoPalpacionService {

    private List<ResultadoPalpacion> listaResultados;
    private ResultadoPalpacionData data;

    //Constructor
    public ResultadoPalpacionService(ResultadoPalpacionData data) {
        this.data = data;
        this.listaResultados = data.cargarTodo();
    }

    //Guarda los cambios en el archivo.
    private void guardar() {
        data.guardarTodo(listaResultados);
    }

    
    // ===== CRUD =====
    

    public void insertar(ResultadoPalpacion nuevoResultado) {
        listaResultados.add(nuevoResultado);
        guardar();
    }

    
    //Elimina un registro del archivo.
    public void eliminar(int idGanado, int idPalpacion) {
        boolean eliminado = listaResultados.removeIf(
            r -> r.getIdGanado() == idGanado && r.getIdPalpacion() == idPalpacion
        );
        if (eliminado) {
            guardar();
        }
    }

    //Busca un resultado de palpaci\u00f3n espec\u00edfico.
    public ResultadoPalpacion buscarPorId(int idGanado, int idPalpacion) {
        return listaResultados.stream()
                .filter(r -> r.getIdGanado() == idGanado && r.getIdPalpacion() == idPalpacion)
                .findFirst()
                .orElse(null);
    }

    //Retorna todos los resultados que pertenecen a una misma palpaci\u00f3n.
    public List<ResultadoPalpacion> buscarPorPalpacion(int idPalpacion) {
        return listaResultados.stream()
                .filter(r -> r.getIdPalpacion() == idPalpacion)
                .collect(Collectors.toList());
    }

    
    public List<ResultadoPalpacion> mostrarTodo() {
        return this.listaResultados;
    }

    //Actualiza un registro del archivo.
    public void actualizarDetalle(ResultadoPalpacion resultadoActualizado) {
        int idGanado = resultadoActualizado.getIdGanado();
        int idPalpacion = resultadoActualizado.getIdPalpacion();

        for (int i = 0; i < listaResultados.size(); i++) {
            ResultadoPalpacion r = listaResultados.get(i);
            if (r.getIdGanado() == idGanado && r.getIdPalpacion() == idPalpacion) {
                listaResultados.set(i, resultadoActualizado);
                guardar();
                return;
            }
        }
    }
}
