package co.upc.ganado.presentacion;


import co.upc.ganado.entidades.Hembra;
import co.upc.ganado.servicios.HembraService;
import co.upc.ganado.entidades.enums.EnumEstadoReproductivo;
import co.upc.ganado.entidades.enums.EnumEstadoSalud;
import co.upc.ganado.entidades.enums.EnumSexo;

import java.awt.BorderLayout;
import java.awt.ScrollPane; //Para usar JScrollPane
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class HembraPanel extends JPanel {
    
    HembraService hembraServicio;
    
    JTable tabla;
    DefaultTableModel modeloTabla;

    //Constructor
    public HembraPanel(HembraService hs) {
        //Inicialzar servicio
        this.hembraServicio = hs;
        inicializarPanel();
        cargarTabla();
    }
    
    //Métodos
    
    public void inicializarPanel() {
        //Definir el layout del panel.
        setLayout(new BorderLayout()); //Par que se centre y ocupe todo el espacio disponible.
        
        //Definir los títulos de las columnas.
        String[] columnas = {"ID", 
                             "Marca", 
                             "Sexo", 
                             "Peso", 
                             "Finca", 
                             "Estado Salud", 
                             "Estado Reproductivo", 
                             "Partos", 
                             "Apta Reproduccion"
                            };
        
        //Asignar las columnas al modelo de la tabla.
        modeloTabla = new DefaultTableModel(columnas, 0);
        
        //Asignar el modelo de tabal a la tabla.
        tabla = new JTable(modeloTabla);
        
        //Añadir componente(tabla) al panel.
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        
    }
    
    

    public void cargarTabla() {
        modeloTabla.setRowCount(0);
        
        for (Hembra h : hembraServicio.mostrarTodo()) {
            modeloTabla.addRow(new Object[]{
                h.getIdGanado(),
                h.getNumeroMarca(),
                h.getTipoSexo(),
                h.getPeso(),
                h.getIdFinca(),
                h.getEstadoSalud(),
                h.getEstadoReproductivoActual(),
                h.getNumeroPartos(),
                h.isAptaParaReproduccion()
            });
        }
    }
    
    
    public void nuevo() {
        //Extraer los estados reproductivos, sexo y estado de salud.
         EnumEstadoReproductivo[] estados = EnumEstadoReproductivo.values();
         EnumSexo[] sexos = EnumSexo.values();
         EnumEstadoSalud[] saludValores = EnumEstadoSalud.values();
        
        String idGanado = JOptionPane.showInputDialog("Ingrese el ID del ganado:");
        if (idGanado == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        if (idGanado.trim().isEmpty()) { JOptionPane.showMessageDialog(null, "El ID no puede estar vacio."); return; }
        
        String numeroMarca = JOptionPane.showInputDialog("Ingrese el numero de marca:");
        if (numeroMarca == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        if (numeroMarca.trim().isEmpty()) { JOptionPane.showMessageDialog(null, "La marca no puede estar vacia."); return; }
        
        EnumSexo tipoSexo = (EnumSexo) JOptionPane.showInputDialog(null, "Seleccione el sexo:", "Sexo", JOptionPane.QUESTION_MESSAGE, null, sexos, sexos[0]);
        if (tipoSexo == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        
        String peso = JOptionPane.showInputDialog("Ingrese el peso:");
        if (peso == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        if (peso.trim().isEmpty()) { JOptionPane.showMessageDialog(null, "El peso no puede estar vacio."); return; }
        
       
        EnumEstadoSalud estadoSalud = (EnumEstadoSalud) JOptionPane.showInputDialog(null, "Seleccione el estado de salud:", "Estado Salud", JOptionPane.QUESTION_MESSAGE, null, saludValores, saludValores[0]);
        if (estadoSalud == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        
        String idFinca = JOptionPane.showInputDialog("Ingrese el ID de la finca:");
        if (idFinca == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        if (idFinca.trim().isEmpty()) { JOptionPane.showMessageDialog(null, "El ID de la finca no puede estar vacio."); return; }
        
        EnumEstadoReproductivo estadoReproductivo = (EnumEstadoReproductivo) JOptionPane.showInputDialog(null, "Ingrese el estado reproductivo:", "Estado", JOptionPane.QUESTION_MESSAGE, null, estados, estados[0]);
        if (estadoReproductivo == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        
        String fechaUltimoParto = JOptionPane.showInputDialog("Ingrese la fecha del ultimo parto (YYYY-MM-DD) o Enter si no tiene:");
        if (fechaUltimoParto == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        
        String numeroPartos = JOptionPane.showInputDialog("Ingrese el numero de partos:");
        if (numeroPartos == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        if (numeroPartos.trim().isEmpty()) { JOptionPane.showMessageDialog(null, "El numero de partos no puede estar vacio."); return; }
        
        int aptaReproduccion = JOptionPane.showConfirmDialog(null, "La hembra es apta para reproduccion?", "Reproduccion", JOptionPane.YES_NO_OPTION);
        if (aptaReproduccion == JOptionPane.CLOSED_OPTION) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; } //Cuando el usuario cierra la ventana sin seleccionar ninguna de las opciones disponibles.
        
        //Insertar.
        Hembra nueva = new Hembra(estadoReproductivo, fechaUltimoParto,
                Integer.parseInt(numeroPartos), aptaReproduccion == JOptionPane.YES_OPTION, //Convierte el valor a boolean, y tira 'true' o 'false' según si la condición se cumple o no.
                Integer.parseInt(idGanado), numeroMarca,
                tipoSexo, Double.parseDouble(peso),
                estadoSalud, Integer.parseInt(idFinca));
        hembraServicio.insertar(nueva);
        
        cargarTabla();
    }
    

    public void editar() {
        
        //Seleccionar fila des de la GUI.
        int fila = tabla.getSelectedRow();
        
        //Validar que la fila haya sido seleccionada.
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila.");
            return;
        }

        Hembra vaca = hembraServicio.buscarPorId((int) tabla.getValueAt(fila, 0));

        //Opciones.
        String[] campos = {"Marca", "Peso", "Estado Salud", "Finca",
                           "Estado Reproductivo", "Fecha Ultimo Parto",
                           "Numero Partos", "Apta Reproduccion"};
        
        
        String campo = (String) JOptionPane.showInputDialog(null,
                                                            "Seleccione el campo a editar:", 
                                                            "Editar Hembra",
                                                            JOptionPane.QUESTION_MESSAGE, 
                                                            null, 
                                                            campos, 
                                                            campos[0]);
        if (campo == null) return;

        switch (campo) {
            case "Marca":
                String vMarca = JOptionPane.showInputDialog("Marca:", vaca.getNumeroMarca());
                if (vMarca != null && !vMarca.trim().isEmpty()) vaca.setNumeroMarca(vMarca.trim());
                break;
                
                
            case "Peso":
                String vPeso = JOptionPane.showInputDialog("Peso:", vaca.getPeso());
                if (vPeso != null && !vPeso.trim().isEmpty()) vaca.setPeso(Double.parseDouble(vPeso.trim()));
                break;
                
                
            case "Estado Salud":
                EnumEstadoSalud[] saludValores = EnumEstadoSalud.values();
                EnumEstadoSalud saludSel = (EnumEstadoSalud) JOptionPane.showInputDialog(null,
                        "Estado salud:", "Editar", JOptionPane.QUESTION_MESSAGE,
                        null, saludValores, vaca.getEstadoSalud());
                if (saludSel != null) vaca.setEstadoSalud(saludSel);
                break;
                
                
            case "Finca":
                String vFinca = JOptionPane.showInputDialog("ID de finca:", vaca.getIdFinca());
                if (vFinca != null && !vFinca.trim().isEmpty()) vaca.setIdFinca(Integer.parseInt(vFinca.trim()));
                break;
                
                
            case "Estado Reproductivo":
                EnumEstadoReproductivo[] erValores = EnumEstadoReproductivo.values();
                EnumEstadoReproductivo seleccionarEstado = (EnumEstadoReproductivo) JOptionPane.showInputDialog(null,
                        "Estado reproductivo:", "Editar", JOptionPane.QUESTION_MESSAGE,
                        null, erValores, vaca.getEstadoReproductivoActual());
                if (seleccionarEstado != null) vaca.setEstadoReproductivoActual(seleccionarEstado);
                break;
                
                
            case "Fecha Ultimo Parto":
                String vFecha = JOptionPane.showInputDialog("Fecha ultimo parto (YYYY-MM-DD):", vaca.getFechaUltimoParto());
                if (vFecha != null) vaca.setFechaUltimoParto(vFecha.trim());
                break;
                
                
            case "Numero Partos":
                String vPartos = JOptionPane.showInputDialog("Numero de partos:", vaca.getNumeroPartos());
                if (vPartos != null && !vPartos.trim().isEmpty()) vaca.setNumeroPartos(Integer.parseInt(vPartos.trim()));
                break;
                
                
            case "Apta Reproduccion":
                int r = JOptionPane.showConfirmDialog(null, "Apta para reproduccion?",
                        "Reproduccion", JOptionPane.YES_NO_OPTION);
                if (r != JOptionPane.CLOSED_OPTION) vaca.setAptaParaReproduccion(r == JOptionPane.YES_OPTION); //'r == JOptionPane.YES_OPTION' es lo mismo que '0 == 0 || 1 == 0' eso devuelve un bool.
                break;
        }

        hembraServicio.actualizarDetalle(vaca);
        cargarTabla();
    }

    public void eliminar() {
        //Obtener el índice de la fila seleccionada en la GUI haciendo click en ella.
        int fila = tabla.getSelectedRow();
        
        //Validar que la fila ha sido seleccionada. 'getSelectedRow()' devuelve -1 si la fila no ha sido seleccionada.
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila. Haga click en una para seleccionarla.");
            return;
        }
        
        //Extraer el id de la vaca a eliminar.
        int idVaca= (int) tabla.getValueAt(fila, 0);
        
        //Eliminar la vaca usando el servicio.
        int seleccion = JOptionPane.showConfirmDialog(null, 
                                                      "¿Seguro que desea eliminar el registro?", //Mensaje
                                                      "Eliminar", //Título de la ventana
                                                      JOptionPane.YES_NO_OPTION //Botones de "Si", "No".
                                                     );
        if (seleccion == JOptionPane.YES_OPTION) {
            //Valor 0 para la opción "Si"
           hembraServicio.eliminar(idVaca);
            JOptionPane.showMessageDialog(null, "Registro eliminado correctamente.");
            
            //Recargar tabla con datos actualizados.
            cargarTabla();
            
        }
    }
    
    
}
