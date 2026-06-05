package co.upc.ganado.presentacion;

import co.upc.ganado.servicios.*;
import javax.swing.*; //Importa todos los componentes visuales básicos de Java (ventanas, botones, tablas, combos, etc.).
import javax.swing.table.DefaultTableModel;
import java.awt.*;  //Importa las herramientas de diseño y dibujo, como los layouts (para organizar elementos) y los colores.
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
/**
 * Panel de consultas y reportes del sistema. Proporciona acceso a las
 * 7 consultas del negocio mediante un selector, campo de parametro
 * opcional y tabla de resultados.
 * <p>
 * Las consultas disponibles son:
 * <ol>
 *   <li>Trazabilidad de animal (requiere idGanado)</li>
 *   <li>Estado reproductivo del hato</li>
 *   <li>Historial de palpaciones (requiere idGanado)</li>
 *   <li>Control de vacunacion</li>
 *   <li>Inventario por finca</li>
 *   <li>Historial de compras</li>
 *   <li>Historial de padrotes</li>
 * </ol>
 *
 */
public class ConsultasPanel extends JPanel {

    private final TrasladoService trasladoService;
    private final HembraService hembraService;
    private final PalpacionService palpacionService;
    private final VacunaService vacunaService;
    private final FincaService fincaService;
    private final CompraService compraService;
    private final MachoService machoService;

    private JComboBox<String> listaOpciones;
    private JLabel idGanado;
    private JTextField txtIdGanado;
    private JButton btnConsultar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    /**
     * Crea el panel de consultas.
     *
     * @param trasladoService  servicio para consulta de trazabilidad
     * @param hembraService    servicio para consulta reproductiva
     * @param palpacionService servicio para historial de palpaciones
     * @param vacunaService    servicio para control de vacunacion
     * @param fincaService     servicio para inventario por finca
     * @param compraService    servicio para historial de compras
     * @param machoService     servicio para historial de padrotes
     */
    public ConsultasPanel(TrasladoService trasladoService,
                          HembraService hembraService,
                          PalpacionService palpacionService,
                          VacunaService vacunaService,
                          FincaService fincaService,
                          CompraService compraService,
                          MachoService machoService) {
        this.trasladoService = trasladoService;
        this.hembraService = hembraService;
        this.palpacionService = palpacionService;
        this.vacunaService = vacunaService;
        this.fincaService = fincaService;
        this.compraService = compraService;
        this.machoService = machoService;

        inicializarPanel();
        //cargarTabla();
    }

