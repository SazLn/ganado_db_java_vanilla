package co.upc.ganado.datos;

import co.upc.ganado.entidades.Ganado;
import co.upc.ganado.entidades.Hembra;
import co.upc.ganado.entidades.Macho;
import java.util.List;
import java.util.ArrayList;

/**
 * Coordinador de acceso a datos para Ganado. Delega las operaciones CRUD
 * a MachoData y HembraData, combinando sus resultados para ofrecer una
 * vista unificada de todo el ganado. No persiste en archivo propio.
 *
 * @author Santiago Rafael Zuleta Neira
 * @version 1.0
 * @since 2026-05-16
 */
public class GanadoData {
    private MachoData macho;
    private HembraData hembra;
    
    //Constructor.
    public GanadoData(MachoData macho, HembraData hembra) {
        this.macho = macho;
        this.hembra = hembra;
    }
    
    
    //MÉTODOS.
    
    /**/
    public List<Ganado> cargarTodo() {
        //Inicializar lista general con los elementos de 'MachoData'.
        List<Ganado> ganado = new ArrayList<>(macho.cargarTodo());
        
        //Añadir los elementos de 'HembraData'.
        ganado.addAll(hembra.cargarTodo());
        
        return ganado;
    }
    
    public void insertarGanado(Ganado nuevoGanado) {
        //Validar si el animal es macho o hembra.
        if (nuevoGanado instanceof Macho) {
            Macho nuevoMacho = (Macho) nuevoGanado;
            
            macho.insertarMacho(nuevoMacho);
        } else {
            Hembra nuevaHembra = (Hembra) nuevoGanado;
            
            hembra.insertarHembra(nuevaHembra);
        }
    }
    
    public void actualizarGanado(Ganado ganadoActualizar) {
        //Validar si el animal es macho o hembra.
        if (ganadoActualizar instanceof Macho) {
            Macho machoActualizar = (Macho) ganadoActualizar;
            
            macho.actualizarMacho(machoActualizar);
        } else {
            Hembra hembraActualizar = (Hembra) ganadoActualizar;
            
            hembra.actualizarHembra(hembraActualizar);
        }
    }
    
    
    public void eliminarGanado(int id) {
        if (macho.eliminarMacho(id)) {
            System.out.println("Macho eliminado correctamente.");
        
        } else if (hembra.eliminarHembra(id)) {
            System.out.println("Hembra eliminada correctamente.");
        
        } else {
            System.out.println("No se encuentra ningún animal registrado con el id " + id);
        }
    }
}
