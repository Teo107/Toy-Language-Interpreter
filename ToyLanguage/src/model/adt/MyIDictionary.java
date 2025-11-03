package model.adt;

import exceptions.MyException;

public interface MyIDictionary<K, V> {
    public void put(K key, V value);
    public boolean isDefined(K key);
    public V getValue(K key);
    public String toString();
    public void remove(K key) throws MyException;

}
