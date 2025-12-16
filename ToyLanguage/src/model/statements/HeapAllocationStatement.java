package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.expressions.IExp;
import model.types.IType;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;


public class HeapAllocationStatement implements IStmt {
    private String id;
    private IExp exp;

    public HeapAllocationStatement(String id, IExp exp) {
        this.id = id;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        MyIHeap heap = state.getHeap();

        if(!symTable.isDefined(id))
            throw new MyException("Variable is not defined");

        IValue idValue = symTable.getValue((id));
        if(!(idValue.getType() instanceof RefType))
            throw new MyException("Variable is not a reference type");

        RefType refType = (RefType) idValue.getType();
        IValue expValue = exp.eval(symTable, heap);

        if(!expValue.getType().equals(refType.getInner()))
            throw new MyException("Expression type is not a reference type");

        int address = heap.add(expValue);

        if (heap.isDefined(address) && heap.getAddress(address) != expValue) {
            throw new MyException("Heap allocation failed");
        }

        symTable.put(id, new RefValue(address, refType.getInner()));

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new  HeapAllocationStatement(this.id, this.exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType idType = typeEnv.getValue(this.id);
        IType expType = exp.typecheck(typeEnv);

        if(!idType.equals(expType))
            throw new MyException("Types do not match");
        return typeEnv;
    }

    @Override
    public String toString() {
        return "new (" + this.id + ", " + this.exp + ")";
    }
}
