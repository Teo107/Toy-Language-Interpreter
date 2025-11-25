package model.expressions;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.values.IValue;

public class VarExp implements IExp {
    String id;

    public VarExp(String id) {
        this.id = id;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> dict, MyIHeap heap) throws MyException {
        if(!dict.isDefined(this.id))
            throw new MyException("Variable id not defined");
        return dict.getValue(id);
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
