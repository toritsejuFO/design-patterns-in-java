package co.learn.designpatterns.patterns.interator.exeercise;

import co.learn.log.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;

class Node<T> {
    public T value;
    public Node<T> left, right, parent;

    public Node(T value) {
        this.value = value;
    }

    public Node(T value, Node<T> left, Node<T> right) {
        this.value = value;
        this.left = left;
        this.right = right;

        left.parent = right.parent = this;
    }

    public Iterator<Node<T>> preOrder() {
        // todo
        return new PreOrderIteratorRecursive<>(this);
    }
}

class PreOrderIteratorRecursive<T> implements Iterator<Node<T>> {
    private static int pointer = 0;
    private List<Node<T>> nodes = new ArrayList<>();

    public PreOrderIteratorRecursive(Node<T> root) {
        traverse(root);
    }

    private void traverse(Node<T> current) {
        if (current == null) return;

        nodes.add(current);
        traverse(current.left);
        traverse(current.right);
    }

    @Override
    public boolean hasNext() {
        return pointer < nodes.size();
    }

    @Override
    public Node<T> next() {
        return this.nodes.get(PreOrderIteratorRecursive.pointer++);
    }
}

public class Demo {
    public static void main(String[] args) {
        Node<Character> node = new Node<>('a',
                new Node<>('b',
                        new Node<>('c'),
                        new Node<>('d')),
                new Node<>('e'));

        StringBuilder sb = new StringBuilder();
        Iterator<Node<Character>> it = node.preOrder();
        // prints a,b,c,d,e

        while (it.hasNext()) {
            char next = it.next().value;
            Log.logLine("" + next + ",");
            sb.append(next);
        }
        assertEquals("abcde", sb.toString());
    }
}
