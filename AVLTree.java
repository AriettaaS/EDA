

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Clases.Listas.LinkedList;


public class AVLTree<T extends Comparable<T>, V> {

    private Node<T, V> root;
    private int size;

    public AVLTree() {
        this.root = null;
        this.size = 0;
    }

    public boolean insert(T key, V value) {
        try {
            root = insertNode(root, key, value);
            return true;
        } catch (DuplicateKeyException err) {
            return false;
        }
    }

    private Node<T, V> insertNode(Node<T, V> node, T key, V value) throws DuplicateKeyException {
        if (node == null) {
            this.size++;
            return new Node<>(key, value);
        }

        int compareResult = key.compareTo(node.getKey());

        if (compareResult < 0) {
            node.setLeft(insertNode(node.getLeft(), key, value));
        } else if (compareResult > 0) {
            node.setRight(insertNode(node.getRight(), key, value));
        } else {
            throw new DuplicateKeyException("El código ya está en uso: " + key);
        }

        node.setHeight(1 + Math.max(getHeight(node.getLeft()), getHeight(node.getRight())));

        int balance = getBalance(node);

        if (balance > 1 && key.compareTo(node.getLeft().getKey()) < 0) {
            return rotateRight(node);
        }

        if (balance > 1 && key.compareTo(node.getLeft().getKey()) > 0) {
            node.setLeft(rotateLeft(node.getLeft()));
            return rotateRight(node);
        }

        if (balance < -1 && key.compareTo(node.getRight().getKey()) > 0) {
            return rotateLeft(node);
        }

        if (balance < -1 && key.compareTo(node.getRight().getKey()) < 0) {
            node.setRight(rotateRight(node.getRight()));
            return rotateLeft(node);
        }

        return node;
    }

    private int getHeight(Node<T, V> node) {
        return (node == null) ? 0 : node.getHeight();
    }

    private int getBalance(Node<T, V> node) {
        return (node == null) ? 0 : getHeight(node.getLeft()) - getHeight(node.getRight());
    }

    private Node<T, V> rotateRight(Node<T, V> z) {
        Node<T, V> y = z.getLeft();
        Node<T, V> T3 = y.getRight();

        y.setRight(z);
        z.setLeft(T3);

        z.setHeight(1 + Math.max(getHeight(z.getLeft()), getHeight(z.getRight())));
        y.setHeight(1 + Math.max(getHeight(y.getLeft()), getHeight(y.getRight())));

        return y;
    }

    private Node<T, V> rotateLeft(Node<T, V> y) {
        Node<T, V> x = y.getRight();
        Node<T, V> T2 = x.getLeft();

        x.setLeft(y);
        y.setRight(T2);

        y.setHeight(1 + Math.max(getHeight(y.getLeft()), getHeight(y.getRight())));
        x.setHeight(1 + Math.max(getHeight(x.getLeft()), getHeight(x.getRight())));

        return x;
    }

    public boolean remove(T key) {
        try {
            root = removeNode(root, key);
            return true;
        } catch (NotFoundException err) {
            return false;
        }
    }

    private Node<T, V> removeNode(Node<T, V> node, T key) throws NotFoundException {
        if (node == null) {
            throw new NotFoundException("Codigo no encontrado");
        }

        int compareResult = key.compareTo(node.getKey());

        if (compareResult < 0) {
            node.setLeft(removeNode(node.getLeft(), key));
        } else if (compareResult > 0) {
            node.setRight(removeNode(node.getRight(), key));
        } else {
            this.size--;
            // Caso 1 y 2: Nodo con un solo hijo o sin hijos
            if (node.getLeft() == null || node.getRight() == null) {
                return (node.getLeft() != null) ? node.getLeft() : node.getRight();
            }

            // Caso 3: Nodo con dos hijos, obtener el sucesor inorden
            Node<T, V> successor = findMinValueNode(node.getRight());
            node.setKey(successor.getKey());
            node.setValue(successor.getValue());

            node.setRight(removeNode(node.getRight(), successor.getKey()));
        }

        node.setHeight(1 + Math.max(getHeight(node.getLeft()), getHeight(node.getRight())));

        int balance = getBalance(node);

        if (balance > 1 && getBalance(node.getLeft()) >= 0) {
            return rotateRight(node);
        }

        if (balance > 1 && getBalance(node.getLeft()) < 0) {
            node.setLeft(rotateLeft(node.getLeft()));
            return rotateRight(node);
        }

        if (balance < -1 && getBalance(node.getRight()) <= 0) {
            return rotateLeft(node);
        }

        if (balance < -1 && getBalance(node.getRight()) > 0) {
            node.setRight(rotateRight(node.getRight()));
            return rotateLeft(node);
        }

        return node;
    }

    private Node<T, V> findMinValueNode(Node<T, V> node) {
        if (node == null) {
            return null;
        }

        while (node.getLeft() != null) {
            node = node.getLeft();
        }

        return node;
    }

    public V search(T key) {
        return searchNode(root, key);
    }

    private V searchNode(Node<T, V> node, T key) {
        if (node == null) {
            return null;
        }

        int compareResult = key.compareTo(node.getKey());

        if (compareResult == 0) {
            return node.getValue();
        } else if (compareResult < 0) {
            return searchNode(node.getLeft(), key);
        } else {
            return searchNode(node.getRight(), key);
        }
    }

    public void addProductsFromFile(String filename) throws IOException {
        FileInputStream file = new FileInputStream(filename);
        Scanner sc = new Scanner(file);
        String regexPattern = "(\\d+)\\s*,\\s*(.+)\\s*,\\s*(\\d+)"; 

        Pattern pattern = Pattern.compile(regexPattern);
        
        while(sc.hasNext()) {
            Matcher matcher = pattern.matcher(sc.nextLine());
            while (matcher.find()) {
                String key = matcher.group(1);
                String producto = matcher.group(2);
                String stock = matcher.group(3);
                Producto product = new Producto(Integer.parseInt(key), producto, Integer.parseInt(stock));
                try {
                    Integer keyInt = Integer.valueOf(key);
                    this.insert((T) (keyInt) , (V) (product));
                } catch (NumberFormatException err) {
                    System.err.println(err.getMessage());
                }
            }
        }
        sc.close();
    }

    public LinkedList<V> getProductsInOrder() {
        LinkedList<V> listValues = new LinkedList<>();
        getInOrder(root, listValues);
        return listValues;
    }

    private void getInOrder(Node<T, V> node, LinkedList<V> valores) {

        if (node != null) {
            getInOrder(node.getLeft(), valores);
            valores.insertLast(node.getValue());
            getInOrder(node.getRight(), valores);
        }
    }
}
