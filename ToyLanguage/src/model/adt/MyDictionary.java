package model.adt;

import exceptions.MyException;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K, V> implements MyIDictionary<K, V> {
    private Map<K, V> map;

    public MyDictionary() {
        this.map = new HashMap<K, V>();
    }

    @Override
    public void put(K key, V value) {
        map.put(key, value);
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
    public void remove(K Key) throws MyException {
        if (!map.containsKey(Key))
            throw new MyException("Key not found");
        map.remove(Key);
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public String toString() {
        if (map.isEmpty())
            return "empty";

        boolean isFileTable = false;
        for (K key : map.keySet())
            if (key instanceof model.values.StringValue) {
                isFileTable = true;
                break;
            }

        StringBuilder result = new StringBuilder();

        if (isFileTable) {
            for (K key : map.keySet())
                result.append(((model.values.StringValue) key).getValue());
            return result.toString();
        }

        Map<K, V> copy = new HashMap<K, V>(this.map);

        result = new StringBuilder("{ \n");
        for (K key : copy.keySet())
            result.append(key).append("->").append(copy.get(key)).append("\n");
        result.append("}");
        return result.toString();
    }

    @Override
    public Map<K,V> getContent(){
        return map;
    }

    @Override
    public MyIDictionary<K, V> deepCopy() {
        MyIDictionary<K, V> copy = new MyDictionary<>();
        for (K key: map.keySet()){
            copy.put(key, map.get(key));
        }
        return copy;
    }
}
