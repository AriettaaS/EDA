package Clases.Listas;

import java.util.ArrayList;

public class PriorityQueue<E extends Comparable<E>>{

    private ArrayList<E> array;

    public PriorityQueue(int n) {
        this.array = new ArrayList<>(n);
    }

    public void insert(E data) {
        this.array.add(data);
        E temp;
        for( int i = this.array.size(); i > 1 && this.array.get(i-1).compareTo(this.array.get(i/2-1)) > 0; i /= 2) {
            temp = this.array.get(i-1);
            this.array.set(i-1, this.array.get(i/2-1));
            this.array.set(i/2-1, temp);
        }
    }
    
    public E delete() {
        int n = this.array.size();
        E m = this.array.get(0);
        this.array.set(0, this.array.get(n-1));
        this.array.remove(--n);
        int j = 0;
        while(2 * j + 1 < n) {
            int k = 2 * j + 1;
            if (k + 1 < n && this.array.get(k+1).compareTo(this.array.get(k)) > 0)
                k++;
            if(this.array.get(j).compareTo(this.array.get(k)) > 0)
                break;
            
            E temp = this.array.get(j);
            this.array.set(j, this.array.get(k));
            this.array.set(k, temp);
            j = k;
        }
        
        return m;
    }

    
    public void heapify() { // Reordena las claves
        int n = this.array.size();
        for(int i = n / 2 -1; i > -1; i--) { // Ultimo padre hacia arriba
            heapifyMax(this.array, i);
        }
    }

    private void heapifyMax(ArrayList<E> arr, int i) {
        int n = arr.size();
        int posMax = 2*i+1 < n ? 2*i+1 : i; // Hijo izquierdo o el mismo
        // Si tiene hijo derecho y es mayor
        if (2*i+1 < n-1 && arr.get(posMax).compareTo(arr.get(2*i+2)) < 0) 
            posMax++;
        if (arr.get(posMax).compareTo(arr.get(i)) > 0) {
            // Intercambio con la raiz
            E temp = arr.get(i);
            arr.set(i, arr.get(posMax));
            arr.set(posMax, temp);
                
            heapifyMax(arr, posMax);
        }
    }
        

    public boolean search(E data) {
        return this.array.contains(data);
    }

    public boolean isEmpty() {
        return this.array.isEmpty();
    }
    
    @Override
    public String toString() {
       return this.array.toString();
    }
    
}

