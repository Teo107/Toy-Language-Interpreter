package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.types.IType;
import model.values.BoolValue;
import model.values.IValue;
import model.types.IntType;
import model.types.BoolType;
import model.values.IntValue;

public class VarDeclStmt implements IStmt {
    private String name;
    private IType type;

    public VarDeclStmt(String name, IType type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        if (symTable.isDefined(name))
            throw new MyException("Variable name already exists");

        symTable.put(name, type.defaultValue());

        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(name, type); // immutable
    }

    @Override
    public String toString() {
        return type.toString() + " " + name;
    }

}
