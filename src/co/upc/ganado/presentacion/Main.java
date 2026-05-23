package co.upc.ganado.presentacion;

import co.upc.ganado.entidades.DetalleTraslado;
import co.upc.ganado.entidades.DetalleCompra;
import co.upc.ganado.entidades.Compra;
import co.upc.ganado.entidades.Vacuna;
import co.upc.ganado.servicios.DetalleTrasladoService;
import co.upc.ganado.servicios.DetalleCompraService;
import co.upc.ganado.servicios.GanadoService;
import co.upc.ganado.servicios.CompraService;
import co.upc.ganado.servicios.AplicacionVacunaService;
import co.upc.ganado.servicios.VacunaService;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        probarDetalleTrasladoService();
        probarDetalleCompraService();
        probarCompraService();
        probarVacunaService();
    }

    static void probarDetalleTrasladoService() {
        System.out.println("=== DETALLE TRASLADO SERVICE ===\n");

        DetalleTrasladoService s = new DetalleTrasladoService();
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

        DetalleCompraService s = new DetalleCompraService();
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

    static void probarCompraService() {
        System.out.println("=== COMPRA SERVICE ===\n");

        GanadoService gs = new GanadoService();
        DetalleCompraService ds = new DetalleCompraService();
        CompraService s = new CompraService(ds, gs);

        System.out.println("1. mostrarTodo: " + s.mostrarTodo().size() + " compras");

        Compra c = s.buscarPorId(1);
        System.out.println("2. buscarPorId(1): " + (c != null ? c : "NO ENCONTRADO"));

        System.out.println("3. buscarPorId(99): " + (s.buscarPorId(99) == null ? "null (correcto)" : "ERROR"));

        s.insertar(new Compra(99, "2026-01-01", null, "", 0, "Prueba", "Test"));
        System.out.println("4. insertar(99): " + (s.buscarPorId(99) != null ? "OK" : "ERROR"));

        s.eliminar(99);
        System.out.println("5. eliminar(99): " + (s.buscarPorId(99) == null ? "OK" : "ERROR"));

        //Q6
        System.out.println("\n6. Q6 - HISTORIAL DE COMPRAS:");
        List<String[]> historial = s.getHistorialCompras();
        if (historial != null) {
            for (String[] fila : historial) {
                System.out.println("   " + String.join(" | ", fila));
            }
            System.out.println("   Total: " + historial.size() + " registros");
        } else {
            System.out.println("   (no implementado)");
        }

        System.out.println();
    }

    static void probarVacunaService() {
        System.out.println("=== VACUNA SERVICE ===\n");

        GanadoService gs = new GanadoService();
        AplicacionVacunaService avs = new AplicacionVacunaService();
        VacunaService s = new VacunaService(avs, gs);

        System.out.println("1. mostrarTodo: " + s.mostrarTodo().size() + " vacunas");

        Vacuna v = s.buscarPorId(1);
        System.out.println("2. buscarPorId(1): " + (v != null ? v : "NO ENCONTRADO"));

        System.out.println("3. buscarPorId(99): " + (s.buscarPorId(99) == null ? "null (correcto)" : "ERROR"));

        s.insertar(new Vacuna(99, "Prueba", "Test", "5ml"));
        System.out.println("4. insertar(99): " + (s.buscarPorId(99) != null ? "OK" : "ERROR"));

        s.eliminar(99);
        System.out.println("5. eliminar(99): " + (s.buscarPorId(99) == null ? "OK" : "ERROR"));

        //Q4
        System.out.println("\n6. Q4 - CONTROL DE VACUNACION:");
        List<String[]> control = s.getControlVacunacion();
        if (control != null) {
            for (String[] fila : control) {
                System.out.println("   " + String.join(" | ", fila));
            }
            System.out.println("   Total: " + control.size() + " registros");
        } else {
            System.out.println("   (no implementado)");
        }

        System.out.println();
    }
}
