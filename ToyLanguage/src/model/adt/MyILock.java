package model.adt;

import model.statements.IStmt;
import model.values.IValue;

import java.util.Map;

public interface MyILock {
    int add(int value);
    void update(int address, int value);
    boolean isDefined(int address);
    int getAddress(int address);

    Map<Integer,Integer> getContent();
    void setContent(Map<Integer,Integer> content);



}
