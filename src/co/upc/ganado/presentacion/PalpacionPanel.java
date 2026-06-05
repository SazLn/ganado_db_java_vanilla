package co.upc.ganado.presentacion;

import co.upc.ganado.entidades.Palpacion;
import co.upc.ganado.entidades.enums.EnumTipoPalpacion;
import co.upc.ganado.servicios.PalpacionService;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;

/**
 * Panel de gestion de palpaciones. Muestra una tabla con todas las
 * palpaciones registradas y proporciona metodos para las operaciones
 * CRUD: crear, editar y eliminar palpaciones.
 * <p>
 * Este panel se inserta dentro de un JTabbedPane en el MainFrame y
 * utiliza un PalpacionService para la logica de negocio y persistencia.
 *
 * @author Santiago Rafael Zuleta Neira
 */
public class PalpacionPanel extends JPanel {

    private PalpacionService palpacionServicio;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    /**
     * Crea un nuevo panel de palpaciones.
     *
     * @param ps Servicio de palpaciones que provee los datos y la
     *           logica de negocio
     */
    public PalpacionPanel(PalpacionService ps) {
        this.palpacionServicio = ps;
        inicializarPanel();
        cargarTabla();
    }

    /**
     * Inicializa los componentes graficos del panel.
     */
    private void inicializarPanel() {
        this.setLayout(new BorderLayout());

        String[] columnas = {"ID Palpacion", "Fecha", "Tipo",
                             "Observaciones"};

        modeloTabla = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modeloTabla);

        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    /**
     * Recarga los datos de la tabla desde el servicio.
     */
    public void cargarTabla() {
        modeloTabla.setRowCount(0);
        
        for (Palpacion p : palpacionServicio.mostrarTodo()) {
            modeloTabla.addRow(new Object[]{
                p.getIdPalpacion(),
                p.getFechaPalpacion(),
                p.getTipoPalpacion(),
                p.getObservaciones() //Nullable
            });
        }
    }

    /**
     * Abre un dialogo para crear una nueva palpacion.
     */
    public void nuevo() {
        //Extraer los enums para el menu desplegable.
        EnumTipoPalpacion[] opcionesTipo = EnumTipoPalpacion.values();
        
        //HashMap para validar el ID duplicado.
        HashMap<Integer, Palpacion> mapaPalpacionesId = new HashMap<>();
        for (Palpacion p : palpacionServicio.mostrarTodo()) {
            mapaPalpacionesId.put(p.getIdPalpacion(), p);
        }
        
        //-----------ENTRADA DE DATOS---------//
        
        //ID
        String idPalpacion = JOptionPane.showInputDialog("Ingrese el ID de la palpacion:");
        if (idPalpacion == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        if (idPalpacion.trim().isEmpty()) { JOptionPane.showMessageDialog(null, "El ID no puede estar vacio."); return; }
        
        try {
            int prueba = Integer.parseInt(idPalpacion);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El valor debe ser numerico.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (mapaPalpacionesId.get(Integer.parseInt(idPalpacion)) != null) { JOptionPane.showMessageDialog(null, "El ID ingresado ya existe.", "Advertencia", JOptionPane.WARNING_MESSAGE); return; }
        
        //Fecha
        String fechaPalpacion = JOptionPane.showInputDialog("Ingrese la fecha (YYYY-MM-DD):");
        if (fechaPalpacion == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        if (fechaPalpacion.trim().isEmpty()) { JOptionPane.showMessageDialog(null, "La fecha no puede estar vacia."); return; }
        
        //Tipo de palpacion
        EnumTipoPalpacion tipoPalpacion = (EnumTipoPalpacion) JOptionPane.showInputDialog(null,
                "Seleccione el tipo de palpacion:", "Tipo de Palpacion",
                JOptionPane.QUESTION_MESSAGE, null, opcionesTipo, opcionesTipo[0]);
        if (tipoPalpacion == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        
        //Observaciones (pueden ser vacias)
        String observaciones = JOptionPane.showInputDialog("Ingrese observaciones (o Enter si no aplica):");
        if (observaciones == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        
        //Insertar
        Palpacion nuevaPalpacion = new Palpacion(Integer.parseInt(idPalpacion), fechaPalpacion.trim(),
                tipoPalpacion, observaciones.trim());
        palpacionServicio.insertar(nuevaPalpacion);
        
        cargarTabla();
    }

    /**
     * Abre un dialogo para editar la palpacion seleccionada.
     */
    public void editar() {
        //Seleccionar fila de la tabla.
        int fila = tabla.getSelectedRow();
        
        //Validar que la fila ha sido seleccionada.
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila. Haga click en una para seleccionarla.");
            return;
        }
        
        //Obtener la palpacion a editar.
        Palpacion palpacionEditar = palpacionServicio.buscarPorId((int) tabla.getValueAt(fila, 0));
        
        //Opciones editables
        String[] opciones = {
            "Fecha",
            "Tipo",
            "Observaciones"
        };
        
        String campo = (String) JOptionPane.showInputDialog(null,
                "Seleccione el campo a editar:", "Editar Palpacion",
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        if (campo == null) return;
        
        switch (campo) {
            case "Fecha":
                String pFecha = JOptionPane.showInputDialog("Fecha (YYYY-MM-DD):", palpacionEditar.getFechaPalpacion());
                if (pFecha != null && !pFecha.trim().isEmpty()) palpacionEditar.setFechaPalpacion(pFecha.trim());
                break;
                
            case "Tipo":
                EnumTipoPalpacion[] tipoValores = EnumTipoPalpacion.values();
                EnumTipoPalpacion tipoSel = (EnumTipoPalpacion) JOptionPane.showInputDialog(null,
                        "Tipo de palpacion:", "Editar", JOptionPane.QUESTION_MESSAGE,
                        null, tipoValores, palpacionEditar.getTipoPalpacion());
                if (tipoSel != null) palpacionEditar.setTipoPalpacion(tipoSel);
                break;
                
            case "Observaciones":
                String pObs = JOptionPane.showInputDialog("Observaciones:", palpacionEditar.getObservaciones());
                if (pObs != null) palpacionEditar.setObservaciones(pObs.trim());
                break;
        }
        
        palpacionServicio.actualizarPalpacion(palpacionEditar);
        cargarTabla();
    }

    /**
     * Elimina la palpacion seleccionada tras confirmacion.
     */
    public void eliminar() {
        //Obtener el indice de la fila seleccionada en la GUI haciendo click en ella.
        int fila = tabla.getSelectedRow();

        //Validar que la fila ha sido seleccionada. 'getSelectedRow()' devuelve -1 si la fila no ha sido seleccionada.
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila. Haga click en una para seleccionarla.");
            return;
        }

        //Extraer el id de la palpacion a eliminar.
        int idPalpacion = (int) tabla.getValueAt(fila, 0);

        //Eliminar la palpacion usando el servicio.
        int seleccion = JOptionPane.showConfirmDialog(null,
                "¿Seguro que desea eliminar el registro?", //Mensaje
                "Eliminar", //Titulo de la ventana
                JOptionPane.YES_NO_OPTION //Botones de "Si", "No".
        );
        if (seleccion == JOptionPane.YES_OPTION) {
            //Valor 0 para la opcion "Si"
            palpacionServicio.eliminar(idPalpacion);
            JOptionPane.showMessageDialog(null, "Registro eliminado correctamente.");

            //Recargar tabla con datos actualizados.
            cargarTabla();
        }
    }
}
