

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class MainController implements ListSelectionListener, ActionListener {
    
    private GUIAlmacenes viewAlmacen;
    private FuncionalidadAlmacen funcionalidadAlmacen;
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.viewAlmacen.registrarAlmacen) {
            try {
                int codigo = Integer.parseInt(this.viewAlmacen.codigoField.getText());
                String nombre = this.viewAlmacen.nombreField.getText();
                String direccion = this.viewAlmacen.direccionField.getText();
                if (nombre.equals("") || direccion.equals("")) {
                    throw new Exception("Nombre y direccion no deben estar vacios", null);
                }
                if (!this.funcionalidadAlmacen.agregarAlmacen(codigo, nombre, direccion)) {
                    JOptionPane.showMessageDialog(null, "Almacen con codigo " + codigo + " ya fue insertado",
                            "Error de insercion", JOptionPane.ERROR_MESSAGE);
                } else {
                    String[] almacen = { Integer.toString(codigo), nombre, direccion };
                    this.viewAlmacen.modelTable.addRow(almacen);
                    this.resetFields();
                }
            } catch (NumberFormatException err) {
                JOptionPane.showMessageDialog(null, "Codigo no valido", "Error de formato", JOptionPane.ERROR_MESSAGE);
            } catch (Exception err) {
                JOptionPane.showMessageDialog(null, err.getMessage(), "Error de formato", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == this.viewAlmacen.registrarAlmacenArchivo) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
    
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();
                try {
                    this.funcionalidadAlmacen.agregarAlmacenDesdeArchivo(filePath);
                    this.showAlmacenes();
                } catch (FileNotFoundException error) {
                    JOptionPane.showMessageDialog(null, "Archivo no encontrado", "File Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (e.getSource() == this.viewAlmacen.busquedaCodigo) {
            String codigoStr = JOptionPane.showInputDialog(null, "Ingrese un codigo:", "Busqueda por codigo",
                    JOptionPane.INFORMATION_MESSAGE);
            if (codigoStr != null) {
                int codigo = Integer.parseInt(codigoStr);
                Almacen almacen = this.funcionalidadAlmacen.buscarAlmacen(codigo);
    
                this.viewAlmacen.showAlmacen(almacen);
            }
        } else if (e.getSource() == this.viewAlmacen.busquedaProducto){
    
        } else if (e.getSource() == this.viewAlmacen.almacenDeBaja) {
            String codigoStr = JOptionPane.showInputDialog(null, "Ingrese un codigo:", "Eliminacion de almacen",
                    JOptionPane.QUESTION_MESSAGE);
            if (codigoStr != null) {
                int codigo = Integer.parseInt(codigoStr);
                if (this.funcionalidadAlmacen.darDeBaja(codigo)) {
                    this.showAlmacenes();
                    JOptionPane.showMessageDialog(null, "Almacen eliminado", "Info eliminacion",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Almacen no encontrado", "Info eliminacion",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } else if (e.getSource() == this.viewAlmacen.verProductos) {
            JTable table = this.viewAlmacen.table;
            int viewRow = table.getSelectedRow();
            if (viewRow != -1) {
                String codigoStr = table.getValueAt(table.getSelectedRow(), 0).toString();
                int codigo = Integer.parseInt(codigoStr);
                Almacen almacen = this.funcionalidadAlmacen.buscarAlmacen(codigo);
                new ProductosController(almacen.getCodigo(), almacen.getProductos());
            }
        } else if (e.getSource() == this.viewAlmacen.cargarVias) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
    
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();
                ResultDistr result = this.funcionalidadAlmacen.loadRoutes(filePath);
                if(result != null) {
                    DefaultTableModel aux = this.viewAlmacen.modelTableRoutes;
                    while (aux.getRowCount() > 0) 
                        aux.removeRow(aux.getRowCount() - 1);
                    int index = 1;
                    for(int i = result.getResult().length - 1; i >= 0; i--) {
                        String[] data = {Integer.toString(index), Integer.toString(result.getResult()[i].getCodigo(), i)};
                        index++;
                        aux.addRow(data);
                    }
    
                }
            }
        } else if (e.getSource() == this.viewAlmacen.cancelar) {
            this.viewAlmacen.hiddenSelectMenu();
        }
    }
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        JTable table = this.viewAlmacen.table;
        int viewRow = table.getSelectedRow();
        if (!e.getValueIsAdjusting() && viewRow != -1) {
            this.viewAlmacen.initSelectMenu();
        }
    }
    public MainController() {
        this.viewAlmacen = new GUIAlmacenes();

        this.funcionalidadAlmacen = new FuncionalidadAlmacen();

        this.initActions();

        this.viewAlmacen.setResizable(false);
        this.viewAlmacen.setVisible(true);
    }

    private void initActions() {
        this.viewAlmacen.registrarAlmacen.addActionListener(this);
        this.viewAlmacen.registrarAlmacenArchivo.addActionListener(this);
        this.viewAlmacen.almacenDeBaja.addActionListener(this);
        this.viewAlmacen.busquedaCodigo.addActionListener(this);
        this.viewAlmacen.busquedaProducto.addActionListener(this);
        this.viewAlmacen.cargarVias.addActionListener(this);

        this.viewAlmacen.verProductos.addActionListener(this);
        this.viewAlmacen.cancelar.addActionListener(this);

        this.viewAlmacen.table.getSelectionModel().addListSelectionListener(this);
    }

    private void resetFields() {
        this.viewAlmacen.codigoField.setText("");
        this.viewAlmacen.nombreField.setText("");
        this.viewAlmacen.direccionField.setText("");
    }

    private void showAlmacenes() {
        ArrayList<Almacen> almacenes = this.funcionalidadAlmacen.mostrarAlmacenes();
        while (this.viewAlmacen.modelTable.getRowCount() > 0) {
            this.viewAlmacen.modelTable.removeRow(this.viewAlmacen.modelTable.getRowCount() - 1);
        }
        for (Almacen almacen : almacenes) {
            String[] newAlmacen = { Integer.toString(almacen.getCodigo()), almacen.getNombre(),
                    almacen.getDireccion() };
            this.viewAlmacen.modelTable.addRow(newAlmacen);
        }
    }


    public static void main(String[] args) {
        new MainController();
    }

}
