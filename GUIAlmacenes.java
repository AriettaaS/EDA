

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

public class GUIAlmacenes extends JFrame{

    public JTextField codigoField;
    public JTextField nombreField;
    public JTextField direccionField;

    public JButton registrarAlmacen;
    public JButton registrarAlmacenArchivo;
    public JButton busquedaCodigo;
    public JButton busquedaProducto;
    
    
    public JButton almacenDeBaja;
    public JButton cargarVias;


    public JButton verProductos;
    public JButton cancelar;

    private JPanel showAlmacen;
    private JPanel accionesPanel;
    private JPanel accionesSecPanel;
    private JPanel accionesSelectPanel;

    public DefaultTableModel modelTable;
    public JTable table;

    public DefaultTableModel modelTableRoutes;
    public JTable tableRoutes;

    public GUIAlmacenes() {

        this.setTitle("Almacenes DOTA S.A.C");

        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.initComponents();

    }

    private void initComponents() {
        this.setLayout(null);

        this.codigoField = new JTextField("", 15);
        this.nombreField = new JTextField("", 15);
        this.direccionField = new JTextField("", 15);

        JPanel fieldsComponent = new JPanel(); // Para insercion de datos
        JPanel showComponent = new JPanel(); // Para vista de almacenes
        JPanel showRoutes = new JPanel(); // Para vista de rutas de distribucion

        fieldsComponent.setBorder(new TitledBorder("Control de almacen"));
        showComponent.setBorder(new TitledBorder("Almacenes"));
        showRoutes.setBorder(new TitledBorder("Rutas de distribucion"));

        this.initFieldsComponent(fieldsComponent);
        this.initTableComponent(showComponent);
        this.initRoutesComponent(showRoutes);
        
        fieldsComponent.setBounds(5, 5, 480, 230);
        showComponent.setBounds(5, 235, 320, 220);
        showRoutes.setBounds(330, 235, 150, 220);

        this.add(fieldsComponent);
        this.add(showComponent);
        this.add(showRoutes);
    }

    /**
     * Inicializa los componentes para la insercion de datos
     * @param fieldsComponent
     */
    private void initFieldsComponent(JPanel fieldsComponent) {

        fieldsComponent.setLayout(null);

        JPanel camposPanel = new JPanel();
        camposPanel.setLayout(null);

        JPanel codigoFieldPanel = new JPanel();
        codigoFieldPanel.add(new JLabel("Codigo de almacen"));
        codigoFieldPanel.add(this.codigoField);
        codigoFieldPanel.setLayout(new GridLayout(1, 2, 10, 0));
        codigoFieldPanel.setBounds(10, 18, 270, 20);

        JPanel nombreFieldPanel = new JPanel();
        nombreFieldPanel.add(new JLabel("Nombre de almacen"));
        nombreFieldPanel.add(this.nombreField);
        nombreFieldPanel.setLayout(new GridLayout(1, 2, 10, 0));
        nombreFieldPanel.setBounds(10, 53, 270, 20);

        JPanel direccionFieldPanel = new JPanel();
        direccionFieldPanel.add(new JLabel("Direccion de almacen"));
        direccionFieldPanel.add(this.direccionField);
        direccionFieldPanel.setLayout(new GridLayout(1, 2, 10, 0));
        direccionFieldPanel.setBounds(10, 88, 270, 20);
        
        camposPanel.add(codigoFieldPanel);
        camposPanel.add(nombreFieldPanel);
        camposPanel.add(direccionFieldPanel);
        
        accionesSelectPanel = new JPanel();
        accionesSelectPanel.setLayout(null);
        accionesSelectPanel.setBorder(new TitledBorder("Acciones Almacen"));

        verProductos = new JButton("Ver productos");
        cancelar = new JButton("Cancelar");

        accionesSelectPanel.add(verProductos);
        accionesSelectPanel.add(cancelar);

        verProductos.setBounds(5, 25, 160, 25);
        cancelar.setBounds(5, 60, 160, 25);

        accionesSelectPanel.setBounds(300, 20, 170, 200);
        accionesSelectPanel.setVisible(false);

        accionesPanel = new JPanel();
        accionesPanel.setLayout(null);
        accionesPanel.setBorder(new TitledBorder("Acciones"));
        
        registrarAlmacen = new JButton("Agregar almacen");
        registrarAlmacenArchivo = new JButton("Cargar archivo");
        busquedaCodigo = new JButton("Buscar por codigo");
        busquedaProducto = new JButton("Buscar por producto");
        
        accionesSecPanel = new JPanel();
        accionesSecPanel.setLayout(null);
        accionesSecPanel.setBorder(new TitledBorder("Acciones"));
        
        almacenDeBaja = new JButton("Dar de baja");
        cargarVias = new JButton("Cargar vias");
        
        JButton nextAcciones = new JButton(">>");
        JButton prevAcciones = new JButton("<<");
        nextAcciones.addActionListener((ActionEvent l) -> {
            this.accionesPanel.setVisible(false);
            this.accionesSecPanel.setVisible(true);
            nextAcciones.setEnabled(false);
            prevAcciones.setEnabled(true);
        });
        prevAcciones.addActionListener((ActionEvent l) -> {
            this.accionesPanel.setVisible(true);
            this.accionesSecPanel.setVisible(false);
            nextAcciones.setEnabled(true);
            prevAcciones.setEnabled(false);
        });

        accionesPanel.add(registrarAlmacen);
        accionesPanel.add(registrarAlmacenArchivo);
        accionesPanel.add(busquedaCodigo);
        accionesPanel.add(busquedaProducto);
        accionesPanel.add(nextAcciones);
        
        accionesSecPanel.add(almacenDeBaja);
        accionesSecPanel.add(cargarVias);
        accionesSecPanel.add(prevAcciones);

        registrarAlmacen.setBounds(5, 25, 160, 25);
        registrarAlmacenArchivo.setBounds(5, 60, 160, 25);
        busquedaCodigo.setBounds(5, 95, 160, 25);
        busquedaProducto.setBounds(5, 130, 160, 25);
        nextAcciones.setBounds(5, 165, 160, 25);

        almacenDeBaja.setBounds(5, 25, 160, 25);
        cargarVias.setBounds(5, 60, 160, 25);
        prevAcciones.setBounds(5, 95, 160, 25);

        camposPanel.setBounds(5, 18, 290, 110);
        accionesPanel.setBounds(300, 20, 170, 200);
        accionesSecPanel.setBounds(300, 20, 170, 200);
        accionesSelectPanel.setBounds(300, 20, 170, 200);

        showAlmacen = new JPanel();
        showAlmacen.setLayout(null);
        showAlmacen.setBorder(new TitledBorder("Almacen seleccionado"));
        showAlmacen.setBounds(10, 138, 280, 83);

        fieldsComponent.add(camposPanel);
        fieldsComponent.add(accionesPanel);
        fieldsComponent.add(accionesSecPanel);
        fieldsComponent.add(accionesSelectPanel);
        fieldsComponent.add(showAlmacen);
    }

