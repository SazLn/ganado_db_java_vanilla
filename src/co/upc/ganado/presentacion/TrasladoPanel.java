package co.upc.ganado.presentacion;

import co.upc.ganado.entidades.Traslado;
import co.upc.ganado.entidades.enums.EnumMotivoTraslado;
import co.upc.ganado.servicios.TrasladoService;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;

/**
 * Panel de gestion de traslados de ganado. Muestra una tabla con todos
 * los traslados registrados y proporciona metodos para las operaciones
 * CRUD: crear, editar y eliminar traslados.

 * Este panel se inserta dentro de un JTabbedPane en el MainFrame y
 * utiliza TrasladoService para la logica de negocio y persistencia.
 *
 * @author Santiago Rafael Zuleta Neira
 */
public class TrasladoPanel extends JPanel {

    private TrasladoService trasladoServicio;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    /**
     * Crea un nuevo panel de traslados.
     *
     * @param ts Servicio de traslados que provee los datos y la logica
     *           de negocio
     */
    public TrasladoPanel(TrasladoService ts) {
        this.trasladoServicio = ts;
        inicializarPanel();
        cargarTabla();
    }

    /**
     * Inicializa los componentes graficos del panel.
     */
    private void inicializarPanel() {
        this.setLayout(new BorderLayout());

        String[] columnas = {"ID Traslado", "Fecha", "Motivo",
                             "Transporte", "Responsable", "Costo",
                             "Finca Origen", "Finca Destino"};

        modeloTabla = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modeloTabla);

        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    /**
     * Recarga los datos de la tabla desde el servicio.
     */
    public void cargarTabla() {
        modeloTabla.setRowCount(0);
        
        for (Traslado t : trasladoServicio.mostrarTodo()) {
            modeloTabla.addRow(new Object[]{
                t.getIdTraslado(),
                t.getFechaTraslado(),
                t.getMotivoTraslado(),
                t.getMedioTransporte(),
                t.getResponsableTraslado(), //Nullable
                String.format("%,.2f", t.getCostoTraslado()),
                t.getIdFincaOrigen(),
                t.getIdFincaDestino()
            });
        }
    }

