

import java.util.ArrayList;

import Clases.Listas.LinkedList;
import Clases.Listas.Node;

public class GraphLink<E> {
    protected LinkedList<Vertex<E>> listVertex;

    public GraphLink() {
        this.listVertex = new LinkedList<Vertex<E>>();
    }

    public boolean insertVertex(E data) {
        Vertex<E> v = new Vertex<E>(data);
        if (this.listVertex.search(v))
            return false;
        else
            this.listVertex.insertFirst(v);
        return true;
    }

    public void insertEdge(E dataOri, E dataDes, int weight) {
        Vertex<E> vOri = this.listVertex.searchData(new Vertex<E>(dataOri));
        Vertex<E> vDes = this.listVertex.searchData(new Vertex<E>(dataDes));

        if (vOri == null || vDes == null)
            System.out.println(dataOri + " o " + dataDes + " no existen ....");
        else {
            Edge<E> e = new Edge<E>(vDes, weight);
            if (vOri.listAdj.search(e))
                System.out.println("Arista (" + dataOri + "," + dataDes + ") ya fue insertada ...");
            else {
                vOri.listAdj.insertFirst(e);
                vDes.listAdj.insertFirst(new Edge<E>(vOri, weight));
            }
        }
    }
    
    @Override
    public String toString() {
        return this.listVertex.toString();
    }

    public void removeEdge(E dataOri, E dataDes) {
        Vertex<E> vOri = this.listVertex.searchData(new Vertex<E>(dataOri));
        Vertex<E> vDes = this.listVertex.searchData(new Vertex<E>(dataDes));
        if (vOri == null || vDes == null) {
            System.out.println(dataOri + " o " + dataDes + " no existen ....");
        } else {
            Edge<E> e = new Edge<E>(vDes);
            vOri.listAdj.remove(e);
            vDes.listAdj.remove(new Edge<E>(vOri));
        }
    }

    public boolean removeVertex(E x) {
        if (x == null)
            return false;
        Vertex<E> v = new Vertex<E>(x);
        if (this.listVertex.search(v)) {
            Node<Vertex<E>> aux = this.listVertex.getHead();
            while (aux != null) {
                if (searchEdge(aux.getData().getData(), x))
                    removeEdge(aux.getData().getData(), x);
                aux = aux.getNext();
            }

            this.listVertex.remove(v);
            return true;
        } else
            return false;
    }

    public boolean searchEdge(E dataOri, E dataDes) {
        Vertex<E> vOri = this.listVertex.searchData(new Vertex<E>(dataOri));
        Vertex<E> vDes = this.listVertex.searchData(new Vertex<E>(dataDes));
        if (vOri == null || vDes == null)
            return false;
        else {
            Edge<E> e = new Edge<E>(vDes);

            if (vOri.listAdj.search(e))
                return true;
            else {
                return false;
            }
        }
    }

    public boolean searchVertex(E data) {
        Vertex<E> v = new Vertex<E>(data);
        if (this.listVertex.search(v))
            return true;
        else
            return false;
    }

   public ArrayList<Vertex<E>> getListVertex() {
        ArrayList<Vertex<E>> vertices = new ArrayList<>();
        Node<Vertex<E>> aux = listVertex.getHead();
        while (aux != null) {
            vertices.add(aux.getData());
            aux = aux.getNext();
        }
        return vertices;
    }

    public Vertex<E> searchElement(E data) {
        Vertex<E> vertex = this.listVertex.searchData(new Vertex<E>(data));
        return vertex;
    }

}

