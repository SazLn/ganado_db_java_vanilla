package co.upc.ganado.presentacion;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame login = new LoginFrame();
            login.setLocationRelativeTo(null); // Centra la ventana
            login.setVisible(true);
        });
    }

    /* ============================================================
       CÓDIGO DE PRUEBAS EN CONSOLA (original) — comentado
       ============================================================

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

    static void probarPalpacionService() {
        System.out.println("=== PALPACION SERVICE (Q3) ===\n");

        GanadoService gs = new GanadoService();
        ResultadoPalpacionService rs = new ResultadoPalpacionService();
        PalpacionService s = new PalpacionService(rs, gs);

        System.out.println("1. CRUD - mostrarTodo: " + s.mostrarTodo().size() + " palpaciones");

        Palpacion p = s.buscarPorId(1);
        System.out.println("2. buscarPorId(1): " + (p != null ? p : "NO ENCONTRADO"));

        System.out.println("3. buscarPorId(99): " + (s.buscarPorId(99) == null ? "null (correcto)" : "ERROR"));

        s.insertar(new Palpacion(99, "2026-05-22", null));
        System.out.println("4. insertar(99): " + (s.buscarPorId(99) != null ? "OK" : "ERROR"));

        s.eliminar(99);
        System.out.println("5. eliminar(99): " + (s.buscarPorId(99) == null ? "OK" : "ERROR"));

        //Q3
        System.out.println("\n6. Q3 - HISTORIAL DE PALPACIONES (idHembra=23):");
        List<String[]> historial = s.getHistorialPalpaciones(23);
        if (historial != null) {
            for (String[] fila : historial) {
                System.out.println("   " + String.join(" | ", fila));
            }
            System.out.println("   Total: " + historial.size() + " registros");
        } else {
            System.out.println("   (null retornado)");
        }

        System.out.println("\n7. Q3 - CON ID MACHO (id=1) - debe mostrar mensaje:");
        s.getHistorialPalpaciones(1);

        System.out.println("\n8. Q3 - CON ID INEXISTENTE (id=999) - debe mostrar mensaje:");
        s.getHistorialPalpaciones(999);

        System.out.println();
    }

    static void probarMachoService() {
        System.out.println("=== MACHO SERVICE (Q7) ===\n");

        MachoService s = new MachoService();

        System.out.println("1. CRUD - mostrarTodo: " + s.mostrarTodo().size() + " machos");

        Macho m = s.buscarPorId(1);
        System.out.println("2. buscarPorId(1): " + (m != null ? m : "NO ENCONTRADO"));

        System.out.println("3. buscarPorId(99): " + (s.buscarPorId(99) == null ? "null (correcto)" : "ERROR"));

        s.insertar(new Macho(null, false, null, null,
                99, "TEST-99", null, 0, null, 1));
        System.out.println("4. insertar(99): " + (s.buscarPorId(99) != null ? "OK" : "ERROR"));

        s.eliminar(99);
        System.out.println("5. eliminar(99): " + (s.buscarPorId(99) == null ? "OK" : "ERROR"));

        //Q7
        System.out.println("\n6. Q7 - HISTORIAL DE PADROTES:");
        List<String[]> padrotes = s.getHistorialPadrotes();
        if (padrotes != null && !padrotes.isEmpty()) {
            for (String[] fila : padrotes) {
                System.out.println("   " + String.join(" | ", fila));
            }
            System.out.println("   Total: " + padrotes.size() + " registros");
        } else {
            System.out.println("   (sin resultados o no implementado)");
        }

        System.out.println();
    }

    static void probarHembraService() {
        System.out.println("=== HEMBRA SERVICE (Q2) ===\n");

        HembraService s = new HembraService();

        System.out.println("1. CRUD - mostrarTodo: " + s.mostrarTodo().size() + " hembras");

        System.out.println("\n2. Q2 - ESTADO REPRODUCTIVO DEL HATO:");
        List<String[]> hatos = s.getEstadoReproductivoHato();
        if (hatos != null && !hatos.isEmpty()) {
            for (String[] fila : hatos) {
                System.out.println("   " + String.join(" | ", fila));
            }
            System.out.println("   Total: " + hatos.size() + " categorias");
        } else {
            System.out.println("   (sin resultados o no implementado)");
        }

        System.out.println();
    }

    static void probarTrasladoService() {
        System.out.println("=== TRASLADO SERVICE (Q1) ===\n");

        GanadoService gs = new GanadoService();
        DetalleTrasladoService ds = new DetalleTrasladoService();
        FincaService fs = new FincaService(gs);
        TrasladoService s = new TrasladoService(ds, gs, fs);

        System.out.println("1. CRUD - mostrarTodo: " + s.mostrarTodo().size() + " traslados");

        Traslado t = s.buscarPorId(1);
        System.out.println("2. buscarPorId(1): " + (t != null ? t : "NO ENCONTRADO"));

        System.out.println("3. buscarPorId(99): " + (s.buscarPorId(99) == null ? "null (correcto)" : "ERROR"));

        s.insertar(new Traslado(99, "2026-05-24", null, 0, 1, 2));
        System.out.println("4. insertar(99): " + (s.buscarPorId(99) != null ? "OK" : "ERROR"));

        s.eliminar(99);
        System.out.println("5. eliminar(99): " + (s.buscarPorId(99) == null ? "OK" : "ERROR"));

        //Q1
        System.out.println("\n6. Q1 - TRAZABILIDAD ANIMAL (idGanado=3):");
        List<String[]> trazabilidad = s.getTrazabilidadAnimal(3);
        if (trazabilidad != null && !trazabilidad.isEmpty()) {
            for (String[] fila : trazabilidad) {
                System.out.println("   " + String.join(" | ", fila));
            }
            System.out.println("   Total: " + trazabilidad.size() + " registros");
        } else {
            System.out.println("   (sin resultados o no implementado)");
        }

        System.out.println();
    }

    static void probarLoginService() {
        System.out.println("=== LOGIN SERVICE ===\n");

        LoginService s = new LoginService();

        System.out.println("1. admin/admin123: " + (s.validar("admin", "admin123") ? "OK" : "ERROR"));
        System.out.println("2. user/user123: " + (s.validar("user", "user123") ? "OK" : "ERROR"));
        System.out.println("3. admin/malpass: " + (s.validar("admin", "malpass") ? "ERROR" : "OK (rechazado)"));
        System.out.println("4. inexistente/pass: " + (s.validar("inexistente", "pass") ? "ERROR" : "OK (rechazado)"));

        System.out.println();
    }
    */  // fin del bloque de pruebas en consola
}
