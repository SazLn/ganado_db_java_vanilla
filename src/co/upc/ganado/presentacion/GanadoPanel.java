package co.upc.ganado.presentacion;

import co.upc.ganado.servicios.GanadoService;

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
        setLayout(new BorderLayout());

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
    }

    
    //Primero que nada, creo que es redundante que este panel tenga operaciones crud dado que dichas operaciones pueden ser hechas por
    //macho y hembra respectivamente.
    
    /**
     * Abre un dialogo para crear un nuevo registro de ganado.
     */
    public void nuevo() {
    }

    /**
     * Abre un dialogo para editar el registro seleccionado.
     */
    public void editar() {
    }

    /**
     * Elimina el registro seleccionado tras confirmacion.
     */
    public void eliminar() {
    }
}
