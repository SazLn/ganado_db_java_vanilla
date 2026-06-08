package co.upc.ganado.presentacion;


import co.upc.ganado.entidades.Venta;
import co.upc.ganado.entidades.enums.EnumTipoTransaccion;
import co.upc.ganado.servicios.VentaService;

import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;

/**
 * Panel de gestion de ventas de ganado. Muestra una tabla con todas
 * las ventas registradas y proporciona metodos para las operaciones
 * CRUD: crear, editar y eliminar ventas.
 * <p>
 * Este panel se inserta dentro de un JTabbedPane en el MainFrame y
 * utiliza un VentaService para la logica de negocio y persistencia.
 *
 * @author Santiago Rafael Zuleta Neira
 */
public class VentaPanel extends JPanel {

    private VentaService ventaServicio;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    /**
     * Crea un nuevo panel de ventas.
     *
     * @param vs Servicio de ventas que provee los datos y la logica
     *           de negocio
     */
    public VentaPanel(VentaService vs) {
        this.ventaServicio = vs;
        inicializarPanel();
        cargarTabla();
    }

    /**
     * Inicializa los componentes graficos del panel.
     */
    private void inicializarPanel() {
        this.setLayout(new BorderLayout());

        String[] columnas = {"ID Venta", "Fecha", "Tipo",
                             "Comprador", "Documento", "Valor Total",
                             "Metodo Pago", "Responsable"};

        modeloTabla = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modeloTabla);

        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    /**
     * Recarga los datos de la tabla desde el servicio.
     */
    public void cargarTabla() {
        modeloTabla.setRowCount(0);
        
        for (Venta v : ventaServicio.mostrarTodo()) {
            modeloTabla.addRow(new Object[]{
                v.getIdVenta(),
                v.getFechaVenta(),
                v.getTipoVenta(),
                v.getComprador(),
                v.getDocumentoReferencia(), //Nullable
                String.format("%,.2f", v.getValorTotalVenta()), //Formatear precios.
                v.getMetodoPago(),
                v.getResponsableVenta()
            });
        }
    }

