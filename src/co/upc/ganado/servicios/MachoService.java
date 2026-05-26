package co.upc.ganado.servicios;

import co.upc.ganado.entidades.Macho;
import co.upc.ganado.datos.MachoData;

import co.upc.ganado.entidades.enums.EnumCalidadReproductiva;
       
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para gestionar la logica de negocio de los machos.
 *
 * @author Santiago Rafael Zuleta Neira
 */
public class MachoService {

    private List<Macho> lista;
    private MachoData data;

    //Constructor
    public MachoService() {
        this.data = new MachoData();
        this.lista = data.cargarTodo();
    }

    //Guarda los cambios en el archivo.
    private void guardar() {
        data.guardarTodo(lista);
    }


    // ===== CRUD =====


    public void insertar(Macho nuevoMacho) {
        lista.add(nuevoMacho);
        guardar();
    }


    public void eliminar(int idGanado) {
        boolean eliminado = lista.removeIf(m -> m.getIdGanado() == idGanado);
        if (eliminado) {
            guardar();
        }
    }


    public Macho buscarPorId(int idGanado) {
        return lista.stream()
                .filter(m -> m.getIdGanado() == idGanado)
                .findFirst()
                .orElse(null);
    }


    public List<Macho> mostrarTodo() {
        return this.lista;
    }


    public void actualizarDetalle(Macho machoActualizado) {
        int idGanado = machoActualizado.getIdGanado();

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getIdGanado() == idGanado) {
                lista.set(i, machoActualizado);
                guardar();
                return;
            }
        }
    }


    // ===== CONSULTAS =====

    //Q7: Historial de padrotes (machos con esPadrote = true)
    public List<String[]> getHistorialPadrotes() {
        List<String[]> datos = new ArrayList<>();
        
        //Obtener los padrotes.
        List<Macho> padrotes = lista.stream()
                               .filter(m -> m.isEsPadrote())
                               .collect(Collectors.toList());
        
        for (Macho m : padrotes) {
            String numeroMarca = m.getNumeroMarca();
            String calidadReproductiva = m.getCalidadReproductiva().toString();
            String fechaInicio = m.getFechaInicioPadrote();
            String fechaFin = "";
            String esPadrote = m.isEsPadrote() ? "Si" : "No";
            
            if (m.getFechaFinPadrote() == null || m.getFechaFinPadrote().isEmpty()) {
                fechaFin = "Actualidad";
            } else {
                fechaFin = m.getFechaFinPadrote();
            }
            
            //Meter los datos a la lista.
            datos.add(new String[]{numeroMarca, calidadReproductiva, fechaInicio, fechaFin, esPadrote});
        }
        return datos;
    }
}
