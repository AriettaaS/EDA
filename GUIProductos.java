

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

public class GUIProductos extends JFrame {

    public JTextField codigoField;
    public JTextField descripcionField;
    public JTextField stockField;

    public JButton registrarProducto;
    public JButton registrarProductoArchivo;
    public JButton busquedaCodigo;
    public JButton productoDeBaja;
    public JButton volver;

    public JPanel showProducto;

    public DefaultTableModel modelTable;
    public JTable table;

    public GUIProductos(int codigo_almacen) {

        this.setTitle("Productos de almacen " + codigo_almacen);

        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.initComponents();

    }

    private void initComponents() {
        this.setLayout(null);

        this.codigoField = new JTextField("", 15);
        this.descripcionField = new JTextField("", 15);
        this.stockField = new JTextField("", 15);

        JPanel fieldsComponent = new JPanel(); // Para insercion de datos
        JPanel showComponent = new JPanel(); // Para vista de productos

        fieldsComponent.setBorder(new TitledBorder("Control de producto"));
        showComponent.setBorder(new TitledBorder("Productos"));

        fieldsComponent.setBounds(5, 5, 480, 230);
        showComponent.setBounds(5, 235, 480, 220);
        
        this.initFieldsComponent(fieldsComponent);
        this.initTableComponent(showComponent);

        this.add(fieldsComponent);
        this.add(showComponent);
    }

    /**
     * Inicializa los componentes para la insercion de producto
     * @param fieldsComponent
     */
    private void initFieldsComponent(JPanel fieldsComponent) {

        fieldsComponent.setLayout(null);

        JPanel camposPanel = new JPanel();
        camposPanel.setLayout(null);

        JPanel codigoFieldPanel = new JPanel();
        codigoFieldPanel.add(new JLabel("Codigo de producto"));
        codigoFieldPanel.add(this.codigoField);
        codigoFieldPanel.setLayout(new GridLayout(1, 2, 10, 0));
        codigoFieldPanel.setBounds(10, 18, 270, 20);

        JPanel descripcionFieldPanel = new JPanel();
        descripcionFieldPanel.add(new JLabel("Descripcion"));
        descripcionFieldPanel.add(this.descripcionField);
        descripcionFieldPanel.setLayout(new GridLayout(1, 2, 10, 0));
        descripcionFieldPanel.setBounds(10, 53, 270, 20);

        JPanel stockFieldPanel = new JPanel();
        stockFieldPanel.add(new JLabel("Stock de producto"));
        stockFieldPanel.add(this.stockField);
        stockFieldPanel.setLayout(new GridLayout(1, 2, 10, 0));
        stockFieldPanel.setBounds(10, 88, 270, 20);
        
        camposPanel.add(codigoFieldPanel);
        camposPanel.add(descripcionFieldPanel);
        camposPanel.add(stockFieldPanel);
        
        JPanel accionesPanel = new JPanel();
        accionesPanel.setLayout(null);
        accionesPanel.setBorder(new TitledBorder("Acciones"));
        
        registrarProducto = new JButton("Agregar producto");
        registrarProductoArchivo = new JButton("Cargar archivo");
        busquedaCodigo = new JButton("Buscar por codigo");
        productoDeBaja = new JButton("Dar de baja");
        volver = new JButton("Volver");

        accionesPanel.add(registrarProducto);
        accionesPanel.add(registrarProductoArchivo);
        accionesPanel.add(busquedaCodigo);
        accionesPanel.add(productoDeBaja);
        accionesPanel.add(volver);

        registrarProducto.setBounds(5, 25, 160, 25);
        registrarProductoArchivo.setBounds(5, 60, 160, 25);
        busquedaCodigo.setBounds(5, 95, 160, 25);
        productoDeBaja.setBounds(5, 130, 160, 25);
        volver.setBounds(5, 165, 160, 25);

        camposPanel.setBounds(5, 18, 290, 110);
        accionesPanel.setBounds(300, 20, 170, 200);

        showProducto = new JPanel();
        showProducto.setLayout(null);
        showProducto.setBorder(new TitledBorder("Producto seleccionado"));
        showProducto.setBounds(10, 138, 280, 83);

        fieldsComponent.add(camposPanel);
        fieldsComponent.add(accionesPanel);
        fieldsComponent.add(showProducto);
    }

    /**
     * Inicializa los componentes para la tabla de productos
     * @param showComponent
     */
    private void initTableComponent(JPanel showComponent) {

        
    	modelTable = new DefaultTableModel();
        modelTable.addColumn("Codigo");
        modelTable.addColumn("Descripcion");
        modelTable.addColumn("Stock");
        table = new JTable(modelTable); // Inicializa la tabla "table"

        JScrollPane tablePanel = new JScrollPane(table);
        tablePanel.setPreferredSize(new Dimension(460, 190));
        tablePanel.setBounds(10, 10, 460, 190);

        showComponent.add(tablePanel);
    }

    public void showProducto(Producto producto) {
        this.showProducto.removeAll();
        this.showProducto.revalidate();
        this.showProducto.repaint();
        if(producto != null) {
            JLabel label1 = new JLabel("Codigo de producto");
            JLabel label2 = new JLabel("Descripcion");
            JLabel label3 = new JLabel("Stock de producto");

            JLabel input1 = new JLabel(Integer.toString(producto.getCodigo()));
            JLabel input2 = new JLabel(producto.getDescripcion());
            JLabel input3 = new JLabel(Integer.toString(producto.getStock()));

            this.showProducto.add(label1);
            this.showProducto.add(label2);
            this.showProducto.add(label3);
            this.showProducto.add(input1);
            this.showProducto.add(input2);
            this.showProducto.add(input3);

            label1.setBounds(10, 20, 150, 10);
            label2.setBounds(10, 40, 150, 10);
            label3.setBounds(10, 60, 150, 10);

            input1.setBounds(170, 20, 120, 10);
            input2.setBounds(170, 40, 120, 10);
            input3.setBounds(170, 60, 120, 10);
        } else {
            JLabel errorLabel = new JLabel("Producto no encontrado");
            this.showProducto.add(errorLabel);
            errorLabel.setBounds(80, 40, 150, 10);
        }
    }

}
