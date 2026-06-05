package co.upc.ganado.presentacion;

import co.upc.ganado.servicios.GanadoService;
import co.upc.ganado.entidades.Ganado;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;

/**
 * Panel de visualizacion de todo el ganado. Muestra una tabla con
 * todos los animales registrados (machos y hembras) y proporciona
 * metodos para las operaciones CRUD basicas.
 * <p>
 * Este panel se inserta dentro de un JTabbedPane en el MainFrame y
 * utiliza un GanadoService para la logica de negocio y persistencia.
 *
 * @author Santiago Rafael Zuleta Neira
 */
public class GanadoPanel extends JPanel {

    private GanadoService ganadoServicio;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    /**
     * Crea un nuevo panel de ganado general.
     *
     * @param gs Servicio de ganado que provee los datos y la logica
     *           de negocio
     */
    public GanadoPanel(GanadoService gs) {
        this.ganadoServicio = gs;
        inicializarPanel();
        cargarTabla();
    }

    /**
     * Inicializa los componentes graficos del panel.
     */
    private void inicializarPanel() {
        this.setLayout(new BorderLayout());

        String[] columnas = {"ID", "Marca", "Sexo", "Peso",
                             "Estado Salud", "Estado Vida", "Finca",
                             "Fecha Nacimiento"};

        modeloTabla = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modeloTabla);

        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    /**
     * Recarga los datos de la tabla desde el servicio.
     */
    public void cargarTabla() {
        //Limpiar modelo de tabla.
        modeloTabla.setRowCount(0);
        
        //Rellenar modelo de tabla.
        for (Ganado g : ganadoServicio.mostrarTodo()) {
            modeloTabla.addRow(new Object[] {
                g.getIdGanado(),
                g.getNumeroMarca(),
                g.getTipoSexo(),
                String.format("%,.2f", g.getPeso()),
                g.getEstadoSalud(),
                g.getEstadoVida(),
                g.getIdFinca(),
                g.getFechaNacimiento()
            });
        }
        
    }

    
    //Código comentado puesto que no es necesario para el panel.
    
    /*
    
    
    public void nuevo() {
    }

    
    public void editar() {
    }

    
    public void eliminar() {
    }
    */
    
}
