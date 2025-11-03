package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.expressions.IExp;
import model.types.StringType;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;

public class closeRFile implements IStmt {
    private IExp exp;

    public closeRFile(IExp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

        IValue val = exp.eval(state.getSymTable());
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

        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new closeRFile(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "CloseRFile: ( " + exp.toString() + ")";
    }
}
