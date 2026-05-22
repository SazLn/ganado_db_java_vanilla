package co.upc.ganado.servicios;

import co.upc.ganado.entidades.Ganado;
import co.upc.ganado.entidades.enums.EnumEstadoVida;
import co.upc.ganado.datos.GanadoData;
import co.upc.ganado.datos.MachoData;
import co.upc.ganado.datos.HembraData;

import java.util.List;

/**
 * Servicio para gestionar la logica de negocio del ganado en general.
 * Coordina machos y hembras a traves de GanadoData. Operaciones CRUD
 * sobre un ArrayList polimorfico de tipo Ganado.
 *
 * @author Santiago Rafael Zuleta Neira
 * @version 1.0
 * @since 2026-05-20
 */
public class GanadoService {

    private List<Ganado> lista;
    private GanadoData data;

    //Constructor
    public GanadoService() {
        this.data = new GanadoData(new MachoData(), new HembraData());
        this.lista = data.cargarTodo();
    }

    
    // ===== CRUD =====
    

    public void insertar(Ganado nuevoGanado) {
        lista.add(nuevoGanado);
        data.insertarGanado(nuevoGanado);
    }

    //NOTA: Soft delete. No elimina fisicamente, cambia estadoVida a Muerto o Vendido.
    public void eliminar(int idGanado) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getIdGanado() == idGanado) {
                lista.get(i).setEstadoVida(EnumEstadoVida.Muerto);
                data.actualizarGanado(lista.get(i));
                return;
            }
        }
    }

    
    public Ganado buscarPorId(int idGanado) {
        return lista.stream()
                .filter(g -> g.getIdGanado() == idGanado)
                .findFirst()
                .orElse(null);
    }

    
    public List<Ganado> mostrarTodo() {
        return this.lista;
    }

    
    public void actualizarRegistro(Ganado ganadoActualizado) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getIdGanado() == ganadoActualizado.getIdGanado()) {
                lista.set(i, ganadoActualizado);
                data.actualizarGanado(ganadoActualizado);
                return;
            }
        }
    }

    //Retorna el numero de marca de un animal por su id.
    public String buscarNumeroMarca(int idGanado) {
        return lista.stream()
               .filter(g -> g.getIdGanado() == idGanado)
               .map(Ganado::getNumeroMarca) //Obtiene el numero de marca.
               .findFirst()
               .orElse(null);
    }
}
