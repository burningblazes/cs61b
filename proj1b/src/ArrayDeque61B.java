import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private static final int RFACTOR=2;
    private T[] array;
    private int size;
    private int capacity;
    private int nextLast;
    private int nextFirst;

    public ArrayDeque61B() {
        capacity = 8;
        array = (T[]) new Object[capacity];
        size = 0;
        nextLast = 1;
        nextFirst = 0;
    }

    @Override
    public void addFirst(T x) {
        if (size == capacity) {
            resize(size*RFACTOR);
        }
        array[nextFirst] = x;
        nextFirst=minusOne(nextFirst);
        size++;
    }

    @Override
    public void addLast(T x) {
        if (size == capacity) {
            resize(size*RFACTOR);
        }
        array[nextLast] = x;
        nextLast=plusOne(nextLast);
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                returnList.add(this.get(i));
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
        if (size == 0) {
            return null;
        }
        if (capacity>=16 && size / (double) capacity <=0.25) {
            resize(capacity/RFACTOR);
        }
        T temp=array[plusOne(nextFirst)];
        nextFirst = plusOne(nextFirst);
        size--;

        return temp;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        if (capacity>=16 && size / (double) capacity <=0.25) {
            resize(capacity/RFACTOR);
        }
        T temp=array[minusOne(nextLast)];
        nextLast = minusOne(nextLast);
        size--;
        return temp;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return array[plusOne(nextFirst+index)];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }


    private void resize(int newCapacity) {
        T[] newArray = (T[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newArray[i] = this.get(i);
        }

        nextFirst = newCapacity - 1;
        nextLast = size;
        capacity = newCapacity;
        array = newArray;
    }

    private int minusOne(int x){
        return Math.floorMod(x-1,capacity);
    }

    private int plusOne(int x){
        return Math.floorMod(x+1,capacity);
    }

}
