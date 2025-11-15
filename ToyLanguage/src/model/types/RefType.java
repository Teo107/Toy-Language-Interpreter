package model.types;

import model.values.IValue;
import model.values.RefValue;
import view.Command;

public class RefType implements IType {
   private IType inner;

    public RefType(IType inner) {
        this.inner = inner;
    }

    public IType getInner() {
        return this.inner;
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof RefType)
            return this.inner.equals(((RefType) another).getInner());
        else return false;

    }

    @Override
    public IValue defaultValue() {
        return new RefValue(0, inner);
    }

    @Override
    public String toString() {
        return "Ref(" + inner.toString() + ")";
    }
}
