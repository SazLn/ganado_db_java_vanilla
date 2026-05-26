package co.upc.ganado.servicios;

import co.upc.ganado.entidades.Hembra;
import co.upc.ganado.datos.HembraData;

import co.upc.ganado.entidades.enums.EnumEstadoVida;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para gestionar la logica de negocio de las hembras.
 *
 * @author Santiago Rafael Zuleta Neira
 */
public class HembraService {

    private List<Hembra> lista;
    private HembraData data;

    //Constructor
    public HembraService() {
        this.data = new HembraData();
        this.lista = data.cargarTodo();
    }

    //Guarda los cambios en el archivo.
    private void guardar() {
        data.guardarTodo(lista);
    }


    // ===== CRUD =====


    public void insertar(Hembra nuevaHembra) {
        lista.add(nuevaHembra);
        guardar();
    }


    public void eliminar(int idGanado) {
        boolean eliminado = lista.removeIf(h -> h.getIdGanado() == idGanado);
        if (eliminado) {
            guardar();
        }
    }


    public Hembra buscarPorId(int idGanado) {
        return lista.stream()
                .filter(h -> h.getIdGanado() == idGanado)
                .findFirst()
                .orElse(null);
    }


    public List<Hembra> mostrarTodo() {
        return this.lista;
    }


    public void actualizarDetalle(Hembra hembraActualizada) {
        int idGanado = hembraActualizada.getIdGanado();

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getIdGanado() == idGanado) {
                lista.set(i, hembraActualizada);
                guardar();
                return;
            }
        }
    }


    // ===== CONSULTAS =====

    //Q2: Estado reproductivo del hato (COUNT + GROUP BY estadoReproductivoActual)
    public List<String[]> getEstadoReproductivoHato() {
        List<String[]> datos = new ArrayList<>();

        //Crear Hashmap
        Map<String, Integer> conteo = new HashMap<>();
        
        //Recorrer lista de vacas.
        for (Hembra h : lista) {
            if (h.getEstadoVida() != EnumEstadoVida.Activo) continue; //Filtrar por las que están activas.
            
            //Extraer el estado de la vaca.
            String estado = h.getEstadoReproductivoActual().toString();
            
            //meter los datos en el mapa.
            conteo.put(estado, conteo.getOrDefault(estado, 0) + 1); //Si el valor no existe, lo crea y lo inicializa en 1.
        }

        //Recorrer el hashmap.
        for (Map.Entry<String, Integer> e : conteo.entrySet()) { //entrySet() devuelve el conjunto de parejas clave-valor.
            
            //Meter los datos a la lista.
            datos.add(new String[]{e.getKey(), String.valueOf(e.getValue())});
        }

        return datos;
    }
}