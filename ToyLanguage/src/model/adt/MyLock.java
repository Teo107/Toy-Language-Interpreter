package model.adt;

import com.sun.security.jgss.InquireType;
import model.statements.IStmt;
import model.values.IValue;

import java.util.HashMap;
import java.util.Map;

public class MyLock implements MyILock {
    private Map<Integer,Integer> lock;
    private int nextFree;

    public MyLock() {
        lock = new HashMap<Integer,Integer>();
        nextFree = 1;
    }

    @Override
    public int add(int value) {
        this.lock.put(nextFree++, value);
        return nextFree;
    }

    @Override
    public void update(int address, int value) {
        lock.put(address, value);
    }

    @Override
    public boolean isDefined(int address) {
        return lock.containsKey(address);
    }

    @Override
    public int getAddress(int address) {
        return lock.get(address);
    }

    @Override
    public Map<Integer, Integer> getContent() {
        return new HashMap<>(lock);
    }

    @Override
    public void setContent(Map<Integer, Integer> content) {
        lock.clear();
        lock.putAll(content);

    }
}
