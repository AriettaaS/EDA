public class Almacen {

    private int codigo;
    private String nombre;
    private String direccion;
    private AVLTree<Integer, Producto> productos;

    public Almacen(int codigo, String nombre, String direccion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.direccion = direccion;
        this.productos = new AVLTree<>();
    }

    // Getters y Setters
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setProductos(AVLTree<Integer, Producto> productos) {
        this.productos = productos;
    }

    public AVLTree<Integer, Producto> getProductos() {
        return this.productos;
    }

    @Override
    public boolean equals(Object o) {
        Almacen x = (Almacen) o;
        return this.codigo == x.getCodigo();
    }

    @Override
    public String toString() {
        return "Código: " + codigo + ", Nombre: " + nombre + ", Dirección: " + direccion;
    }
}
