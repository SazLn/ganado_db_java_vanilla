package co.upc.ganado.presentacion;

import co.upc.ganado.entidades.Compra;
import co.upc.ganado.entidades.enums.EnumTipoTransaccion;
import co.upc.ganado.servicios.CompraService;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;

/**
 * Panel de gestion de compras de ganado. Muestra una tabla con todas
 * las compras registradas y proporciona metodos para las operaciones
 * CRUD: crear, editar y eliminar compras.
 * <p>
 * Este panel se inserta dentro de un JTabbedPane en el MainFrame y
 * utiliza un CompraService para la logica de negocio y persistencia.
 *
 * @author Santiago Rafael Zuleta Neira
 */
public class CompraPanel extends JPanel {

    private CompraService compraServicio;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    /**
     * Crea un nuevo panel de compras.
     *
     * @param cs Servicio de compras que provee los datos y la logica
     *           de negocio
     */
    public CompraPanel(CompraService cs) {
        this.compraServicio = cs;
        inicializarPanel();
        cargarTabla();
    }

    /**
     * Inicializa los componentes graficos del panel.
     */
    private void inicializarPanel() {
        this.setLayout(new BorderLayout());

        String[] columnas = {"ID Compra", "Fecha", "Tipo",
                             "Proveedor", "Documento", "Valor Total",
                             "Responsable"};

        modeloTabla = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modeloTabla);

        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    /**
     * Recarga los datos de la tabla desde el servicio.
     */
    public void cargarTabla() {
        modeloTabla.setRowCount(0);
        
        for (Compra c : compraServicio.mostrarTodo()) {
            modeloTabla.addRow(new Object[]{
                c.getIdCompra(),
                c.getFechaCompra(),
                c.getTipoCompra(),
                c.getProveedor(),
                c.getDocumentoReferencia(), //Nullable
                String.format("%,.2f", c.getValorTotalCompra()),
                c.getResponsableCompra()
            });
        }
    }

