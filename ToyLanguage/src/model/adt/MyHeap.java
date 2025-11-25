package model.adt;

import model.values.IValue;

import java.util.HashMap;
import java.util.Map;

public class MyHeap implements MyIHeap{
    private Map<Integer, IValue> heap;
    private int nextFree;

    public MyHeap(){
        heap = new HashMap<Integer, IValue>();
        nextFree = 1;
    }
    @Override
    public int add(IValue value) {
        heap.put(nextFree, value);
        return nextFree++;
    }

    @Override
    public void remove(int address) {
        heap.remove(address);
    }

    @Override
    public void update(int address, IValue value) {
        heap.put(address, value);
    }

    @Override
    public boolean isDefined(int address) {
        return heap.containsKey(address);
    }

    @Override
    public IValue getAddress(int address) {
        return heap.get(address);
    }

    @Override
    public int nextFreeAddress() {
        return nextFree;
    }

    @Override
    public int size() {
        return heap.size();
    }

    @Override
    public Map<Integer, IValue> getContent() {
        return new HashMap<>(heap);
    }

    @Override
    public void setContent(Map<Integer, IValue> content) {
        heap.clear();
        heap.putAll(content);
    }

    @Override
    public String toString() {
        if(heap.isEmpty())
            return "empty";

            StringBuilder result = new StringBuilder();
            result.append("{\n");
            for(int key : heap.keySet()){
                result.append(key).append("->").append(heap.get(key)).append(" \n");
            }
            result.append("}");

            return result.toString();
    }
}
