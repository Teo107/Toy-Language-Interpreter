package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.MyIList;
import model.expressions.IExp;
import model.values.IValue;

public class PrintStmt implements IStmt {

    private IExp exp;

    public PrintStmt(IExp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIList<IValue> out = state.getOut();
        IValue val = exp.eval(state.getSymTable(), state.getHeap());
        out.add(val);
        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "Print(" + exp.toString() + ")";
    }

}
