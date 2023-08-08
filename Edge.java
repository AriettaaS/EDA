public class Edge <E> {
    protected Vertex<E> refdest;
	protected int weight;
	
	public Edge(Vertex<E> refdest, int weight) {
		this.refdest = refdest;
		this.weight = weight;
	}
	public Edge(Vertex<E> refdest) {
		this(refdest,-1);
	}

	public Vertex<E> getDestino() {
		return this.refdest;
	}

	public int getWeight() {
		return this.weight;
	}
	
	public boolean equals(Object o) {
		if (o instanceof Edge<?>) {
			Edge<?> e = (Edge<?>) o;
			return this.refdest.equals(e.refdest);
		}
		return false;
	}
	@Override
	public String toString() {
		if (this.weight > -1)
			return this.refdest.data.toString() + "[" + this.weight + "]";
		return this.refdest.data.toString();
	}
}