    /**
     * Abre un dialogo para crear una nueva compra.
     */
    public void nuevo() {
        //Extraer los enums para el menu desplegable.
        EnumTipoTransaccion[] opcionesTipo = EnumTipoTransaccion.values();
        
        //HashMap para validar el ID duplicado.
        HashMap<Integer, Compra> mapaComprasId = new HashMap<>();
        for (Compra c : compraServicio.mostrarTodo()) {
            mapaComprasId.put(c.getIdCompra(), c);
        }
        
        //---------ENTRADA DE DATOS----------//
        
        //ID
        String idCompra = JOptionPane.showInputDialog("Ingrese el ID de la compra:");
        if (idCompra == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        if (idCompra.trim().isEmpty()) { JOptionPane.showMessageDialog(null, "El ID no puede estar vacio."); return; }
        
        try {
            int prueba = Integer.parseInt(idCompra);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El valor debe ser numérico.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (mapaComprasId.get(Integer.parseInt(idCompra)) != null) { JOptionPane.showMessageDialog(null, "El ID ingresado ya existe.", "Advertencia", JOptionPane.WARNING_MESSAGE); return; }
        
        //Fecha
        String fechaCompra = JOptionPane.showInputDialog("Ingrese la fecha (YYYY-MM-DD):");
        if (fechaCompra == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        if (fechaCompra.trim().isEmpty()) { JOptionPane.showMessageDialog(null, "La fecha no puede estar vacia."); return; }
        
        //Tipo de compra
        EnumTipoTransaccion tipoCompra = (EnumTipoTransaccion) JOptionPane.showInputDialog(null,
                "Seleccione el tipo de compra:", "Tipo de Compra",
                JOptionPane.QUESTION_MESSAGE, null, opcionesTipo, opcionesTipo[0]);
        if (tipoCompra == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        
        //Proveedor
        String proveedor = JOptionPane.showInputDialog("Ingrese el proveedor:");
        if (proveedor == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        if (proveedor.trim().isEmpty()) { JOptionPane.showMessageDialog(null, "El proveedor no puede estar vacio."); return; }
        
        //Documento de referencia (puede ser vacio)
        String documentoReferencia = JOptionPane.showInputDialog("Ingrese el documento de referencia (o Enter si no aplica):");
        if (documentoReferencia == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        
        //Valor total
        String valorTotalCompra = JOptionPane.showInputDialog("Ingrese el valor total de la compra:");
        if (valorTotalCompra == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        if (valorTotalCompra.trim().isEmpty()) { JOptionPane.showMessageDialog(null, "El valor total no puede estar vacio."); return; }
        
        //Responsable
        String responsableCompra = JOptionPane.showInputDialog("Ingrese el responsable de la compra:");
        if (responsableCompra == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        if (responsableCompra.trim().isEmpty()) { JOptionPane.showMessageDialog(null, "El responsable no puede estar vacio."); return; }
        
        //Insertar
        Compra nuevaCompra = new Compra(Integer.parseInt(idCompra), fechaCompra.trim(),
                tipoCompra, documentoReferencia.trim(),
                Double.parseDouble(valorTotalCompra), responsableCompra.trim(),
                proveedor.trim());
        compraServicio.insertar(nuevaCompra);
        
        cargarTabla();
    }

    /**
     * Abre un dialogo para editar la compra seleccionada.
     */
    public void editar() {
        //Seleccionar fila de la tabla.
        int fila = tabla.getSelectedRow();
        
        //Validar que la fila ha sido seleccionada.
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila. Haga click en una para seleccionarla.");
            return;
        }
        
        //Obtener la compra a editar.
        Compra compraEditar = compraServicio.buscarPorId((int) tabla.getValueAt(fila, 0));
        
        //Opciones editables
        String[] opciones = {
            "Fecha",
            "Tipo",
            "Proveedor",
            "Documento de Referencia",
            "Valor Total",
            "Responsable"
        };
        
        String campo = (String) JOptionPane.showInputDialog(null,
                "Seleccione el campo a editar:", "Editar Compra",
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        if (campo == null) return;
        
        switch (campo) {
            case "Fecha":
                String cFecha = JOptionPane.showInputDialog("Fecha (YYYY-MM-DD):", compraEditar.getFechaCompra());
                if (cFecha != null && !cFecha.trim().isEmpty()) compraEditar.setFechaCompra(cFecha.trim());
                break;
                
            case "Tipo":
                EnumTipoTransaccion[] tipoValores = EnumTipoTransaccion.values();
                
                EnumTipoTransaccion tipoSel = (EnumTipoTransaccion) JOptionPane.showInputDialog(null,
                        "Tipo de compra:", "Editar", JOptionPane.QUESTION_MESSAGE,
                        null, tipoValores, compraEditar.getTipoCompra());
                
                if (tipoSel != null) compraEditar.setTipoCompra(tipoSel);
                break;
                
            case "Proveedor":
                String cProveedor = JOptionPane.showInputDialog("Proveedor:", compraEditar.getProveedor());
                if (cProveedor != null && !cProveedor.trim().isEmpty()) compraEditar.setProveedor(cProveedor.trim());
                break;
                
            case "Documento de Referencia":
                String cDoc = JOptionPane.showInputDialog("Documento de referencia:", compraEditar.getDocumentoReferencia());
                if (cDoc != null) compraEditar.setDocumentoReferencia(cDoc.trim());
                break;
                
            case "Valor Total":
                String cValor = JOptionPane.showInputDialog("Valor total:", compraEditar.getValorTotalCompra());
                if (cValor != null && !cValor.trim().isEmpty()) compraEditar.setValorTotalCompra(Double.parseDouble(cValor.trim()));
                break;
                
            case "Responsable":
                String cResp = JOptionPane.showInputDialog("Responsable:", compraEditar.getResponsableCompra());
                if (cResp != null && !cResp.trim().isEmpty()) compraEditar.setResponsableCompra(cResp.trim());
                break;
        }
        
        compraServicio.actualizarDetalle(compraEditar);
        cargarTabla();
    }

    /**
     * Elimina la compra seleccionada tras confirmacion.
     */
    public void eliminar() {
        //Obtener el índice de la fila seleccionada en la GUI haciendo click en ella.
        int fila = tabla.getSelectedRow();

        //Validar que la fila ha sido seleccionada. 'getSelectedRow()' devuelve -1 si la fila no ha sido seleccionada.
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila. Haga click en una para seleccionarla.");
            return;
        }

        //Extraer el id de la compra a eliminar.
        int idCompra = (int) tabla.getValueAt(fila, 0);

        //Eliminar la compra usando el servicio.
        int seleccion = JOptionPane.showConfirmDialog(null,
                "¿Seguro que desea eliminar el registro?", //Mensaje
                "Eliminar", //Título de la ventana
                JOptionPane.YES_NO_OPTION //Botones de "Si", "No".
        );
        if (seleccion == JOptionPane.YES_OPTION) {
            //Valor 0 para la opción "Si"
            compraServicio.eliminar(idCompra);
            JOptionPane.showMessageDialog(null, "Registro eliminado correctamente.");

            //Recargar tabla con datos actualizados.
            cargarTabla();

        }
    }
}
