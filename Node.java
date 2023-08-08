

public class Node<T extends Comparable<T>, V> {
    private T key;
    private V value;
    private Node<T, V> left;
    private Node<T, V> right;
    private int height;

    public Node(T key, V value) {
        this.key = key;
        this.value = value;
        this.height = 1;
    }

    // Getters and setters
    public T getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public Node<T, V> getLeft() {
        return left;
    }

    public void setLeft(Node<T, V> left) {
        this.left = left;
    }

    public Node<T, V> getRight() {
        return right;
    }

    public void setRight(Node<T, V> right) {
        this.right = right;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setKey(T key2) {
        this.key = key2;
    }

    public void setValue(V value2) {
        this.value = value2;
    }
}

