package com.asura.structure.design;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * @author jamieLu
 * @create 2025-02-11
 */
public class LFUCache {
    private int capacity;
    private int minFreq;
    private Map<Integer,Integer> data;

    private final Map<Integer,Integer> keyFreq = new HashMap<>();

    private final Map<Integer, LinkedHashSet<Integer>> freqKeys = new HashMap<>();

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.minFreq = 0;
        data = new HashMap<>(capacity);
    }

    public int get(int key) {
        if (!data.containsKey(key)) {
            return -1;
        }
        increaseFreq(key);
        return data.get(key);
    }

    public void put(int key, int value) {
        if (capacity <= 0) {
            return;
        }
        if (data.containsKey(key)) {
            data.put(key, value);
            increaseFreq(key);
            return;
        }
        if (data.size() >= capacity) {
            removeMinFreq();
        }
        // 插入数据
        data.put(key, value);
        // 连锁更新频次表和key频次
        keyFreq.put(key, 1);
        // 插入 FK 表
        freqKeys.putIfAbsent(1, new LinkedHashSet<>());
        freqKeys.get(1).add(key);
        this.minFreq = 1;
    }

    private void removeMinFreq() {
        // 寻找删除key
        LinkedHashSet<Integer> keys = freqKeys.get(minFreq);
        Integer delKey = keys.iterator().next();

        //  频次表key删除
        keys.remove(delKey);

        // 该频次无key后删除该频次
        if (keys.isEmpty()) {
            freqKeys.remove(minFreq);
        }
        // 连锁更新数据表 key频次表
        keyFreq.remove(delKey);
        data.remove(delKey);
    }
    private void increaseFreq(int key) {
        // key频次变更
        int freq = keyFreq.get(key);
        keyFreq.put(key, freq + 1);

        // 频次表变更
        LinkedHashSet<Integer> keys = freqKeys.get(freq);
        keys.remove(key);

        // 连锁更新刷新频次表
        if (freqKeys.containsKey(freq + 1)) {
            freqKeys.get(freq + 1).add(key);
        } else {
            LinkedHashSet<Integer> newKeys = new LinkedHashSet<>();
            newKeys.add(key);
            freqKeys.put(freq + 1, newKeys);
        }
        // 连锁更新刷新最小频次
        if (freqKeys.get(freq).isEmpty()) {
            freqKeys.remove(freq);
            if (freq == minFreq) {
                minFreq++;
            }
        }
    }
}
