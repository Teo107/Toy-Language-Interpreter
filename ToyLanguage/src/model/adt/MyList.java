package model.adt;

import java.util.ArrayList;
import java.util.List;

public class MyList<V> implements MyIList<V>{
    private List<V> list;

    public MyList(){
        this.list = new ArrayList<V>();
    }

    @Override
    public void add(V value) {
        this.list.add(value);
    }

    @Override
    public String toString(){
        String result = "[ ";
        for(V values : this.list){
            result += values + ", ";
        }
        result += "]";
        return result;
    }
}
