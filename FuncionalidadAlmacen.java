

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class FuncionalidadAlmacen {
    private GraphLink<Almacen> grafoAlmacenes;
    private DistributionRoute funcionRoutes;

    public FuncionalidadAlmacen() {
        this.grafoAlmacenes = new GraphLink<>();
        this.funcionRoutes = new DistributionRoute(this.grafoAlmacenes);
    }

    public boolean agregarAlmacen(int codigoAlmacen, String nombre, String direccion) {
        Almacen almacen = new Almacen(codigoAlmacen, nombre, direccion);
        return grafoAlmacenes.insertVertex(almacen);
    }
  
    public void agregarAlmacenDesdeArchivo(String nombreArchivo) throws FileNotFoundException {

        FileInputStream file = new FileInputStream(nombreArchivo);
        Scanner sc = new Scanner(file);

        String regexPattern = "(\\d+)\\s*,\\s*(.+)\\s*,\\s*(.+)"; 

        Pattern pattern = Pattern.compile(regexPattern);
        
        while(sc.hasNext()) {
            Matcher matcher = pattern.matcher(sc.nextLine());
            while (matcher.find()) {
                String codigo = matcher.group(1);
                String nombre = matcher.group(2);
                String direccion = matcher.group(3);
                if(!this.agregarAlmacen(Integer.parseInt(codigo), nombre, direccion)) {
                    System.out.println("Almacen ya registrado");
                }
            }
        }
        sc.close();
    }

    public Almacen buscarAlmacen(int codigo) {
        Vertex<Almacen> almacenVertex = grafoAlmacenes.searchElement(new Almacen(codigo, "", ""));
        if (almacenVertex != null) {
            return almacenVertex.getData();
        } else {
            return null; // Si no se encuentra el almacén, devuelve null.
        }
    }

    public void buscarProducto() {

    }
    
    public boolean darDeBaja(int codigo) {
        Almacen almacen = this.buscarAlmacen(codigo);
        return this.grafoAlmacenes.removeVertex(almacen);
    }

    public ArrayList<Almacen> mostrarAlmacenes() {
        ArrayList<Almacen> listaAlmacenes = new ArrayList<>();
        ArrayList<Vertex<Almacen>> vertices = grafoAlmacenes.getListVertex();
        for (Vertex<Almacen> vertex : vertices) {
            listaAlmacenes.add(vertex.getData());
        }
        return listaAlmacenes;
    }

    public ResultDistr loadRoutes(String path) {
        if (this.funcionRoutes.loadFile(path)) {
            String codOrigenStr = JOptionPane.showInputDialog(null, "Indique el codigo de almacen origen:", "Rutas minimas", JOptionPane.INFORMATION_MESSAGE);
            if (codOrigenStr != null) {
                String codDestinoStr = JOptionPane.showInputDialog(null, "Indique el codigo de almacen destino:", "Rutas minimas", JOptionPane.INFORMATION_MESSAGE);
                if (codDestinoStr != null) {
                    int codOrigen = Integer.parseInt(codOrigenStr);
                    int codDestino = Integer.parseInt(codDestinoStr);
                    return this.funcionRoutes.verifyRoutes(codOrigen, codDestino);
                }
            }
        }
        return null;
    }
}
