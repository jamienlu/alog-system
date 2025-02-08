package com.asura.structure.hash;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jamieLu
 * @create 2025-01-30
 */
public class LinkedHash<K,V> {
    private static class Node<K,V> {
        K key;
        V value;
        Node prev;
        Node next;
        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private final Node<K,V> head;
    private final Node<K,V> tail;
    private final Map<K, Node<K, V>> map = new HashMap<>();

    public LinkedHash() {
        head = new Node<>(null, null);
        tail = new Node<>(null, null);
        head.next = tail;
        tail.prev = head;
    }

    public V get(K key) {
        Node<K,V> node = map.get(key);
        return node == null ? null : node.value;
    }

    public void put(K key, V value) {
        Node<K,V> node = map.get(key);
        if (node == null) {
            node = new Node<>(key, value);
            map.put(key, node);

            Node<K,V> temp = tail.prev;
            node.next = tail;
            node.prev = temp;

            temp.next = node;
            tail.prev = node;
        } else {
            map.get(key).value = value;
        }
    }

    public V remove(K key) {
        Node<K,V> node = map.get(key);
        if (node == null) {
            return null;
        } else {
            map.remove(key);
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.prev = null;
            node.next = null;
            return node.value;
        }
    }
}
