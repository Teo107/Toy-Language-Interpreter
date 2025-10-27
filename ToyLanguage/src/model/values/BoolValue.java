package model.values;

import model.types.BoolType;
import model.types.IType;

public class BoolValue implements IValue {
    private boolean bool;

    public BoolValue(boolean bool) {
        this.bool = bool;
    }

    public boolean getValue() {
        return this.bool;
    }

    @Override
    public IType getType() {
        return new BoolType();
    }

    @Override
    public String toString() {
        return String.valueOf(this.bool);
    }
}
