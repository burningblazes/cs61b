package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author Yusheng Rain Y
 */
public class MyHashMap<K, V> implements Map61B<K, V> {


    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    private int size=0;
    private int capacity;
    private double loadFactor;
    private Set<K> keySet;

    private static final int resizeFactor=2;

    /** Constructors */
    public MyHashMap() {
        this(16);
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, 0.75);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        this.capacity = initialCapacity;
        this.loadFactor = loadFactor;
        buckets = new Collection[capacity];
        keySet = new HashSet<K>();
        for (int i = 0; i < capacity; i++) {
            buckets[i] = createBucket();
        }
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }


    @Override
    public void put(K key, V value) {
        if (key!=null){
            int id=getBucketIndex(key);
            Collection<Node> bucket=buckets[id];
            for (Node p : bucket) {
                if (p.key.equals(key)) {
                    p.value = value;
                    return;
                }
            }
            bucket.add(new Node(key,value));
            }
        size++;
        if (size>capacity*loadFactor){
            resize();
        }
        keySet.add(key);
    }

    private void resize() {
        capacity*=resizeFactor;
        Collection<Node>[] newbuckets = new Collection[capacity];
        for (int i = 0; i < capacity; i++) {
            newbuckets[i] = createBucket();
        }
        for (Collection<Node> bucket : buckets) {
            for (Node node : bucket) {
                newbuckets[getBucketIndex(node.key)].add(node);
            }
        }
        buckets = newbuckets;
    }


    @Override
    public V get(K key) {
        int id=getBucketIndex(key);
        Collection<Node> bucket=buckets[id];
        for (Node p : bucket) {
            if (p.key.equals(key)) {
                return p.value;
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        int id=getBucketIndex(key);
        Collection<Node> bucket=buckets[id];
        for (Node p : bucket) {
            if (p.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        this.size=0;
        this.buckets = new Collection[capacity];
        for (int i = 0; i < capacity; i++) {
            buckets[i] = createBucket();
        }
    }

    @Override
    public Set<K> keySet() {
        return this.keySet;
    }

    @Override
    public V remove(K key) {
        int id=getBucketIndex(key);
        Collection<Node> bucket=buckets[id];
        for (Node p : bucket) {
            if (p.key.equals(key)) {
                V res=p.value;
                bucket.remove(p);
                keySet.remove(p.key);
                size--;
                return res;
            }
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    private int getBucketIndex(K key) {
        return Math.floorMod(key.hashCode(), capacity);
    }

}
