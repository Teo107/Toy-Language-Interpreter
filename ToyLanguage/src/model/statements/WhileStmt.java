package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.adt.MyIStack;
import model.expressions.IExp;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.IValue;

public class WhileStmt implements IStmt{
    private IStmt stmt;
    private IExp exp;

    public WhileStmt(IExp exp, IStmt stmt) {
        this.stmt = stmt;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getExeStack();
        IValue val = exp.eval(state.getSymTable(), state.getHeap());

        if (!val.getType().equals(new BoolType()))
            throw new MyException("condition exp is not a boolean");

        BoolValue cond = (BoolValue) val;

        if (cond.getValue()) {
            stk.push(this);
            stk.push(stmt);
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(exp.deepCopy(), stmt.deepCopy());
    }

    @Override
    public String toString() {
        return "while(" + exp.toString() + ")" + stmt;
    }
}