    /**
     * Abre un dialogo para crear una nueva venta.
     */
    public void nuevo() {
        //Extraer los enums para el menu desplegable.
        EnumTipoTransaccion[] opcionesTipo = EnumTipoTransaccion.values();
        
        //HashMap para validar el ID duplicado.
        HashMap<Integer, Venta> mapaVentasId = new HashMap<>();
        for (Venta v : ventaServicio.mostrarTodo()) {
            mapaVentasId.put(v.getIdVenta(), v);
        }
        
        
        //-----------ENTRADA DE DATOS---------//
        
        //ID
        String idVenta = JOptionPane.showInputDialog("Ingrese el ID de la venta:");
        if (idVenta == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        if (idVenta.trim().isEmpty()) { JOptionPane.showMessageDialog(null, "El ID no puede estar vacio."); return; }
        
        try {
            int prueba = Integer.parseInt(idVenta);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El valor debe ser numérico.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (mapaVentasId.get(Integer.parseInt(idVenta)) != null) { JOptionPane.showMessageDialog(null, "El ID ingresado ya existe.", "Advertencia", JOptionPane.WARNING_MESSAGE); return; }
        
        
        //Fecha
        String fechaVenta = JOptionPane.showInputDialog("Ingrese la fecha (YYYY-MM-DD):");
        if (fechaVenta == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        if (fechaVenta.trim().isEmpty()) { JOptionPane.showMessageDialog(null, "La fecha no puede estar vacia."); return; }
        
        //Tipo de venta
        EnumTipoTransaccion tipoVenta = (EnumTipoTransaccion) JOptionPane.showInputDialog(null,
                "Seleccione el tipo de venta:", "Tipo de Venta",
                JOptionPane.QUESTION_MESSAGE, null, opcionesTipo, opcionesTipo[0]);
        if (tipoVenta == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        
        //Comprador
        String comprador = JOptionPane.showInputDialog("Ingrese el comprador:");
        if (comprador == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        if (comprador.trim().isEmpty()) { JOptionPane.showMessageDialog(null, "El comprador no puede estar vacio."); return; }
        
        //Documento de referencia (puede ser vacio)
        String documentoReferencia = JOptionPane.showInputDialog("Ingrese el documento de referencia (o Enter si no aplica):");
        if (documentoReferencia == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        
        //Valor total
        String valorTotalVenta = JOptionPane.showInputDialog("Ingrese el valor total de la venta:");
        if (valorTotalVenta == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        if (valorTotalVenta.trim().isEmpty()) { JOptionPane.showMessageDialog(null, "El valor total no puede estar vacio."); return; }
        
        try {
            double prueba = Double.parseDouble(valorTotalVenta);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El valor de la venta debe ser numerico.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        
        //Metodo de pago
        String metodoPago = JOptionPane.showInputDialog("Ingrese el metodo de pago:");
        if (metodoPago == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        if (metodoPago.trim().isEmpty()) { JOptionPane.showMessageDialog(null, "El metodo de pago no puede estar vacio."); return; }
        
        //Responsable
        String responsableVenta = JOptionPane.showInputDialog("Ingrese el responsable de la venta:");
        if (responsableVenta == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        if (responsableVenta.trim().isEmpty()) { JOptionPane.showMessageDialog(null, "El responsable no puede estar vacio."); return; }
        
        //Insertar
        Venta nuevaVenta = new Venta(Integer.parseInt(idVenta), fechaVenta.trim(),
                tipoVenta, comprador.trim(), documentoReferencia.trim(),
                Double.parseDouble(valorTotalVenta), metodoPago.trim(),
                responsableVenta.trim());
        ventaServicio.insertar(nuevaVenta);
        
        cargarTabla();
    }

    /**
     * Abre un dialogo para editar la venta seleccionada.
     */
    public void editar() {
        //Seleccionar fila de la tabla.
        int fila = tabla.getSelectedRow();
        
        //Validar que la fila ha sido seleccionada.
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila. Haga click en una para seleccionarla.");
            return;
        }
        
        //Obtener la venta a editar.
        Venta ventaEditar = ventaServicio.buscarPorId((int) tabla.getValueAt(fila, 0));
        
        //Opciones editables
        String[] opciones = {
            "Fecha",
            "Tipo",
            "Comprador",
            "Documento de Referencia",
            "Valor Total",
            "Metodo de Pago",
            "Responsable"
        };
        
        String campo = (String) JOptionPane.showInputDialog(null,
                "Seleccione el campo a editar:", "Editar Venta",
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        if (campo == null) return;
        
        switch (campo) {
            case "Fecha":
                String vFecha = JOptionPane.showInputDialog("Fecha (YYYY-MM-DD):", ventaEditar.getFechaVenta());
                if (vFecha != null && !vFecha.trim().isEmpty()) ventaEditar.setFechaVenta(vFecha.trim());
                break;
                
            case "Tipo":
                EnumTipoTransaccion[] tipoValores = EnumTipoTransaccion.values();
                EnumTipoTransaccion tipoSel = (EnumTipoTransaccion) JOptionPane.showInputDialog(null,
                        "Tipo de venta:", "Editar", JOptionPane.QUESTION_MESSAGE,
                        null, tipoValores, ventaEditar.getTipoVenta());
                if (tipoSel != null) ventaEditar.setTipoVenta(tipoSel);
                break;
                
            case "Comprador":
                String vComprador = JOptionPane.showInputDialog("Comprador:", ventaEditar.getComprador());
                if (vComprador != null && !vComprador.trim().isEmpty()) ventaEditar.setComprador(vComprador.trim());
                break;
                
            case "Documento de Referencia":
                String vDoc = JOptionPane.showInputDialog("Documento de referencia:", ventaEditar.getDocumentoReferencia());
                if (vDoc != null) ventaEditar.setDocumentoReferencia(vDoc.trim());
                break;
                
            case "Valor Total":
                String vValor = JOptionPane.showInputDialog("Valor total:", ventaEditar.getValorTotalVenta());
                if (vValor != null && !vValor.trim().isEmpty()) ventaEditar.setValorTotalVenta(Double.parseDouble(vValor.trim()));
                break;
                
            case "Metodo de Pago":
                String vMetodo = JOptionPane.showInputDialog("Metodo de pago:", ventaEditar.getMetodoPago());
                if (vMetodo != null && !vMetodo.trim().isEmpty()) ventaEditar.setMetodoPago(vMetodo.trim());
                break;
                
            case "Responsable":
                String vResp = JOptionPane.showInputDialog("Responsable:", ventaEditar.getResponsableVenta());
                if (vResp != null && !vResp.trim().isEmpty()) ventaEditar.setResponsableVenta(vResp.trim());
                break;
        }
        
        ventaServicio.actualizarDetalle(ventaEditar);
        cargarTabla();
    }

    /**
     * Elimina la venta seleccionada tras confirmacion.
     */
    public void eliminar() {
        //Obtener el indice de la fila seleccionada en la GUI haciendo click en ella.
        int fila = tabla.getSelectedRow();

        //Validar que la fila ha sido seleccionada. 'getSelectedRow()' devuelve -1 si la fila no ha sido seleccionada.
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila. Haga click en una para seleccionarla.");
            return;
        }

        //Extraer el id de la venta a eliminar.
        int idVenta = (int) tabla.getValueAt(fila, 0);

        //Eliminar la venta usando el servicio.
        int seleccion = JOptionPane.showConfirmDialog(null,
                "¿Seguro que desea eliminar el registro?", //Mensaje
                "Eliminar", //Titulo de la ventana
                JOptionPane.YES_NO_OPTION //Botones de "Si", "No".
        );
        
        if (seleccion == JOptionPane.YES_OPTION) {
            //Valor 0 para la opcion "Si"
            ventaServicio.eliminar(idVenta);
            JOptionPane.showMessageDialog(null, "Registro eliminado correctamente.");

            //Recargar tabla con datos actualizados.
            cargarTabla();

        }
    }
}
