package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.MyIStack;
import model.expressions.IExp;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.IValue;

public class IfStmt implements IStmt {
    private IExp exp;
    private IStmt thenS;
    private IStmt elseS;

    public IfStmt(IExp exp, IStmt thenS, IStmt elseS) {
        this.exp = exp;
        this.thenS = thenS;
        this.elseS = elseS;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getExeStack();
        IValue condValue = exp.eval(state.getSymTable());

        if (condValue.getType().equals(new BoolType())) {
            BoolValue boolVal = (BoolValue) condValue;
            if (boolVal.getValue())
                stk.push(thenS);
            else
                stk.push(elseS);
        } else
            throw new MyException("Condition expression is not of type bool!");
        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(exp.deepCopy(), thenS.deepCopy(), elseS.deepCopy());
    }

    public String toString() {
        return "(IF(" + exp.toString() + ") THEN(" + thenS.toString() + ") ELSE(" + elseS.toString() + "))";
    }
}
