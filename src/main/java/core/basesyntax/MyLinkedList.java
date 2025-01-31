package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    public static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(T item, Node<T> prev, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            first = last = new Node<>(value, null, null);
            ++size;
        } else {
            addInEnd(value);
        }
    }

    @Override
    public void add(T value, int index) {
        if (index > 0 && index < size) {
            addInMiddle(value, index);
        } else if (index == size) {
            add(value);
        } else if (index == 0) {
            addInBegin(value);
        } else {
            checkIndex(index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list != null) {
            for (T t: list) {
                add(t);
            }
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return searchByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> newNode = searchByIndex(index);
        T oldValue = newNode.item;
        newNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = searchByIndex(index);
        unlink(node);
        return node.item;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> i = first; i != null; i = i.next) {
            if (i.item == object || i.item != null && i.item.equals(object)) {
                unlink(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> searchByIndex(int index) {
        Node<T> node;
        if (index <= size / 2) {
            node = this.first;
            for (int i = 0; i < index; node = node.next, ++i) {
            }
        } else {
            node = this.last;
            for (int i = size - 1; i > index; node = node.prev, --i) {
            }
        }
        return node;
    }

    private void addInBegin(T value) {
        Node<T> newNode = new Node<>(value, null, first);
        first.prev = newNode;
        first = newNode;
        ++size;
    }

    private void addInMiddle(T value, int index) {
        Node<T> searched = searchByIndex(index);
        Node<T> newNode = new Node<>(value, searched.prev, searched);
        searched.prev.next = newNode;
        searched.prev = newNode;
        ++size;
    }

    private void addInEnd(T value) {
        Node<T> newNode = new Node<>(value, last, null);
        last.next = newNode;
        last = newNode;
        ++size;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Size " + size + "! Index " + index + " wrong");
        }
    }

    private void unlink(Node<T> node) {
        if (node == first && node == last) {
            first = last = null;
        } else if (node == first) {
            first = first.next;
            node.next.prev = node.prev;
        } else if (node == last) {
            last = last.prev;
            node.prev.next = node.next;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
        --size;
    }
}
