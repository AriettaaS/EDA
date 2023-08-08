
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class MainController2 implements ListSelectionListener, ActionListener {

    private GUIProductos viewProducto;
    private FuncionalidadProducto funcionalidadProducto;

    public MainController2() {
        // Inicializar la vista y la funcionalidad de productos
    	
        AVLTree<Integer, Producto> avlTree = new AVLTree<>();
        this.funcionalidadProducto = new FuncionalidadProducto(avlTree);

        this.viewProducto = new GUIProductos(1);
        
        // Agregar ActionListener para botones
        this.viewProducto.registrarProducto.addActionListener(this);
        this.viewProducto.registrarProductoArchivo.addActionListener(this);
        this.viewProducto.busquedaCodigo.addActionListener(this);
        this.viewProducto.productoDeBaja.addActionListener(this);
        this.viewProducto.volver.addActionListener(this);

        // Agregar ListSelectionListener para la tabla de productos
        this.viewProducto.table.getSelectionModel().addListSelectionListener(this);

        // Mostrar la vista
        this.viewProducto.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.viewProducto.registrarProducto) {
            try {
                int codigo = Integer.parseInt(this.viewProducto.codigoField.getText());
                String descripcion = this.viewProducto.descripcionField.getText();
                int stock = Integer.parseInt(this.viewProducto.stockField.getText());

                Producto producto = new Producto(codigo, descripcion, stock);

                if (!this.funcionalidadProducto.addProducto(producto)) {
                    JOptionPane.showMessageDialog(null, "Producto con código " + codigo + " ya fue insertado",
                            "Error de inserción", JOptionPane.ERROR_MESSAGE);
                } else {
                    Object[] rowData = { codigo, descripcion, stock };
                    this.viewProducto.modelTable.addRow(rowData);
                    this.resetFields();
                }
            } catch (NumberFormatException err) {
                JOptionPane.showMessageDialog(null, "Datos no válidos", "Error de formato", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == this.viewProducto.registrarProductoArchivo) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();
                try {
                    if (this.funcionalidadProducto.addDesdeArchivo(filePath)) {
                        this.showProductos();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al cargar desde el archivo", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NotFoundException error) { // Usa tu clase personalizada NotFoundException
                    JOptionPane.showMessageDialog(null, "Archivo no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (e.getSource() == this.viewProducto.busquedaCodigo) {
            String codigoStr = JOptionPane.showInputDialog(null, "Ingrese un código:", "Búsqueda por código",
                    JOptionPane.INFORMATION_MESSAGE);
            if (codigoStr != null) {
                try {
                    int codigo = Integer.parseInt(codigoStr);
                    Producto producto = this.funcionalidadProducto.buscarProducto(codigo);
                    this.viewProducto.showProducto(producto);
                } catch (NumberFormatException err) {
                    JOptionPane.showMessageDialog(null, "Código no válido", "Error de formato",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (e.getSource() == this.viewProducto.productoDeBaja) {
            String codigoStr = JOptionPane.showInputDialog(null, "Ingrese un código:", "Eliminación de producto",
                    JOptionPane.QUESTION_MESSAGE);
            if (codigoStr != null) {
                int codigo = Integer.parseInt(codigoStr);
                if (this.funcionalidadProducto.removeProducto(codigo)) {
                    this.showProductos();
                    JOptionPane.showMessageDialog(null, "Producto eliminado", "Info eliminación",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Producto no encontrado", "Info eliminación",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        // Agregar más condiciones para otros botones aquí
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getSource() == this.viewProducto.table.getSelectionModel()) {
            int selectedRow = this.viewProducto.table.getSelectedRow();
            if (selectedRow >= 0) {
                int codigo = (int) this.viewProducto.modelTable.getValueAt(selectedRow, 0);
                Producto producto = this.funcionalidadProducto.buscarProducto(codigo);
                this.viewProducto.showProducto(producto);
            }
        }
    }

    private void showProductos() {
        this.viewProducto.modelTable.setRowCount(0);
        // Recuperar la lista de productos de la funcionalidad y agregarlos a la tabla
        // this.viewProducto.modelTable.addRow(...);
    }

    private void resetFields() {
        this.viewProducto.codigoField.setText("");
        this.viewProducto.descripcionField.setText("");
        this.viewProducto.stockField.setText("");
    }

    public static void main(String[] args) {
        new MainController2();
    }
}

