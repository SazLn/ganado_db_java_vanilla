package co.upc.ganado.servicios;

import co.upc.ganado.entidades.Ganado;
import co.upc.ganado.entidades.Finca;
import co.upc.ganado.datos.FincaData;

import co.upc.ganado.entidades.enums.EnumEstadoVida;
import co.upc.ganado.entidades.enums.EnumSexo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servicio para gestionar la logica de negocio de las fincas.
 *
 * @author Santiago Rafael Zuleta Neira
 */
public class FincaService {

    private List<Finca> lista;
    private FincaData data;
    private GanadoService ganadoService;

    //Constructor
    public FincaService(GanadoService ganadoService) {
        this.data = new FincaData();
        this.ganadoService = ganadoService;
        this.lista = data.cargarTodo();
    }

    //Guarda los cambios en el archivo.
    private void guardar() {
        data.guardarTodo(lista);
    }


    // ===== CRUD =====


    public void insertar(Finca nuevaFinca) {
        lista.add(nuevaFinca);
        guardar();
    }


    public void eliminar(int idFinca) {
        boolean eliminado = lista.removeIf(f -> f.getIdFinca() == idFinca);
        if (eliminado) {
            guardar();
        }
    }


    public Finca buscarPorId(int idFinca) {
        return lista.stream()
                .filter(f -> f.getIdFinca() == idFinca)
                .findFirst()
                .orElse(null);
    }


    public List<Finca> mostrarTodo() {
        return this.lista;
    }


    public void actualizarDetalle(Finca fincaActualizada) {
        int idFinca = fincaActualizada.getIdFinca();

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getIdFinca() == idFinca) {
                lista.set(i, fincaActualizada);
                guardar();
                return;
            }
        }
    }


    // ===== CONSULTAS =====

    //Q5: Inventario por finca (requiere ganadoService)
    public List<Object[]> getInventarioPorFinca() {
        // Mapa: idFinca(clave) → [machos, hembras](valor) -> Map<clave, valor> nombre

        //Crear el hashmap. Clave y valor deben ser objetos.
        Map<Integer, int[]> conteo = new HashMap<>(); //El valor 'int[]' solo tiene 2 elementos [0 = macho] [1 = hembra] -> [0, 1] = [macho, hembra].

        //Recorrer la lista de ganado.
        for (Ganado g : ganadoService.mostrarTodo()) {

            //Filtrar por animales activos.
            if (g.getEstadoVida() != EnumEstadoVida.Activo) {
                continue; //Salta  a la siguiente iteración si el animal está vendido o muerto.
            }
            
            //Crear otro par clave-valor si la finca no existe en el hashmap. El tamaño del arreglo si debe ser definido.
            conteo.putIfAbsent(g.getIdFinca(), new int[2]);

            //Cuenta los machos.
            if (g.getTipoSexo() == EnumSexo.M) {
                int[] machos = conteo.get(g.getIdFinca()); //Obtiene el valor asociado a esa clave, en este caso el valor es un arreglo 'int[]'.
                machos[0]++; //Suma el valor en la posición [0] del arreglo, los machos.
            } else {

                //Cuenta las hembras.
                int[] hembras = conteo.get(g.getIdFinca());
                hembras[1]++;
            }
        }

        List<Object[]> datos = new ArrayList<>();

        //Recorrer la lista de fincas.
        for (Finca f : lista) {
            int[] c = conteo.getOrDefault(f.getIdFinca(), new int[2]); //Devuelve el valor asociado a la clave si esta existe. Si no existe, devuelve un valor por defecto, en este caso un arreglo 'int[2]' pero no lo agrega al hashmap.

            //Meter los datos a la lista. 'c[0] = machos', 'c[1] = hembras', 'c[0] + c[1] = total'
            datos.add(new Object[]{f.getNombreFinca(), c[0], c[1], c[0] + c[1]});
        }

        return datos;
    }
}
