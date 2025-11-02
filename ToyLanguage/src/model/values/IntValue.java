package model.values;

import model.types.IntType;
import model.types.IType;

public class IntValue implements IValue {
    private int value;

    public IntValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

}
