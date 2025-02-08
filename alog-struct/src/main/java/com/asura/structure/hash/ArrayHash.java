package com.asura.structure.hash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author jamieLu
 * @create 2025-01-30
 */
public class ArrayHash<K,V> {
    private static class Node<K,V> {
        K key;
        V value;
        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private final List<Node<K,V>> datas = new ArrayList<>();
    private final Map<K, Integer> map = new HashMap<>();

    public void put(K key, V value) {
        if (!map.containsKey(key)) {
            Node<K,V> node = new Node<K,V>(key, value);
            datas.add(node);
            map.put(key, datas.size() -1);
        } else {
            int index = map.get(key);
            datas.get(index).value = value;
        }
    }
    public V get(K key) {
        if (!map.containsKey(key)) {
            return null;
        }
        int index = map.get(key);
        return datas.get(index).value;
    }
    public V remove(K key) {
        if (!map.containsKey(key)) {
            return null;
        }
        int index = map.get(key);
        map.remove(key);
        if (index == datas.size() - 1) {
            Node<K,V> old = datas.remove(index);
            return old.value;
        } else {
            Node<K,V> old = datas.get(index);
            Node<K,V> tail = datas.get(datas.size() -1);
            datas.set(index, tail);
            datas.remove(datas.size() - 1);
            return old.value;
        }
    }
    public K randomKey() {
        return datas.get(new Random().nextInt(datas.size())).key;
    }
}