    /**
     * Abre un dialogo para crear un nuevo traslado.
     */
    public void nuevo() {
        //Extraer los enums para el menu desplegable.
        EnumMotivoTraslado[] opcionesMotivo = EnumMotivoTraslado.values();
        
        //HashMap para validar el ID duplicado.
        HashMap<Integer, Traslado> mapaTraslados = new HashMap<>();
        for (Traslado t : trasladoServicio.mostrarTodo()) {
            mapaTraslados.put(t.getIdTraslado(), t);
        }
        
        //-----------ENTRADA DE DATOS---------//
        
        //ID
        String idTraslado = JOptionPane.showInputDialog("Ingrese el ID del traslado:");
        if (idTraslado == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        if (idTraslado.trim().isEmpty()) { JOptionPane.showMessageDialog(null, "El ID no puede estar vacio."); return; }
        
        try {
            int prueba = Integer.parseInt(idTraslado);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El valor debe ser numerico.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (mapaTraslados.get(Integer.parseInt(idTraslado)) != null) { JOptionPane.showMessageDialog(null, "El ID ingresado ya existe.", "Advertencia", JOptionPane.WARNING_MESSAGE); return; }
        
        //Fecha
        String fechaTraslado = JOptionPane.showInputDialog("Ingrese la fecha (YYYY-MM-DD):");
        if (fechaTraslado == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        if (fechaTraslado.trim().isEmpty()) { JOptionPane.showMessageDialog(null, "La fecha no puede estar vacia."); return; }
        
        //Motivo de traslado
        EnumMotivoTraslado motivoTraslado = (EnumMotivoTraslado) JOptionPane.showInputDialog(null,
                "Seleccione el motivo del traslado:", "Motivo de Traslado",
                JOptionPane.QUESTION_MESSAGE, null, opcionesMotivo, opcionesMotivo[0]);
        if (motivoTraslado == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        
        //Medio de transporte
        String medioTransporte = JOptionPane.showInputDialog("Ingrese el medio de transporte:");
        if (medioTransporte == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        if (medioTransporte.trim().isEmpty()) { JOptionPane.showMessageDialog(null, "El medio de transporte no puede estar vacio."); return; }
        
        //Responsable (puede ser vacio)
        String responsableTraslado = JOptionPane.showInputDialog("Ingrese el responsable (o Enter si no aplica):");
        if (responsableTraslado == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        
        //Costo
        String costoTraslado = JOptionPane.showInputDialog("Ingrese el costo del traslado:");
        if (costoTraslado == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        if (costoTraslado.trim().isEmpty()) { JOptionPane.showMessageDialog(null, "El costo no puede estar vacio."); return; }
        
        try {
            double prueba = Double.parseDouble(costoTraslado);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El costo del traslado debe ser numerico.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        //Finca origen
        String idFincaOrigen = JOptionPane.showInputDialog("Ingrese el ID de la finca de origen:");
        if (idFincaOrigen == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        if (idFincaOrigen.trim().isEmpty()) { JOptionPane.showMessageDialog(null, "La finca origen no puede estar vacia."); return; }
        
        //Finca destino
        String idFincaDestino = JOptionPane.showInputDialog("Ingrese el ID de la finca de destino:");
        if (idFincaDestino == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        if (idFincaDestino.trim().isEmpty()) { JOptionPane.showMessageDialog(null, "La finca destino no puede estar vacia."); return; }
        
        //Insertar
        Traslado nuevoTraslado = new Traslado(Integer.parseInt(idTraslado), fechaTraslado.trim(),
                motivoTraslado, medioTransporte.trim(), responsableTraslado.trim(),
                Double.parseDouble(costoTraslado), Integer.parseInt(idFincaOrigen),
                Integer.parseInt(idFincaDestino));
        trasladoServicio.insertar(nuevoTraslado);
        
        cargarTabla();
    }

    /**
     * Abre un dialogo para editar el traslado seleccionado.
     */
    public void editar() {
        //Seleccionar fila de la tabla.
        int fila = tabla.getSelectedRow();
        
        //Validar que la fila ha sido seleccionada.
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila. Haga click en una para seleccionarla.");
            return;
        }
        
        //Obtener el traslado a editar.
        Traslado trasladoEditar = trasladoServicio.buscarPorId((int) tabla.getValueAt(fila, 0));
        
        //Opciones editables
        String[] opciones = {
            "Fecha",
            "Motivo",
            "Medio de Transporte",
            "Responsable",
            "Costo",
            "Finca Origen",
            "Finca Destino"
        };
        
        String campo = (String) JOptionPane.showInputDialog(null,
                "Seleccione el campo a editar:", "Editar Traslado",
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        if (campo == null) return;
        
        switch (campo) {
            case "Fecha":
                String tFecha = JOptionPane.showInputDialog("Fecha (YYYY-MM-DD):", trasladoEditar.getFechaTraslado());
                if (tFecha != null && !tFecha.trim().isEmpty()) trasladoEditar.setFechaTraslado(tFecha.trim());
                break;
                
            case "Motivo":
                EnumMotivoTraslado[] motivoValores = EnumMotivoTraslado.values();
                EnumMotivoTraslado motivoSel = (EnumMotivoTraslado) JOptionPane.showInputDialog(null,
                        "Motivo de traslado:", "Editar", JOptionPane.QUESTION_MESSAGE,
                        null, motivoValores, trasladoEditar.getMotivoTraslado());
                if (motivoSel != null) trasladoEditar.setMotivoTraslado(motivoSel);
                break;
                
            case "Medio de Transporte":
                String tTransporte = JOptionPane.showInputDialog("Medio de transporte:", trasladoEditar.getMedioTransporte());
                if (tTransporte != null && !tTransporte.trim().isEmpty()) trasladoEditar.setMedioTransporte(tTransporte.trim());
                break;
                
            case "Responsable":
                String tResp = JOptionPane.showInputDialog("Responsable:", trasladoEditar.getResponsableTraslado());
                if (tResp != null) trasladoEditar.setResponsableTraslado(tResp.trim());
                break;
                
            case "Costo":
                String tCosto = JOptionPane.showInputDialog("Costo:", trasladoEditar.getCostoTraslado());
                if (tCosto != null && !tCosto.trim().isEmpty()) trasladoEditar.setCostoTraslado(Double.parseDouble(tCosto.trim()));
                break;
                
            case "Finca Origen":
                String tOri = JOptionPane.showInputDialog("ID de finca origen:", trasladoEditar.getIdFincaOrigen());
                if (tOri != null && !tOri.trim().isEmpty()) trasladoEditar.setIdFincaOrigen(Integer.parseInt(tOri.trim()));
                break;
                
            case "Finca Destino":
                String tDes = JOptionPane.showInputDialog("ID de finca destino:", trasladoEditar.getIdFincaDestino());
                if (tDes != null && !tDes.trim().isEmpty()) trasladoEditar.setIdFincaDestino(Integer.parseInt(tDes.trim()));
                break;
        }
        
        trasladoServicio.actualizarDetalle(trasladoEditar);
        cargarTabla();
    }

    /**
     * Elimina el traslado seleccionado tras confirmacion.
     */
    public void eliminar() {
        //Obtener el indice de la fila seleccionada en la GUI haciendo click en ella.
        int fila = tabla.getSelectedRow();

        //Validar que la fila ha sido seleccionada. 'getSelectedRow()' devuelve -1 si la fila no ha sido seleccionada.
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila. Haga click en una para seleccionarla.");
            return;
        }

        //Extraer el id del traslado a eliminar.
        int idTraslado = (int) tabla.getValueAt(fila, 0);

        //Eliminar el traslado usando el servicio.
        int seleccion = JOptionPane.showConfirmDialog(null,
                "¿Seguro que desea eliminar el registro?", //Mensaje
                "Eliminar", //Titulo de la ventana
                JOptionPane.YES_NO_OPTION //Botones de "Si", "No".
        );
        if (seleccion == JOptionPane.YES_OPTION) {
            //Valor 0 para la opcion "Si"
            trasladoServicio.eliminar(idTraslado);
            JOptionPane.showMessageDialog(null, "Registro eliminado correctamente.");

            //Recargar tabla con datos actualizados.
            cargarTabla();
        }
    }
}
