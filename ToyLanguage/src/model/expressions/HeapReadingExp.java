package model.expressions;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.values.IValue;
import model.values.RefValue;

public class HeapReadingExp implements IExp{
    private IExp exp;

    public HeapReadingExp(IExp exp) {
        this.exp = exp;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> dict, MyIHeap heap) throws MyException {
        IValue value = exp.eval(dict, heap);

        if(!(value instanceof RefValue))
            throw new MyException("Value is not a reference value");

        int address = ((RefValue) value).getAddress();

        if(!heap.isDefined(address))
            throw new MyException("Address is not defined");

        return heap.getAddress(address);
    }

    @Override
    public IExp deepCopy() {
        return new HeapReadingExp(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "rH(" + exp + ")";
    }
}
