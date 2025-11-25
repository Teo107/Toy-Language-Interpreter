package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.expressions.IExp;
import model.values.IValue;
import model.values.RefValue;

public class HeapWriteStmt implements IStmt{
    private String id;
    private IExp exp;

    public HeapWriteStmt(String id, IExp exp) {
        this.id = id;
        this.exp = exp;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        MyIHeap heap = state.getHeap();

        if(!symTable.isDefined(id))
            throw new MyException("Heap is not defined");

        IValue idValue = symTable.getValue(id);
        if(!(idValue instanceof RefValue))
            throw new MyException("Variable is not a reference value");

        RefValue refValue = (RefValue) idValue;
        int address = refValue.getAddress();
        if (!heap.isDefined(address))
            throw new MyException("Address is not defined");

        IValue expValue = exp.eval(symTable,heap);
        if (! expValue.getType().equals(refValue.getLocationType()))
            throw new MyException("Expression is not a reference value");

        heap.update(address,expValue);
        return state;

    }

    @Override
    public IStmt deepCopy() {
       return new HeapWriteStmt(this.id, this.exp.deepCopy());
    }

    @Override
    public String toString() {
        return "wH(" + id + ", " + exp + ")";
    }
}
