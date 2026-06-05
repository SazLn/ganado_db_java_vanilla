package co.upc.ganado.presentacion;

import co.upc.ganado.entidades.Ganado;
import co.upc.ganado.entidades.Macho;
import co.upc.ganado.servicios.MachoService;
import co.upc.ganado.servicios.GanadoService;

import co.upc.ganado.entidades.enums.EnumCalidadReproductiva;
import co.upc.ganado.entidades.enums.EnumEstadoSalud;
import co.upc.ganado.entidades.enums.EnumSexo;

import java.awt.BorderLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.util.HashMap;

/**
 * Panel de gestion de ganado macho. Muestra una tabla con todos los
 * machos registrados y proporciona metodos para las operaciones CRUD:
 * crear, editar y eliminar registros de machos.
 * <p>
 * Este panel se inserta dentro de un JTabbedPane en el MainFrame y
 * utiliza un MachoService para la logica de negocio y persistencia.
 *
 * @author Santiago Rafael Zuleta Neira
 */
public class MachoPanel extends JPanel {
    private GanadoService ganadoServicio;
    private MachoService machoServicio;
    
    
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    

    //CONSTRUCTOR
    public MachoPanel(MachoService ms, GanadoService gs) {
        machoServicio = ms;
        ganadoServicio = gs;
        
        inicializarPanel();
        cargarTabla();
    }
    
    //------------------------MÉTODOS------------------------//
    
    
    public void inicializarPanel() {
        //Definir el layout del panel.
        this.setLayout(new BorderLayout());
        
        //------------COMPONENTES DEL PANEL------------
        
        //TABLA
        
        //Columnas de la tabla.
        String[] columnas = {"ID",
                             "Marca",
                             "Sexo",
                             "Peso",
                             "Finca",
                             "Estado Salud",
                             "Calidad Reproductiva",
                             "Es Padrote",
                             "Fecha Inicio Padrote",
                             "Fecha Fin Padrote"
                            };
        
        //Moelo de la tabla.
        modeloTabla = new DefaultTableModel(columnas, 0);
        
        //Añadir modelo a la tabla.
        tabla = new JTable(modeloTabla);
        
        
        //------------AÑADIR COMPONENTES AL PANEL------------
        
        //Añadir tabla.
        this.add(new JScrollPane(tabla), BorderLayout.CENTER);
        
    }
    
    
    public void cargarTabla() {
        //Limpiar modelo te tabla.
        modeloTabla.setRowCount(0);
        
        //Extraer los machos y agregarlos al modelo de tabla.
        for (Macho m : machoServicio.mostrarTodo()) {
            String fechaFinPadrote = m.getFechaFinPadrote();
            if (m.getFechaFinPadrote().isEmpty() && m.isEsPadrote()) {
                fechaFinPadrote = "Actualidad";
            }
            
            modeloTabla.addRow(new Object[] {
                m.getIdGanado(),
                m.getNumeroMarca(),
                m.getTipoSexo(),
                String.format("%,.2f", m.getPeso()),
                m.getIdFinca(),
                m.getEstadoSalud(),
                m.getCalidadReproductiva(),
                m.isEsPadrote() ? "Si" : "No",
                m.getFechaInicioPadrote(),
                fechaFinPadrote
            });
        }
        
    }
    
    
    //Métodos CRUD
    
