package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.MyStack;

public class ForkStmt implements IStmt {
    private IStmt innerStmt;

    public ForkStmt(IStmt innerStmt) {
        this.innerStmt = innerStmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        return new PrgState(new MyStack<>(), state.getSymTable().deepCopy(), state.getOut(), state.getFileTable(), state.getHeap(), innerStmt);
    }

    @Override
    public IStmt deepCopy() {
        return new ForkStmt(this.innerStmt.deepCopy());
    }

    @Override
    public String toString() {
        return "fork(" + this.innerStmt.toString() + ")";
    }
}
