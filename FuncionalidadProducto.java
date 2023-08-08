
import java.io.IOException;

import Clases.Listas.LinkedList;

public class FuncionalidadProducto {

    private AVLTree<Integer, Producto> productos;

    public FuncionalidadProducto(AVLTree<Integer, Producto> productos) {
        this.productos = productos;
    }

    public boolean addProducto(Producto producto) {
        return this.productos.insert(producto.getCodigo(), producto);
    }

    public boolean addDesdeArchivo(String path) {
        try {
            this.productos.addProductsFromFile(path);	
            return true;
        } catch (IOException err) {
            return false; 
        }
    }

    public boolean removeProducto(Integer key) {
        return this.productos.remove(key);
    }

    public Producto buscarProducto(Integer key) {
        return this.productos.search(key);
    }

    public LinkedList<Producto> getAllProductos() {
        return this.productos.getProductsInOrder();
    }

}