    /**
     * Inicializa los componentes graficos del panel.
     * Dispone un panel superior con el selector de consulta,
     * el campo de idGanado y el boton Consultar, y un
     * JScrollPane con la tabla de resultados en el centro.
     */
    private void inicializarPanel() {
        //Definir layout del panel.
        this.setLayout(new BorderLayout()); //Para que ocupe todo el espacio disponible.
        
        
        //Definir opciones del JComboBox.
        String[] opciones = {"Trazabilidad de un animal",
                             "Estado reproductivo del hato",
                             "Historial de palpaciones por hembra",
                             "Control de vacunación",
                             "Inventario activo por finca",
                             "Historial de compras",
                             "Seguimiento del padrote"
                            };
        
        
        //---------------------Panel superior y componentes.---------------------
        
        
        
        //----------COMPONENTES----------
        
        //JLabel y TextField para las consulta 1 y 3.
        
        JLabel selOpcion = new JLabel("Consultas: ");
        idGanado = new JLabel("ID Ganado: ");//para la consutla 1 y 3.
        
        txtIdGanado = new JTextField();
        txtIdGanado.setColumns(10); //tamaño del campo.
        txtIdGanado.addActionListener(e -> cargarTabla()); //Para validar los datos al precionar 'Enter'
        
        
         //----------Tablas con los datos de las consultas----------
        
        modeloTabla = new DefaultTableModel(); //Modelo vacío al iniciar el programa.
        tabla = new JTable(modeloTabla);
        
        
        //-----COMBOBOX-----
        
        //Añadir opciones al combobox.
        listaOpciones = new JComboBox(opciones);
        

        listaOpciones.addActionListener(añadirActionListener());
        
        //Definir opción por defecto.
        listaOpciones.setSelectedIndex(0); //Primera opción por defecto (Trazabilidad de un animal).
        
        
        
        
        //----------PANEL SUPERIOR----------
        
        //Panel superior para el JComboBox. Inserción de componentes.
        JPanel panelSuperior = new JPanel();
        
        //Definir Layout del panel superior: Elementos en horizontal alineados a la izquierda.
        panelSuperior.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        
        
        
        //----------INSERTAR CAMPOS EN EL PANEL SUPERIOR----------
        
        panelSuperior.add(selOpcion);
        panelSuperior.add(listaOpciones); //Mete el ComboBox en el panel superior.
        panelSuperior.add(idGanado);
        panelSuperior.add(txtIdGanado);
        
        
        
       
        
        
        
        
        //-------------------------------PANEL PRINCIPAL----------------------------------
        
        //añadir el panel superior al panel principal.
        this.add(panelSuperior, BorderLayout.NORTH);
        
        //Añadir la tabla (inicialmente vacía) al panel principal, que ocupe todo el espacio disponible.
        this.add(new JScrollPane(tabla), BorderLayout.CENTER);

    }

    
    
    
    
    
    /**
     * Ejecuta la consulta seleccionada en el combo box y
     * actualiza la tabla con los resultados obtenidos del
     * servicio correspondiente.
     */
    public void cargarTabla() {
        //Limpiar la tabla.
        modeloTabla.setRowCount(0);
        
        //Configurar y rellenar tabla según la opción seleccionada.
        
        int opcion = listaOpciones.getSelectedIndex();
        
        switch (opcion) {
            
            case 0:
                String[] columnasTrazabilidad = {"Animal",
                                     "Fecha Traslado",
                                     "Motivo",
                                     "Finca Origen",
                                     "Finca Destino",
                                     "Observaciones"
                                    };
                cargarConsulta(columnasTrazabilidad);
                
                
                
                break;
                
                
            case 1:
                String[] columnasEstado = {"Estado Reproductivo", "Total Hembras"};

                //Obtener la lista de datos de la consutla.
                List<String[]> datosEstado = hembraService.getEstadoReproductivoHato();
                
                cargarConsulta(columnasEstado, datosEstado);
                break;
                
            
                
            case 2:
                String[] columnasPalpaciones = {"Animal",
                                     "Fecha Palpacion",
                                     "Resultado",
                                     "Fecha concepcion"
                                    };
                
                cargarConsulta(columnasPalpaciones);
                break;
                
             
            case 3:
                String[] columnasVacunacion = {"Animal",
                                               "Vacuna",
                                               "Fecha Aplicacion",
                                               "dosis",
                                               "Responsable"
                                              };
                
                
                //Obtener la lista de datos de la consutla.
                List<String[]> datosVacunacion = vacunaService.getControlVacunacion();
                
                //Cargar consulta.
                cargarConsulta(columnasVacunacion, datosVacunacion);
                break;
             
            
            case 4:
                String[] columnasInventario = {"Finca",
                                               "Machos",
                                               "Hembras",
                                               "Total"
                                              };
                
                List<Object[]> datosInventario = fincaService.getInventarioPorFinca();
                
                cargarConsulta(columnasInventario, datosInventario);
                break;
                
                
            case 5:
                String[] columnasCompra = {"Compra",
                                           "Fecha Compra",
                                           "Proveedor",
                                           "Animal",
                                           "Precio Individual",
                                           "Valor Total",
                                           "Responsable"
                                          };
                
                List<String[]> datosCompra = compraService.getHistorialCompras();
                
                cargarConsulta(columnasCompra, datosCompra);
                break;
                
            case 6:
                String[] columnasPadrote = {"Animal",
                                            "Calidad Reproductiva",
                                            "Fecha Inicio",
                                            "Fecha Fin",
                                            "Es padrote"
                                           };
                
                List<String[]> datosPadrote = machoService.getHistorialPadrotes();
                
                cargarConsulta(columnasPadrote, datosPadrote);
                break;
        }
    }

