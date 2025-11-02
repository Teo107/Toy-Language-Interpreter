package model.adt;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
public class MyDictionary<K,V> implements MyIDictionary<K,V> {
    private Map<K,V> map;
    public MyDictionary() {
        this.map = new HashMap<K,V>();
    }

    @Override
    public void put(K key, V value) {
        map.put(key,value);
    }

    @Override
    public boolean isDefined(K key) {
        return map.containsKey(key);
    }

    @Override
    public V getValue(K key) {
        return map.get(key);
    }

    @Override
    public String toString() {
        Map<K,V> copy = new HashMap<K,V>(this.map);

        String result = "{ \n";
        for ( K key: copy.keySet() )
            result += key + "->" + copy.get(key) + "\n";
        result += "}";
        return result;
    }
}
