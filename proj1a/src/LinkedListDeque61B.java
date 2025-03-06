import java.util.List;
import java.util.ArrayList;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    private Node sentinel;
    private int size;

    private class Node {
        private T value;
        private Node next;
        private Node prev;

        public Node(T value, Node next, Node prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    public LinkedListDeque61B() {
        sentinel = new Node((T) new Object(),null,null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }


    @Override
    public void addFirst(T x) {
        Node temp =new Node(x, sentinel.next, sentinel);
        sentinel.next.prev= temp;
        sentinel.next = temp;
        size++;
    }

    @Override
    public void addLast(T x) {
        Node temp=new Node(x,sentinel,sentinel.prev);
        sentinel.prev.next = temp;
        sentinel.prev = temp;
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        if (size != 0) {
            Node temp=sentinel;
            while (temp.next != sentinel) {
                temp = temp.next;
                returnList.add(temp.value);
            }
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T res=sentinel.next.value;
        Node new_next= sentinel.next.next;
        new_next.prev= sentinel;
        sentinel.next = new_next;
        size--;
        return res;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T res=sentinel.prev.value;
        Node new_prev= sentinel.prev.prev;
        sentinel.prev= new_prev;
        new_prev.next = sentinel;
        size--;
        return res;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Node temp=sentinel.next;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp.value;
    }

    @Override
    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return getRecursiveHelper(sentinel.next, index);
    }

    private T getRecursiveHelper(Node node,int index) {
        if (index==0){
            return node.value;
        }else {
            return getRecursiveHelper(node.next,index-1);
        }
    }
}
