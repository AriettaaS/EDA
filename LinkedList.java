package Clases.Listas;


public class LinkedList <E>{
    Node<E> head;
	
	public LinkedList() {
		this.head = null;
	}
	
	public boolean isEmpty() {
		return this.head == null; 
	}
	
	public Node<E> getHead() {
		return head;
	}
	public boolean search(E x) {
		Node<E> aux = this.head;
		for(; aux != null; aux = aux.getNext()) {
			if (aux.getData().equals(x)) {
				return true;
			}
		}
		return false;
	}

	public E searchData(E x) {
		Node<E> aux = this.head;
		for(; aux != null; aux = aux.getNext()) {
			if (aux.getData().equals(x)) {
				return aux.getData();
			}
		}
		return null;
	}
	
	public void insertFirst(E x) {
		this.head = new Node<E>(x, this.head);
	}
	
	public void insertLast(E x) {
		if (this.isEmpty()) {
			this.insertFirst(x); 
		}
		else {
			Node <E> aux = this.head;
			while (aux != null && aux.getNext() != null)
				aux = aux.getNext(); 
			if (aux != null) {
				aux.setNext(new Node<E> (x)); 
			}
		}
	}
	
	public void remove(E x) {
		if (this.head != null && this.head.getData().equals(x)) { 
			this.head = this.head.getNext();
		}
		else {
			Node<E> aux = this.head;
			while (aux.getNext() != null && !aux.getNext().getData().equals(x)) {
				aux = aux.getNext();
			}
			if (aux.getNext()!= null) {
				aux.setNext(aux.getNext().getNext());
			}
		}
	}
	
	@Override
	public String toString() {
		String str = "";
		for(Node<E> aux = this.head; aux != null; aux = aux.getNext()) { 
			str = str + aux.toString() + ", "; 
		}
		return str;
	}
}
