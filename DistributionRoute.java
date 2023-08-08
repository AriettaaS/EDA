

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import java.util.ArrayList;
import java.util.Scanner;

import Clases.Listas.Node;
import Clases.Listas.PriorityQueue;

public class DistributionRoute {

    private GraphLink<Almacen> grafoAlmacenes;

    public DistributionRoute(GraphLink<Almacen> grafoAlmacenes) {
        this.grafoAlmacenes = grafoAlmacenes;
    }

    public boolean loadFile(String path) {
        try {
            FileInputStream file = new FileInputStream(path);
            Scanner sc = new Scanner(file);

            String regexPattern = "(\\d+)\\s*,\\s*(\\d+)\\s*,\\s*(\\d+)";
            Pattern pattern = Pattern.compile(regexPattern);

            while (sc.hasNext()) {
                Matcher matcher = pattern.matcher(sc.nextLine());
                while (matcher.find()) {
                    int codigoOri = Integer.parseInt(matcher.group(1));
                    int distancia = Integer.parseInt(matcher.group(2));
                    int codigoDes = Integer.parseInt(matcher.group(3));

                    Almacen almacenOri = buscarAlmacen(codigoOri);
                    Almacen almacenDes = buscarAlmacen(codigoDes);

                    if (almacenOri != null && almacenDes != null) {
                        grafoAlmacenes.insertEdge(almacenOri, almacenDes, distancia);
                    }
                }
            }
            sc.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultDistr verifyRoutes(int codOrigen, int codDestino) throws NotFoundException{

        Almacen origen = this.buscarAlmacen(codOrigen);
        Almacen destino = this.buscarAlmacen(codDestino);

        Vertex<Almacen> origenVertex = this.grafoAlmacenes.searchElement(origen);
        Vertex<Almacen> destinoVertex = this.grafoAlmacenes.searchElement(destino);
        
        if (destino == null) { // No existe
            throw new NotFoundException("Almacen de destino no encontrado");
        }
        
        
        PriorityQueue<Vertex<Almacen>> pq = new PriorityQueue<Vertex<Almacen>>(this.grafoAlmacenes.getListVertex().size());

        for(Vertex<Almacen> v : this.grafoAlmacenes.getListVertex()) {
            if (v.getData() == origen) 
                v.setDistance(0);
            else
                v.setDistance(Integer.MAX_VALUE);
            pq.insert(v);
        }

        while (!pq.isEmpty()) {
            Vertex<Almacen> u = pq.delete();
            Node<Edge<Almacen>> aux = u.getAdj().getHead();
            while (aux != null) {
                Vertex<Almacen> adj = aux.getData().getDestino();
                if (pq.search(adj)) {
                    if (adj.getDistance() > u.getDistance() + aux.getData().getWeight()) { 
                        adj.setDistance(u.getDistance() + aux.getData().getWeight());
                        adj.setPrevious(u);
                        pq.heapify();
                    }
                }
                aux = aux.getNext();
            }
        }

        if (destinoVertex.getDistance() == Integer.MAX_VALUE) {
            JOptionPane.showConfirmDialog(null, "Almacen destino inalcanzable", "Erro de busqueda", JOptionPane.ERROR_MESSAGE);
            return null;
        } else {
            ArrayList<Almacen> listaAlmacenes = new ArrayList<>();
            Vertex<Almacen> aux = destinoVertex;
            listaAlmacenes.add(aux.getData());
            while (aux != origenVertex) {
                listaAlmacenes.add(aux.getPrevious().getData());
                aux = aux.getPrevious();
            }
            Almacen[] resultArray = new Almacen[listaAlmacenes.size()];
            listaAlmacenes.toArray(resultArray);
            return new ResultDistr(resultArray);
        }

    }

    private Almacen buscarAlmacen(int codigo) {
        Vertex<Almacen> almacenVertex = grafoAlmacenes.searchElement(new Almacen(codigo, "", ""));
        if (almacenVertex != null) {
            return almacenVertex.getData();
        } else {
            return null;
        }
    }
}
