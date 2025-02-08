package com.asura.structure.hash;

/**
 * 线性探测法实现
 *
 * @author jamieLu
 * @create 2025-01-29
 */
public class HashLine<K,V> {
    private static class KVNode<K, V> {
        K key;
        V val;

        KVNode(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    // 真正存储键值对的数组
    private KVNode<K, V>[] table;
    // HashMap 中的键值对个数
    private int size;
    // 默认的初始化容量
    private static final int INIT_CAP = 8;

    public HashLine() {
        this(INIT_CAP);
    }

    public HashLine(int size) {
        this.size = 0;
        table = (KVNode<K, V>[]) new KVNode[size];
    }
    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % table.length;
    }
    private int getKeyIndex(K key) {
        int index;
        for (index = hash(key); table[index] !=null; index = (index + 1) % table.length ) {
            if (table[index].key.equals(key)) {
                return index;
            }
        }
        return index;

      
    }
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        if (size >= table.length * 0.75) {
            resize(size * 2);
        }
        int index = getKeyIndex(key);
        if (table[index] != null) {
            table[index].val = value;
        } else {
            table[index] = new KVNode<>(key, value);
            size++;
        }
    }

    public void remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        if (size <= table.length / 8) {
            resize(table.length / 4);
        }
        int index = getKeyIndex(key);
        if (table[index] == null) {
            return;
        }
        table[index] = null;
        size--;
        // 搬运元素
        for (int i = (index + 1) % table.length; table[i] != null; i = (i + 1) % table.length) {
            KVNode<K, V> old = table[index];
            table[index] = null;
            size--;
            put(old.key, old.val);
        }
    }
    private void resize(int newSize) {
        HashLine<K, V> newHash = new HashLine<K, V>(newSize);
        for (KVNode<K, V> kvNode : table) {
            if (kvNode != null) {
                newHash.put(kvNode.key, kvNode.val);
            }
        }
    }
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        int index = getKeyIndex(key);
        if (table[index] == null) {
            return null;
        }
        return table[index].val;
    }
}