    /**
     * Inicializa los componentes para la tabla de almacenes
     * @param showComponent
     */
    private void initTableComponent(JPanel showComponent) {
        modelTable = new DefaultTableModel();
        modelTable.addColumn("Codigo");
        modelTable.addColumn("Nombre");
        modelTable.addColumn("Direccion");
        table = new JTable(modelTable);
        
        JScrollPane tablePanel = new JScrollPane(table);
        tablePanel.setPreferredSize(new Dimension(300, 190));
        tablePanel.setBounds(10, 10, 300, 190);

        showComponent.add(tablePanel);
    }

    /**
     * Inicializa los componentes para la tabla de rutas
     * @param showRoutes
     */
    private void initRoutesComponent(JPanel showRoutes) {
        modelTableRoutes = new DefaultTableModel();
        modelTableRoutes.addColumn("Secuencia");
        modelTableRoutes.addColumn("Almacen");
        tableRoutes = new JTable(modelTableRoutes);
        
        JScrollPane tablePanel = new JScrollPane(tableRoutes);
        tablePanel.setPreferredSize(new Dimension(130, 190));
        tablePanel.setBounds(10, 10, 130, 190);

        showRoutes.add(tablePanel);
    }

    /**
     * Inicializa la vista de los atributos del almacen buscado
     * @param almacen
     */
    public void showAlmacen(Almacen almacen) {
        this.showAlmacen.removeAll();
        this.showAlmacen.revalidate();
        this.showAlmacen.repaint();
        if(almacen != null) {
            JLabel label1 = new JLabel("Codigo de almacen");
            JLabel label2 = new JLabel("Nombre de almacen");
            JLabel label3 = new JLabel("Direccion de almacen");

            JLabel input1 = new JLabel(Integer.toString(almacen.getCodigo()));
            JLabel input2 = new JLabel(almacen.getNombre());
            JLabel input3 = new JLabel(almacen.getDireccion());

            this.showAlmacen.add(label1);
            this.showAlmacen.add(label2);
            this.showAlmacen.add(label3);
            this.showAlmacen.add(input1);
            this.showAlmacen.add(input2);
            this.showAlmacen.add(input3);

            label1.setBounds(10, 20, 150, 10);
            label2.setBounds(10, 40, 150, 10);
            label3.setBounds(10, 60, 150, 10);

            input1.setBounds(170, 20, 120, 10);
            input2.setBounds(170, 40, 120, 10);
            input3.setBounds(170, 60, 120, 10);
        } else {
            JLabel errorLabel = new JLabel("Almacen no encontrado");
            this.showAlmacen.add(errorLabel);
            errorLabel.setBounds(80, 40, 150, 10);
        }
    }

    public void initSelectMenu() {
        this.accionesPanel.setVisible(false);
        this.accionesSelectPanel.setVisible(true);
    }

    public void hiddenSelectMenu() {
        this.accionesPanel.setVisible(true);
        this.accionesSelectPanel.setVisible(false);
    }
        
}