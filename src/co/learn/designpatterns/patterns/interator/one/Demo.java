package co.learn.designpatterns.patterns.interator.one;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

class Node<T> {
    public T value;
    public Node<T> left;
    public Node<T> right;
    public Node<T> parent;

    public Node(T value) {
        this.value = value;
    }

    public Node(T value, Node left, Node right) {
        this.value = value;
        this.left = left;
        this.right = right;

        left.parent = right.parent = this;
    }
}

class InOrderIterator<T> implements Iterator<T> {
    private Node<T> current;
    private Node<T> root;
    private boolean yieldedStart;

    public InOrderIterator(Node<T> root) {
        this.root = current = root;

        while (current.left != null) {
            current = current.left;
        }
    }

    public boolean hasRightMostParent(Node<T> node) {
        if (node.parent == null) return false;
        else {
            return node.equals(node.parent.left) || hasRightMostParent(node.parent);
        }
    }

    @Override
    public boolean hasNext() {
        return current.left != null || current.right != null || hasRightMostParent(current);
    }

    @Override
    public T next() {
        if (!yieldedStart) {
            yieldedStart = true;
            return current.value;
        }

        if (current.right != null) {
            current = current.right;
            while (current.left != null)
                current = current.left;
            return current.value;
        } else {
            Node<T> p = current.parent;
            while (p != null && current == p.right) {
                current = p;
                p = p.parent;
            }
            current = p;
            return current.value;
        }
    }
}

class InOrderIteratorRecursive<T> implements Iterator<T> {
    private static int pointer = 0;
    private Node<T> root;
    private List<Node<T>> nodes = new ArrayList<>();

    public InOrderIteratorRecursive(Node<T> root) {
        this.root = root;
        traverse(root);
    }

    private void traverse(Node<T> current) {
        if (current == null) return;

        traverse(current.left);
        nodes.add(current);
        traverse(current.right);
    }

    @Override
    public boolean hasNext() {
        return pointer <= nodes.size() - 1;
    }

    @Override
    public T next() {
        return nodes.get(InOrderIteratorRecursive.pointer++).value;
    }
}

class BinaryTree<T> implements Iterable<T> {
    private Node<T> root;
    private Iterator<T> iterator;


    public BinaryTree(Node<T> root) {
        this.root = root;
        iterator = new InOrderIteratorRecursive<>(this.root);
//        iterator = new InOrderIterator<>(root);
    }

    @Override
    public Iterator<T> iterator() {
        return iterator;
    }

    @Override
    public void forEach(Consumer<? super T> action) {
//        for (T item : this)
//            action.accept(item);

        while (iterator.hasNext())
            action.accept(iterator.next());
    }

    @Override
    public Spliterator<T> spliterator() {
        return null;
    }
}

public class Demo {
    public static void main(String[] args) {
        //   1
        //  / \
        // 2   3
        Node<Integer> root = new Node<>(1,
                new Node<>(2),
                new Node<>(3));

        //        4
        //      /   \
        //     2     6
        //    / \   / \
        //   1   3  5  7
        // prints 1,2,3,4,5,6,7
        Node<Integer> root2 = new Node<>(4,
                new Node<>(2,
                        new Node<>(1), new Node<>(3)),
                new Node<>(6,
                        new Node<>(5), new Node<>(7)));

        InOrderIteratorRecursive<Integer> it = new InOrderIteratorRecursive<>(root2);
        while (it.hasNext()) {
            System.out.print("" + it.next() + ",");
        }
        System.out.println();

        BinaryTree<Integer> tree = new BinaryTree<>(root);
        for (int i : tree) {
            System.out.print("ok: " + i + ",");
        }
        System.out.println();
    }
}
