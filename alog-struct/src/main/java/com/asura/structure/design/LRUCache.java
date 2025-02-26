package com.asura.structure.design;

import java.util.LinkedHashMap;

/**
 * @author jamieLu
 * @create 2025-02-08
 */
public class LRUCache {
    private int capacity;
    private LinkedHashMap<Integer, Integer> data;

    public LRUCache(int capacity) {
        data = new LinkedHashMap<>();
        this.capacity = capacity;
    }

    public int get(int key) {
        if (!data.containsKey(key)) {
            return -1;
        }
        int val = data.get(key);
        data.remove(key);
        data.put(key, val);
        return val;
    }

    public void put(int key, int value) {
        if (data.containsKey(key)) {
            data.remove(key);
        } else if (data.size() == capacity) {
            int oldKey = data.keySet().iterator().next();
            data.remove(oldKey);
        }
        data.put(key, value);
    }
}