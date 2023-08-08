

import Clases.Listas.LinkedList;


public class Vertex<E> implements Comparable<Vertex<E>>{
	protected E data;
	protected LinkedList<Edge<E>> listAdj;  
	private Vertex<E> previousMin;
	private int distance;

	
	public Vertex(E data) {
		this.data = data;
		this.listAdj = new LinkedList<Edge<E>>();
	}

	public void setPrevious(Vertex<E> previous) {
		this.previousMin = previous;
	}

	public Vertex<E> getPrevious() {
		return this.previousMin;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getDistance() {
		return this.distance;
	}

	public LinkedList<Edge<E>> getAdj() {
		return this.listAdj;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Vertex<?>) {
			Vertex<?> v = (Vertex<?>) o;
			return this.data.equals(v.data);
		}
		return false;
	}
	
	@Override
	public String toString() {
		return this.data + " -->\t " + this.listAdj.toString() + "\n";
	}
	
	public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

	@Override
	public int compareTo(Vertex<E> o) {
		return o.getDistance() - this.getDistance();
	}	
}

