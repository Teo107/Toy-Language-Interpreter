package model.expressions;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.values.IValue;

public class VarExp implements IExp {
    String id;

    public VarExp(String id) {
        this.id = id;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> dict) throws MyException {
        return dict.getValue(id);  // return dict.lookup(id);
    }

    @Override
    public IExp deepCopy() {
        return new VarExp(id);
    }

    @Override
    public String toString() {
        return id;
    }

}
