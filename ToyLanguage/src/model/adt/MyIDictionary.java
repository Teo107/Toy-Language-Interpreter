package model.adt;

import exceptions.MyException;

import java.util.Map;

public interface MyIDictionary<K, V> {
    public void put(K key, V value);
    public boolean isDefined(K key);
    public V getValue(K key);
    public String toString();
    public void remove(K key) throws MyException;
    public boolean isEmpty();

    Map<K,V> getContent();
}
