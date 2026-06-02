package co.upc.ganado.presentacion;

import co.upc.ganado.entidades.Finca;
import co.upc.ganado.servicios.FincaService; //Para extraer los datos de las fincas.

import javax.swing.*; //Para poder usar JOptionPane, JTable, entre otros
import javax.swing.table.DefaultTableModel; //Para crear el modelo de tabla e insertar los datos en la misma.
import java.awt.*; //Para poder implementar los layouts.

/**
 * Panel de gestion de fincas. Muestra una tabla con todas las fincas
 * y proporciona metodos para las operaciones CRUD: crear, editar y
 * eliminar fincas.
 * Este panel se inserta dentro de un JTabbedPane en el MainFrame y
 * utiliza un FincaService para la logica de negocio y persistencia.
 *
 * @author Santiago Rafael Zuleta Neira
 */
public class FincaPanel extends JPanel {

    private FincaService service;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    /**
     * Crea un nuevo panel de fincas.
     *
     * @param service Servicio de fincas que provee los datos y la logica
     *                de negocio
     */
    public FincaPanel(FincaService service) {
        this.service = service;
        inicializarPanel();
        cargarTabla();
    }

    /**
     * Inicializa los componentes graficos del panel.
     */
    private void inicializarPanel() {
        setLayout(new BorderLayout()); //Para que el panel ocupe todo el espcio disponible del JTabbedPane (alto y ancho)

        String[] columnas = {"ID", "Nombre", "Ubicacion"}; //Los títulos de las columnas.
        modeloTabla = new DefaultTableModel(columnas, 0); //Crea el modelo de tabla con 3 columnas pero sin filas.
        tabla = new JTable(modeloTabla); //Asigna el modelo a la tabla.
        
        //Mete la tabla dentro de un ScrollPane (Barras de desplazamiento) por si la tabla tiene más filas de las que caben en la pantalla.
        //Con 'BorderLayout.CENTER' centra el ScrollPane con todo y tabla para que ocupe todo el espacio disponible.
        //Este es un componente que se añade al panel.
        this.add(new JScrollPane(tabla), BorderLayout.CENTER); 
    }

    /**
     * Recarga los datos de la tabla desde el servicio.
     * Limpia el modelo y lo vuelve a llenar con todas las fincas.
     */
    public void cargarTabla() {
        modeloTabla.setRowCount(0); //Limpia las filas, evita que se muestren datos duplicados cada vez que se cargue la tabla con sus datos
        
        //Extraer los datos de las fincas.
        for (Finca f : service.mostrarTodo()) {
            
            //Añadir datos a cada fila. Con 'Object[]' se puede tener un arreglo que tenga distintos tipos de dato.
            modeloTabla.addRow(new Object[]{
                f.getIdFinca(),
                f.getNombreFinca(),
                f.getUbicacion()
            });
        }
    }

    /**
     * Abre un dialogo para crear una nueva finca.
     * Solicita nombre y ubicacion, luego inserta la finca
     * a traves del servicio y recarga la tabla.
     */
    public void nuevo() {
        String idFinca = JOptionPane.showInputDialog(null, "Ingrese el id de la finca:", "Finca");
        String nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre de la finca:", "Finca");
        String ubicacion = JOptionPane.showInputDialog(null, "Ingrese la ubicacion de la finca (Si no tiene, pulse 'Enter'):", "Finca");
        
        //Si se cierra algun cuadro de diálogo o se presiona el boton de cancelar, se aborta toda la operacion.
        if (idFinca == null || nombre == null || ubicacion == null) {
            JOptionPane.showMessageDialog(null, "Operación cancelada.");
            return;
        }
        
        //Insertar datos.
        service.insertar(new Finca(Integer.parseInt(idFinca), nombre, ubicacion));
        
        //REcargar tabla con los datos actualizados.
        cargarTabla();
    }

    
    /**
     * Abre un dialogo para editar la finca seleccionada.
     * Obtiene la fila seleccionada de la tabla, muestra los
     * datos actuales y permite modificarlos.
     */
    public void editar() {
        //Obtnere índice de la fila seleccionada en la GUI.
        int fila = tabla.getSelectedRow();
        
        //Validar que la fila ha sido seleccionada. 'getSelectedRow()' devuelve -1 si la fila no ha sido seleccionada.
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila. Haga click en una para seleccionarla.");
            return;
        }
        
