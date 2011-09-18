package com.timgroup.option.util;

import com.timgroup.option.Option;

import java.util.HashMap;
import java.util.Map;

public class OptionMap<K, V> {

    private final Map<K, V> map = new HashMap<K, V>();
    
    public void clear() {
        map.clear();
    }

    public boolean containsKey(K key) {
        return map.containsKey(key);
    }

    public boolean containsValue(V value) {
        return map.containsValue(value);
    }

    public Option<V> get(K key) {
        return Option.fromNullable(map.get(key));
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public V put(K key, V value) {
        return map.put(key, value);
    }

    public V remove(K key) {
        return map.remove(key);
    }

    public int size() {
        return map.size();
    }

}
