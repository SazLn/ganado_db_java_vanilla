package co.upc.ganado.presentacion;

import co.upc.ganado.entidades.DetalleTraslado;
import co.upc.ganado.entidades.DetalleCompra;
import co.upc.ganado.datos.DetalleTrasladoData;
import co.upc.ganado.datos.DetalleCompraData;
import co.upc.ganado.servicios.DetalleTrasladoService;
import co.upc.ganado.servicios.DetalleCompraService;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        probarDetalleTrasladoService();
        probarDetalleCompraService();
    }

    static void probarDetalleTrasladoService() {
        System.out.println("=== DETALLE TRASLADO SERVICE ===\n");

        DetalleTrasladoService s = new DetalleTrasladoService(new DetalleTrasladoData());
        int inicial = s.mostrarTodo().size();

        System.out.println("1. mostrarTodo: " + inicial + " registros");

        DetalleTraslado encontrado = s.buscarPorId(1, 3);
        System.out.println("2. buscarPorId(1,3): " + (encontrado != null ? encontrado : "NO ENCONTRADO"));

        System.out.println("3. buscarPorId(99,99): " + (s.buscarPorId(99, 99) == null ? "null (correcto)" : "ERROR"));

        List<DetalleTraslado> porTraslado = s.buscarTraslado(1);
        System.out.println("4. buscarTraslado(1): " + porTraslado.size() + " registros");

        s.insertar(new DetalleTraslado(99, 99, "Prueba insercion"));
        System.out.println("5. insertar(99,99): " + (s.buscarPorId(99, 99) != null ? "OK" : "ERROR"));

        s.actualizarDetalle(new DetalleTraslado(99, 99, "Actualizado"));
        System.out.println("6. actualizar(99,99): " + ("Actualizado".equals(s.buscarPorId(99, 99).getObservaciones()) ? "OK" : "ERROR"));

        s.eliminar(99, 99);
        System.out.println("7. eliminar(99,99): " + (s.buscarPorId(99, 99) == null ? "OK (eliminado)" : "ERROR"));

        System.out.println("8. integridad: " + (s.mostrarTodo().size() == inicial ? "OK" : "ERROR"));

        System.out.println();
    }

    static void probarDetalleCompraService() {
        System.out.println("=== DETALLE COMPRA SERVICE ===\n");

        DetalleCompraService s = new DetalleCompraService(new DetalleCompraData());
        int inicial = s.mostrarTodo().size();

        System.out.println("1. mostrarTodo: " + inicial + " registros");

        DetalleCompra encontrado = s.buscarPorId(1, 23);
        System.out.println("2. buscarPorId(1,23): " + (encontrado != null ? encontrado : "NO ENCONTRADO"));

        System.out.println("3. buscarPorId(99,99): " + (s.buscarPorId(99, 99) == null ? "null (correcto)" : "ERROR"));

        List<DetalleCompra> porCompra = s.buscarPorCompra(2);
        System.out.println("4. buscarPorCompra(2): " + porCompra.size() + " registros");

        s.insertar(new DetalleCompra(99, 99, 500000, "Prueba insercion"));
        System.out.println("5. insertar(99,99): " + (s.buscarPorId(99, 99) != null ? "OK" : "ERROR"));

        s.actualizarDetalle(new DetalleCompra(99, 99, 999999, "Actualizado"));
        DetalleCompra verif = s.buscarPorId(99, 99);
        System.out.println("6. actualizar(99,99): " + (verif != null && verif.getPrecioIndividual() == 999999 ? "OK" : "ERROR"));

        s.eliminar(99, 99);
        System.out.println("7. eliminar(99,99): " + (s.buscarPorId(99, 99) == null ? "OK (eliminado)" : "ERROR"));

        System.out.println("8. integridad: " + (s.mostrarTodo().size() == inicial ? "OK" : "ERROR"));

        System.out.println();
    }
}
