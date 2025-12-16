package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.expressions.IExp;
import model.types.IType;
import model.types.StringType;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;

public class CloseRFile implements IStmt {
    private IExp exp;

    public CloseRFile(IExp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

        IValue val = exp.eval(state.getSymTable(), state.getHeap());
        if (!val.getType().equals(new StringType()))
            throw new MyException("CloseRFile must have a string expression");

        StringValue fileName = (StringValue) val;
        if (!fileTable.isDefined(fileName))
            throw new MyException("File not found");

        BufferedReader reader = fileTable.getValue(fileName);

        try {
            reader.close();
            fileTable.remove(fileName);
        } catch (Exception e) {
            throw new MyException("Error while closing file");
        }

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CloseRFile(exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType type = exp.typecheck(typeEnv);
        if(!type.equals(new StringType()))
            throw new MyException("Type is not a string");
        return typeEnv;
    }

    @Override
    public String toString() {
        return "CloseRFile: ( " + exp.toString() + ")";
    }
}
