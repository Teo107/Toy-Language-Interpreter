package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.expressions.IExp;
import model.types.StringType;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;

public class OpenRFile implements IStmt {
    private IExp exp;

    public OpenRFile(IExp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue val = exp.eval(state.getSymTable(), state.getHeap());
        if (!val.getType().equals(new StringType())) {
            throw new MyException("OpenRFile expects a string value");
        }
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        StringValue sv = (StringValue) val;

        if(fileTable.isDefined(sv))
            throw new MyException("OpenRFile already opened");

        try {
            BufferedReader br = new BufferedReader(new FileReader(sv.getValue()));
            fileTable.put(sv, br);
        } catch (Exception e) {
            throw new MyException("Could not open file: " + sv.getValue());
        }

        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new OpenRFile(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "OpenRFile: ( " + exp.toString() + " )";
    }
}