    public void nuevo() {
        //Extraer los enums para el menú desplegable.
        EnumCalidadReproductiva[] opcionesCalidadRepr = EnumCalidadReproductiva.values();
        EnumEstadoSalud[] opcionesEstadoSalud = EnumEstadoSalud.values();
        
        //Hashmaps para validar la entrada de datos.
        HashMap<Integer, Ganado> mapaGanadoId = new HashMap<>();
        HashMap<String, Ganado> mapaGanadoMarca = new HashMap<>();
        
        //Rellenar el hashmap para validar entrada de datos del ID.
        for (Ganado g : ganadoServicio.mostrarTodo()) {
            mapaGanadoId.put(g.getIdGanado(), g);
        }
        
        //Otro Hashmap para validar la entrada del número de marca.
        HashMap<String, Ganado> mapaNumeroMarca = new HashMap<>();
        for (Ganado g : ganadoServicio.mostrarTodo()) {
            mapaNumeroMarca.put(g.getNumeroMarca(), g);
        }
        
        
        //---------ENTRADA DE DATOS----------//
        
        //ID
        String idGanado = JOptionPane.showInputDialog("Ingrese el ID del ganado:");
        if (idGanado == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        if (idGanado.trim().isEmpty()) { JOptionPane.showMessageDialog(null, "El ID no puede estar vacio."); return; }
        
        try {
            int prueba = Integer.parseInt(idGanado);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El valor debe ser numérico.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (mapaGanadoId.get(Integer.parseInt(idGanado)) != null) {JOptionPane.showMessageDialog(null, "El ID ingresado ya existe.", "Advertencia", JOptionPane.WARNING_MESSAGE); return;}; //Validar que el usuario no ingrese datos duplicados.
        
        //Número de marca
        String numeroMarca = JOptionPane.showInputDialog("Ingrese el numero de marca:");
        if (numeroMarca == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        if (numeroMarca.trim().isEmpty()) { JOptionPane.showMessageDialog(null, "La marca no puede estar vacia."); return; }
        if(mapaNumeroMarca.get(numeroMarca) != null) { JOptionPane.showMessageDialog(null, "La marca ingresada ya existe.", "Advertencia", JOptionPane.WARNING_MESSAGE); return;}//Validar que el número de marca no sea duplicado.
        
        //Peso
        String peso = JOptionPane.showInputDialog("Ingrese el peso:");
        if (peso == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        if (peso.trim().isEmpty()) { JOptionPane.showMessageDialog(null, "El peso no puede estar vacio."); return; }
        
        //Sexo (hardcodeado)
        EnumSexo sexo = EnumSexo.M;
        
        //Finca
        String idFinca = JOptionPane.showInputDialog("Ingrese el ID de la finca:");
        if (idFinca == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        if (idFinca.trim().isEmpty()) { JOptionPane.showMessageDialog(null, "El ID de la finca no puede estar vacio."); return; }
        
        //Estado Salud
        EnumEstadoSalud estadoSalud = (EnumEstadoSalud) JOptionPane.showInputDialog(null,
                "Seleccione el estado de salud:", "Estado Salud",
                JOptionPane.QUESTION_MESSAGE, null, opcionesEstadoSalud, opcionesEstadoSalud[0]);
        if (estadoSalud == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        
        //Calidad Reproductiva
        EnumCalidadReproductiva calidadReproductiva = (EnumCalidadReproductiva) JOptionPane.showInputDialog(null,
                "Seleccione la calidad reproductiva:", "Calidad Reproductiva",
                JOptionPane.QUESTION_MESSAGE, null, opcionesCalidadRepr, opcionesCalidadRepr[0]);
        if (calidadReproductiva == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        
        //Es Padrote
        int esPadrote = JOptionPane.showConfirmDialog(null, "El macho es padrote?", "Padrote", JOptionPane.YES_NO_OPTION);
        if (esPadrote == JOptionPane.CLOSED_OPTION) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        
        //Fecha Inicio Padrote
        String fechaInicioPadrote = JOptionPane.showInputDialog("Ingrese la fecha de inicio como padrote (YYYY-MM-DD) o Enter si no aplica:");
        if (fechaInicioPadrote == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        
        //Fecha Fin Padrote
        String fechaFinPadrote = JOptionPane.showInputDialog("Ingrese la fecha de fin como padrote (YYYY-MM-DD) o Enter si no aplica:");
        if (fechaFinPadrote == null) { JOptionPane.showMessageDialog(null, "Operacion cancelada."); return; }
        
        //Insertar.
        Macho nuevoMacho = new Macho(calidadReproductiva, esPadrote == JOptionPane.YES_OPTION,
                fechaInicioPadrote, fechaFinPadrote,
                Integer.parseInt(idGanado), numeroMarca,
                sexo, Double.parseDouble(peso),
                estadoSalud, Integer.parseInt(idFinca));
        
        machoServicio.insertar(nuevoMacho);
        
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
        
        //Obtener al toro que s va a editar.
        Macho machoEditar = machoServicio.buscarPorId((int) tabla.getValueAt(fila, 0));
        
        //opciones
        String[] opciones = {
            "Marca",
            "Peso",
            "Finca",
            "Estado Salud",
            "Calidad Reproductiva",
            "Es Padrote",
            "Fecha Nacimiento",
            "Fecha Inicio Padrote",
            "Fecha Fin Padrote"
        };
        
        String campo = (String) JOptionPane.showInputDialog(null,
                                                            "Seleccione el campo a editar:", 
                                                            "Editar Hembra",
                                                            JOptionPane.QUESTION_MESSAGE, 
                                                            null, 
                                                            opciones, 
                                                            opciones[0]);
        if (campo == null) return;

        switch (campo) {
            case "Marca":
                String mMarca = JOptionPane.showInputDialog("Marca:", machoEditar.getNumeroMarca());
                if (mMarca != null && !mMarca.trim().isEmpty()) machoEditar.setNumeroMarca(mMarca.trim());
                break;

            case "Peso":
                String mPeso = JOptionPane.showInputDialog("Peso:", machoEditar.getPeso());
                if (mPeso != null && !mPeso.trim().isEmpty()) machoEditar.setPeso(Double.parseDouble(mPeso.trim()));
                break;

            case "Finca":
                String mFinca = JOptionPane.showInputDialog("ID de finca:", machoEditar.getIdFinca());
                if (mFinca != null && !mFinca.trim().isEmpty()) machoEditar.setIdFinca(Integer.parseInt(mFinca.trim()));
                break;

            case "Estado Salud":
                EnumEstadoSalud[] saludValores = EnumEstadoSalud.values();
                EnumEstadoSalud saludSel = (EnumEstadoSalud) JOptionPane.showInputDialog(null,
                        "Estado salud:", "Editar", JOptionPane.QUESTION_MESSAGE,
                        null, saludValores, machoEditar.getEstadoSalud());
                if (saludSel != null) machoEditar.setEstadoSalud(saludSel);
                break;

            case "Calidad Reproductiva":
                EnumCalidadReproductiva[] crValores = EnumCalidadReproductiva.values();
                EnumCalidadReproductiva crSel = (EnumCalidadReproductiva) JOptionPane.showInputDialog(null,
                        "Calidad reproductiva:", "Editar", JOptionPane.QUESTION_MESSAGE,
                        null, crValores, machoEditar.getCalidadReproductiva());
                if (crSel != null) machoEditar.setCalidadReproductiva(crSel);
                break;

            case "Es Padrote":
                int r = JOptionPane.showConfirmDialog(null, "Es padrote?",
                        "Padrote", JOptionPane.YES_NO_OPTION);
                if (r != JOptionPane.CLOSED_OPTION) machoEditar.setEsPadrote(r == JOptionPane.YES_OPTION);
                break;

            case "Fecha Nacimiento":
                String mFNac = JOptionPane.showInputDialog("Fecha nacimiento (YYYY-MM-DD):", machoEditar.getFechaNacimiento());
                if (mFNac != null) machoEditar.setFechaNacimiento(mFNac.trim());
                break;

            case "Fecha Inicio Padrote":
                String mFIP = JOptionPane.showInputDialog("Fecha inicio padrote (YYYY-MM-DD):", machoEditar.getFechaInicioPadrote());
                if (mFIP != null) machoEditar.setFechaInicioPadrote(mFIP.trim());
                break;

            case "Fecha Fin Padrote":
                String mFFP = JOptionPane.showInputDialog("Fecha fin padrote (YYYY-MM-DD):", machoEditar.getFechaFinPadrote());
                if (mFFP != null) machoEditar.setFechaFinPadrote(mFFP.trim());
                break;
        }

        machoServicio.actualizarDetalle(machoEditar);
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
        int idToro = (int) tabla.getValueAt(fila, 0);

        //Eliminar la vaca usando el servicio.
        int seleccion = JOptionPane.showConfirmDialog(null,
                "¿Seguro que desea eliminar el registro?", //Mensaje
                "Eliminar", //Título de la ventana
                JOptionPane.YES_NO_OPTION //Botones de "Si", "No".
        );
        if (seleccion == JOptionPane.YES_OPTION) {
            //Valor 0 para la opción "Si"
            machoServicio.eliminar(idToro);
            JOptionPane.showMessageDialog(null, "Registro eliminado correctamente.");

            //Recargar tabla con datos actualizados.
            cargarTabla();

        }
    }
}
