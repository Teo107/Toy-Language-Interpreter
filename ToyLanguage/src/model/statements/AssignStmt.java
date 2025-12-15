package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.adt.MyIStack;
import model.expressions.IExp;
import model.values.IValue;
import model.types.IType;

public class AssignStmt implements IStmt {
    private String id;
    private IExp exp;

    public AssignStmt(String id, IExp exp) {
        this.id = id;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> symTbl = state.getSymTable();
        MyIHeap heap = state.getHeap();

        if (symTbl.isDefined(id)) {
            IValue val = exp.eval(symTbl, heap);
            IType typeId = symTbl.getValue(id).getType();
            if (val.getType().equals(typeId))
                symTbl.put(id, val);
            else
                throw new MyException("Declared type of variable " + id +
                        " and type of the assigned expression do not match");
        } else {
            throw new MyException("The used variable " + id + " was not declared before");
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new AssignStmt(id, exp.deepCopy());
    }


    @Override
    public String toString() {
        return id + " = " + exp.toString();
    }
}
