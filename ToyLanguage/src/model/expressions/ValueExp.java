package model.expressions;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.values.IValue;

public class ValueExp implements IExp {
    private IValue value;

    public ValueExp(IValue value) {
        this.value = value;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> dict) throws MyException {
        return value;
    }

    @Override
    public IExp deepCopy() {
        return new ValueExp(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
