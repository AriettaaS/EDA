

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Clases.Listas.LinkedList;
import Clases.Listas.Node;

public class ProductosController implements ActionListener {

    private GUIProductos viewProductos;
    private FuncionalidadProducto funcionalidadProducto;

    public ProductosController(int codigo_almacen, AVLTree<Integer, Producto> productos) {
        this.viewProductos = new GUIProductos(codigo_almacen);

        this.funcionalidadProducto = new FuncionalidadProducto(productos);

        this.showProductos();
        this.initActions();

        this.viewProductos.setResizable(false);
        this.viewProductos.setVisible(true);
    }

    private void initActions() {
        this.viewProductos.registrarProducto.addActionListener(this);
        this.viewProductos.registrarProductoArchivo.addActionListener(this);
        this.viewProductos.busquedaCodigo.addActionListener(this);
        this.viewProductos.productoDeBaja.addActionListener(this);
        this.viewProductos.volver.addActionListener(this);
    }

    private void showProductos() {
        LinkedList<Producto> lista = this.funcionalidadProducto.getAllProductos();
        Node<Producto> aux = lista.getHead();

        DefaultTableModel table = this.viewProductos.modelTable;
        while (table.getRowCount() > 0) 
            table.removeRow(table.getRowCount()-1);
        
        while (aux != null) {
            String[] row = {Integer.toString(aux.getData().getCodigo()), aux.getData().getDescripcion(), Integer.toString(aux.getData().getStock())};
            table.addRow(row);
            aux = aux.getNext();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.viewProductos.registrarProducto) {
            try {
                int codigo = Integer.parseInt(this.viewProductos.codigoField.getText());
                String descripcion = this.viewProductos.descripcionField.getText();
                int stock = Integer.parseInt(this.viewProductos.stockField.getText());

                Producto prod = new Producto(codigo, descripcion, stock);

                if (this.funcionalidadProducto.addProducto(prod)) {
                    String[] data = {Integer.toString(codigo), descripcion, Integer.toString(stock)};
                    this.viewProductos.modelTable.addRow(data);
                } else {
                    JOptionPane.showMessageDialog(null, "Producto con clave " + codigo + " ya se encuentra registrado", "Error de insercion", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException err) {
                JOptionPane.showMessageDialog(null, "Fomato de codigo o stock invalidos", "Error de formato", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == this.viewProductos.registrarProductoArchivo) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();
                if (this.funcionalidadProducto.addDesdeArchivo(filePath)) {
                    this.showProductos();
                    System.out.println();
                } else { 
                    JOptionPane.showMessageDialog(null, "Archivo no encontrado", "File Error", JOptionPane.ERROR_MESSAGE);
                }
                    
            }
        } else if (e.getSource() == this.viewProductos.busquedaCodigo) {
            String codigoStr = JOptionPane.showInputDialog(null, "Ingrese un codigo:", "Busqueda por codigo", JOptionPane.QUESTION_MESSAGE);

            if (codigoStr != null) {
                int codigo = Integer.parseInt(codigoStr);
                Producto producto = this.funcionalidadProducto.buscarProducto(codigo);
                this.viewProductos.showProducto(producto);
            }

        } else if (e.getSource() == this.viewProductos.productoDeBaja) {
            String codigoStr = JOptionPane.showInputDialog(null, "Ingrese un codigo:", "Eliminacion de almacen", JOptionPane.QUESTION_MESSAGE);
            if (codigoStr != null) {
                int codigo = Integer.parseInt(codigoStr);
                if (this.funcionalidadProducto.removeProducto(codigo)) {
                    this.showProductos();
                    JOptionPane.showMessageDialog(null, "Producto eliminado", "Info eliminacion", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Producto no encontrado", "Error de eliminacion", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (e.getSource() == this.viewProductos.volver) {
            this.viewProductos.setVisible(false);
        }
    }
    
}