        //Extraer el id de la finca.
        int idFinca = (int) tabla.getValueAt(fila, 0);
        
        //Obtener la finca a actualizar.
        Finca fincaActualizar = service.buscarPorId(idFinca);
        
        
        //Actualizar los datos. Usar menú de opciones con JOptionPane.
       
        String[] opciones = {"nombre", "ubicacion"};
        
        
        int opcion = JOptionPane.showOptionDialog(null, 
                                                  "¿Qué datos desea actualizar?", 
                                                  "Actualizar datos", //Título de la ventana.
                                                  JOptionPane.DEFAULT_OPTION, //Para que use las opciones dadas en el arreglo 'opciones' como botones.
                                                  JOptionPane.QUESTION_MESSAGE, //ícono. 
                                                  null, //Usa el ícono personalizado, pero como no hay, null indica el ícono por defecto.
                                                  opciones, //Arreglo de opciones.
                                                  opciones[0] //Opcion seleccionada por defecto.
                                                 );
        
        //Las opciones van desde 0 hasta n, siendo n la cantidad de opciones disponibles.
        //-1 significa que se cerro el dialogo sin seleccionar nada.
        switch (opcion) {
            case 0:
                String nuevoNombre = JOptionPane.showInputDialog(null,"Ingrese el nuevo nombre:", fincaActualizar.getNombreFinca()); //getter para rellenar el campo de texto con el dato original para que se sepa exactametne qué dato se va a modificar.
                if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                    fincaActualizar.setNombreFinca(nuevoNombre.trim());
                    service.actualizarDetalle(fincaActualizar);
                    cargarTabla();
                }
                break;
                
            case 1:
                String nuevaUbicacion = JOptionPane.showInputDialog(null,"Ingrese la nueva ubicacion:", fincaActualizar.getUbicacion());
                if (nuevaUbicacion != null) {
                    fincaActualizar.setUbicacion(nuevaUbicacion.trim());
                    service.actualizarDetalle(fincaActualizar);
                    cargarTabla();
                }
                break;
            default:
                //dialogo cerrado o cancelado, no hacer nada
                break;
        }
    }

    /**
     * Elimina la finca seleccionada.
     * Muestra un dialogo de confirmacion antes de eliminar
     * y recarga la tabla si se confirma la operacion.
     */
    public void eliminar() {
        //Obtener el índice de la fila seleccionada en la GUI haciendo click en ella.
        int fila = tabla.getSelectedRow();
        
        //Validar que la fila ha sido seleccionada. 'getSelectedRow()' devuelve -1 si la fila no ha sido seleccionada.
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila. Haga click en una para seleccionarla.");
            return;
        }
        
        //Extraer el id de la finca.
        int idFinca = (int) tabla.getValueAt(fila, 0);
        
        //Eliminar la finca usando el servicio.
        int seleccion = JOptionPane.showConfirmDialog(null, 
                                                      "¿Seguro que desea eliminar el registro?", //Mensaje
                                                      "Eliminar", //Título de la ventana
                                                      JOptionPane.YES_NO_OPTION //Botones de "Si", "No".
                                                     );
        if (seleccion == JOptionPane.YES_OPTION) {
            //Valor 0 para la opción "Si"
            service.eliminar(idFinca);
            JOptionPane.showMessageDialog(null, "Registro eliminado correctamente.");
            
            //Recargar tabla con datos actualizados.
            cargarTabla();
            
        }
        
        //Si la opción es "No", devuelve 1, si se cierra la ventana si escoger nada, devuelve -1. En ambos casos no se hace nada.
        //Por eso no se incluyen.
        
    }
}