    /**
     * Cambia la visibilidad del campo txtIdGanado y su
     * etiqueta segun la consulta seleccionada.
     *
     * @param visible true para mostrar, false para ocultar
     */
    private void mostrarCampoId(boolean visible) {
        idGanado.setVisible(visible);
        txtIdGanado.setVisible(visible);
    }
    
    
    
//    /**
//     * Actualiza las columnas de la tabla segun la consulta
//     * seleccionada en el combo box.
//     */
//    private void configurarColumnas() {
//        
//    }
    
    //Método auxiliar
    private ActionListener añadirActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               String opcion =  (String) listaOpciones.getSelectedItem();
               
               if (opcion.equals("Historial de palpaciones por hembra") || opcion.equals("Trazabilidad de un animal")) {
                   mostrarCampoId(true);
               } else {
                   mostrarCampoId(false);
               }
               
               //Aquí y no en el constructor dado que las tablas dependen de la opción escogida.
               cargarTabla();
            }
        };
    }
    
    
    //Método auxiliar generico para cargar las tablas de consulta.
    private <T> void cargarConsulta(String[] columnas, List<T[]> datos) {
        
        //Añadir columnas al modelo de la tabla.
        modeloTabla.setColumnIdentifiers(columnas);//Reescribe las columnas anteriores por las nuevas de esta consulta.
        
        //Añadir los datos.
        for (T[] fila : datos) {
            
            modeloTabla.addRow(fila);
        }
    }
    
    //Método auxiliar genérico sobrecargado para la consulta 1 y 3 .
    private void cargarConsulta(String[] columnas) {
        //Según la opcíon del ComboBox se utiliza alguno de estos.
        List<String[]> datosTrazabilidad = new ArrayList<>();
        List<String[]> datosPalpaciones = new ArrayList<>();
        
        //Añadir columnas al modelo de la tabla.
        modeloTabla.setColumnIdentifiers(columnas);//Reescribe las columnas anteriores por las nuevas de esta consulta.
        
        if (txtIdGanado.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el ID para realizar la consulta.");
            return;
        }

        try {

            //Extraer el dato ingresado en el campo de texto.
            int idIngresado = Integer.parseInt(txtIdGanado.getText().trim());

            if (listaOpciones.getSelectedIndex() == 0) {
                //Consulta 1: obtener datos.
                datosTrazabilidad = trasladoService.getTrazabilidadAnimal(idIngresado);
                
                if (datosTrazabilidad.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El animal ingresado no tiene registro de traslados o no existe.", null, JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                
                //Añadir los datos.
                for (String[] fila : datosTrazabilidad) {
                    modeloTabla.addRow(fila);
                }
                
            } else {
                //Consulta 3: Obtener datos.
                datosPalpaciones = palpacionService.getHistorialPalpaciones(idIngresado);
                
                //Si el id ingresado corresponde a un macho o a un animal que no existe en el sistema, se detiene el 'switch'.
                if (datosPalpaciones == null) {
                    JOptionPane.showMessageDialog(null, "El animal ingresado es macho o no existe.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                //Añadir los datos.
                for (String[] fila : datosPalpaciones) {
                    modeloTabla.addRow(fila);
                }
            }

            

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Debe digitar un número", "Advertencia", JOptionPane.WARNING_MESSAGE);

            txtIdGanado.setText(""); //Limpiar campo.

        }
    }
    
    
}
