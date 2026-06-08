package co.upc.ganado.presentacion;

import co.upc.ganado.entidades.Vacuna;
import co.upc.ganado.servicios.VacunaService;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;

/**
 * Panel de gestion de vacunas del catalogo veterinario. Muestra una tabla
 * con todas las vacunas registradas y proporciona metodos para las
 * operaciones CRUD: crear, editar y eliminar vacunas.
 * <p>
 * Este panel se inserta dentro de un JTabbedPane en el MainFrame y
 * utiliza un VacunaService para la logica de negocio y persistencia.
 *
 * @author Santiago Rafael Zuleta Neira
 */
public class VacunaPanel extends JPanel {

    private VacunaService vacunaServicio;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    /**
     * Crea un nuevo panel de vacunas.
     *
     * @param vs Servicio de vacunas que provee los datos y la logica
     *           de negocio
     */
    public VacunaPanel(VacunaService vs) {
        this.vacunaServicio = vs;
        inicializarPanel();
        cargarTabla();
    }

    /**
     * Inicializa los componentes graficos del panel.
     */
    private void inicializarPanel() {
        this.setLayout(new BorderLayout());

        String[] columnas = {"ID Vacuna", "Nombre", "Descripcion",
                             "Dosis Estandar"};

        modeloTabla = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modeloTabla);

        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    /**
     * Recarga los datos de la tabla desde el servicio.
     */
    public void cargarTabla() {
        modeloTabla.setRowCount(0);
        
        for (Vacuna v : vacunaServicio.mostrarTodo()) {
            modeloTabla.addRow(new Object[]{
                v.getIdVacuna(),
                v.getNombreVacuna(),
                v.getDescripcion(), //Nullable
                v.getDosisEstandar()
            });
        }
    }

    /**
     * Abre un dialogo para crear una nueva vacuna.
     */
    public void nuevo() {
        //HashMap para validar el ID duplicado.
        HashMap<Integer, Vacuna> mapaVacunasId = new HashMap<>();
        for (Vacuna v : vacunaServicio.mostrarTodo()) {
            mapaVacunasId.put(v.getIdVacuna(), v);
        }
        
        //-----------ENTRADA DE DATOS---------//
        
        //ID
        String idVacuna = JOptionPane.showInputDialog("Ingrese el ID de la vacuna:");
        if (idVacuna == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        if (idVacuna.trim().isEmpty()) { JOptionPane.showMessageDialog(null, "El ID no puede estar vacio."); return; }
        
        try {
            int prueba = Integer.parseInt(idVacuna);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El valor debe ser numerico.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (mapaVacunasId.get(Integer.parseInt(idVacuna)) != null) { JOptionPane.showMessageDialog(null, "El ID ingresado ya existe.", "Advertencia", JOptionPane.WARNING_MESSAGE); return; }
        
        //Nombre
        String nombreVacuna = JOptionPane.showInputDialog("Ingrese el nombre de la vacuna:");
        if (nombreVacuna == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        if (nombreVacuna.trim().isEmpty()) { JOptionPane.showMessageDialog(null, "El nombre no puede estar vacio."); return; }
        
        //Descripcion (puede ser vacia)
        String descripcion = JOptionPane.showInputDialog("Ingrese una descripcion (o Enter si no aplica):");
        if (descripcion == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        
        //Dosis estandar
        String dosisEstandar = JOptionPane.showInputDialog("Ingrese la dosis estandar en ml:");
        if (dosisEstandar == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        if (dosisEstandar.trim().isEmpty()) { JOptionPane.showMessageDialog(null, "La dosis estandar no puede estar vacia."); return; }
        
        //Insertar
        Vacuna nuevaVacuna = new Vacuna(Integer.parseInt(idVacuna), nombreVacuna.trim(),
                descripcion.trim(), dosisEstandar.trim());
        vacunaServicio.insertar(nuevaVacuna);
        
        cargarTabla();
    }

    /**
     * Abre un dialogo para editar la vacuna seleccionada.
     */
    public void editar() {
        //Seleccionar fila de la tabla.
        int fila = tabla.getSelectedRow();
        
        //Validar que la fila ha sido seleccionada.
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila. Haga click en una para seleccionarla.");
            return;
        }
        
        //Obtener la vacuna a editar.
        Vacuna vacunaEditar = vacunaServicio.buscarPorId((int) tabla.getValueAt(fila, 0));
        
        //Opciones editables
        String[] opciones = {
            "Nombre",
            "Descripcion",
            "Dosis Estandar"
        };
        
        String campo = (String) JOptionPane.showInputDialog(null,
                "Seleccione el campo a editar:", "Editar Vacuna",
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        if (campo == null) return;
        
        switch (campo) {
            case "Nombre":
                String vNombre = JOptionPane.showInputDialog("Nombre:", vacunaEditar.getNombreVacuna());
                if (vNombre != null && !vNombre.trim().isEmpty()) vacunaEditar.setNombreVacuna(vNombre.trim());
                break;
                
            case "Descripcion":
                String vDesc = JOptionPane.showInputDialog("Descripcion:", vacunaEditar.getDescripcion());
                if (vDesc != null) vacunaEditar.setDescripcion(vDesc.trim());
                break;
                
            case "Dosis Estandar":
                String vDosis = JOptionPane.showInputDialog("Dosis estandar:", vacunaEditar.getDosisEstandar());
                if (vDosis != null && !vDosis.trim().isEmpty()) vacunaEditar.setDosisEstandar(vDosis.trim());
                break;
        }
        
        vacunaServicio.actualizarDetalle(vacunaEditar);
        cargarTabla();
    }

    /**
     * Elimina la vacuna seleccionada tras confirmacion.
     */
    public void eliminar() {
        //Obtener el indice de la fila seleccionada en la GUI haciendo click en ella.
        int fila = tabla.getSelectedRow();

        //Validar que la fila ha sido seleccionada. 'getSelectedRow()' devuelve -1 si la fila no ha sido seleccionada.
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila. Haga click en una para seleccionarla.");
            return;
        }

        //Extraer el id de la vacuna a eliminar.
        int idVacuna = (int) tabla.getValueAt(fila, 0);

        //Eliminar la vacuna usando el servicio.
        int seleccion = JOptionPane.showConfirmDialog(null,
                "¿Seguro que desea eliminar el registro?", //Mensaje
                "Eliminar", //Titulo de la ventana
                JOptionPane.YES_NO_OPTION //Botones de "Si", "No".
        );
        if (seleccion == JOptionPane.YES_OPTION) {
            //Valor 0 para la opcion "Si"
            vacunaServicio.eliminar(idVacuna);
            JOptionPane.showMessageDialog(null, "Registro eliminado correctamente.");

            //Recargar tabla con datos actualizados.
            cargarTabla();
        }
    }
}
