package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyILock;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

public class NewLockStmt implements IStmt {
    private String varName;

    public NewLockStmt(String varName) {
    this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        MyILock lockTable = state.getLockTable();

        synchronized (lockTable) {
            int address = lockTable.add(-1);
            symTable.put(this.varName, new IntValue(address));
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NewLockStmt(this.varName);
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        if(!typeEnv.isDefined(this.varName)){
            throw new MyException("Variable name not found");
        }

        return typeEnv;
    }
}
