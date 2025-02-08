package com.asura.structure.hash;

import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.hash;

/**
 * 开链法实现
 *
 * @author jamieLu
 * @create 2025-01-27
 */
public class HashLinked <K,V>{
    private static final int DEFAULT_CAPACITY = 8;
    private int size;
    private LinkedList<KV<K,V>>[] tables;
    private static class KV<K,V> {
        K key;
        V value;
        KV(K key, V value){
            this.key = key;
            this.value = value;
        }
    }

    public HashLinked() {
        this(DEFAULT_CAPACITY);
    }

    public HashLinked(int cap) {
        size = 0;
        int capacity = Math.max(cap, DEFAULT_CAPACITY);
        tables = (LinkedList<KV<K,V>>[]) new LinkedList[capacity];
        for (int i = 0; i < tables.length; i++) {
            tables[i] = new LinkedList<KV<K,V>>();
        }
    }

    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");

        }
        LinkedList<KV<K,V>> list = tables[hashIndex(hash(key))];
        for (KV<K,V> kv : list) {
            if (kv.key.equals(key)) {
                kv.value = value;
                return;
            }
        }
        list.add(new KV(key, value));
        size++;
        if (size > tables.length * 0.75) {
            resize(size * 2);
        }
    }
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        LinkedList<KV<K,V>> list = tables[hashIndex(hash(key))];
        for (KV<K,V> kv : list) {
            if (kv.key.equals(key)) {
                list.remove(kv);
                size--;
                if (size < tables.length / 4) {
                    resize(size / 2);
                }
                return kv.value;
            }
        }
        return null;
    }
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        LinkedList<KV<K,V>> list = tables[hashIndex(hash(key))];
        for (KV<K,V> kv : list) {
            if (kv.key.equals(key)) {
                return kv.value;
            }
        }
        return null;
    }
    public List<K> keys() {
        List<K> keys = new LinkedList<>();
        for (LinkedList<KV<K,V>> table : tables) {
            for (KV<K,V> kv : table) {
                keys.add(kv.key);
            }
        }
        return keys;
    }
    public int size() {
        return size;
    }
    private void resize(int cap) {
        int capacity = Math.max(cap, DEFAULT_CAPACITY);
        HashLinked<K,V> newHash = new HashLinked(capacity);
        for (LinkedList<KV<K,V>> table : tables) {
            for (KV<K,V> kv : table) {
                newHash.put(kv.key, kv.value);
            }
        }
        tables = newHash.tables;
    }
    private int hashIndex(int hashcode) {
        return (hashcode & 0x7fffffff) % tables.length;
    }
}
