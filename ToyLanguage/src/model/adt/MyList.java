package model.adt;

import java.util.ArrayList;
import java.util.List;

public class MyList<V> implements MyIList<V> {
    private List<V> list;

    public MyList() {
        this.list = new ArrayList<V>();
    }

    @Override
    public void add(V value) {
        this.list.add(value);
    }

    @Override
    public String toString() {
        String result = "[ \n";
        for (int i = 0; i < this.list.size(); i++) {
            result += list.get(i).toString();
            if (i != this.list.size() - 1)
                result += "\n ";
        }
        result += " ]";
        return result;
    }
}
