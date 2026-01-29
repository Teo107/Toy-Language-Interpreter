package model.adt;

import java.util.List;

public interface MyIList<V> {
    public void add(V value);
    String toString();
    public List<V> getList();
}
