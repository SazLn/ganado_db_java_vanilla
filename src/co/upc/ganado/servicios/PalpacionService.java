package co.upc.ganado.servicios;

import co.upc.ganado.entidades.Ganado;
import co.upc.ganado.entidades.Palpacion;
import co.upc.ganado.datos.PalpacionData;
import co.upc.ganado.entidades.ResultadoPalpacion;
import co.upc.ganado.entidades.enums.EnumSexo;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Servicio para gestionar la logica de negocio de las palpaciones.
 *
 * @author Santiago Rafael Zuleta Neira
 */
public class PalpacionService {

    private List<Palpacion> lista;
    private PalpacionData data;
    private ResultadoPalpacionService resultadoService;
    private GanadoService ganadoService;

    //Constructor
    public PalpacionService(ResultadoPalpacionService resultadoService, GanadoService ganadoService) {
        this.data = new PalpacionData();
        this.resultadoService = resultadoService;
        this.ganadoService = ganadoService;
        this.lista = data.cargarTodo();
    }

    //Guarda los cambios en el archivo.
    private void guardar() {
        data.guardarTodo(lista);
    }


    // ===== CRUD =====


    public void insertar(Palpacion nuevaPalpacion) {
        lista.add(nuevaPalpacion);
        guardar();
    }


    public void eliminar(int idPalpacion) {
        boolean eliminado = lista.removeIf(p -> p.getIdPalpacion() == idPalpacion);
        if (eliminado) {
            guardar();
        }
    }


    public Palpacion buscarPorId(int idPalpacion) {
        return lista.stream()
                .filter(p -> p.getIdPalpacion() == idPalpacion)
                .findFirst()
                .orElse(null);
    }


    public List<Palpacion> mostrarTodo() {
        return this.lista;
    }


    public void actualizarPalpacion(Palpacion palpacionActualizada) {
        int idPalpacion = palpacionActualizada.getIdPalpacion();

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getIdPalpacion() == idPalpacion) {
                lista.set(i, palpacionActualizada);
                guardar();
                return;
            }
        }
    }


    // ===== CONSULTAS =====

    //Q3: Historial de palpaciones de un animal (requiere resultadoService y ganadoService)
    public List<String[]> getHistorialPalpaciones(int idGanado) {
        List<String[]> datos = new ArrayList<>();
        
        //Crear hashmap
        Map<Integer, Palpacion> palpaciones = new HashMap<>(); //Clave == idPalpacion, valor == Instancia de Palpacion
        
        //Validar si el id ingresado es válido.
        Ganado g = ganadoService.buscarPorId(idGanado);
        
        if (g == null) {
            System.out.println("El animal ingresado no existe.");
            return null;
        } else if (g.getTipoSexo() == EnumSexo.M) {
            System.out.println("El animal ingresado es macho.");
            return null;
        }
        
        
        //Asignar palpaciones al hashmap.
        for (Palpacion p : lista) palpaciones.put(p.getIdPalpacion(), p);
        
        //Recorrer lista de resultados.
        for (ResultadoPalpacion rp : resultadoService.mostrarTodo()) {
            //Validar id.
            if (idGanado != rp.getIdGanado()) continue;
            
            //Obtener palpacion.
            Palpacion p = palpaciones.get(rp.getIdPalpacion());
            
            if (p == null) continue; //Por si el resultado no está asociado a ninguna palpacion. Registro huerfano.
            
            //meter datos a la lista.
            datos.add(new String[]{
                ganadoService.buscarNumeroMarca(idGanado),
                p.getFechaPalpacion(),
                rp.getResultadoPalpacion().toString(),
                rp.getFechaConcepcionAprox()
            });
        }
        
        
        return datos;
    }
}
