package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.expressions.IExp;
import model.types.IType;
import model.types.IntType;
import model.types.StringType;
import model.values.IValue;
import model.values.IntValue;
import model.values.StringValue;

import java.io.BufferedReader;

public class ReadFile implements IStmt {
    private IExp exp;
    private String varName;

    public ReadFile(IExp exp, String var) {
        this.exp = exp;
        this.varName = var;
    }


    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyIHeap heap = state.getHeap();

        if (!symTable.isDefined(varName))
            throw new MyException("Variable is not defined");

        IValue value = symTable.getValue(varName);
        if (!value.getType().equals(new IntType()))
            throw new MyException("Variable must be Integer");

        IValue fileNameVal = exp.eval(symTable, heap);
        if (!fileNameVal.getType().equals(new StringType()))
            throw new MyException("ReadFile need a String for the filename");

        StringValue fileName = (StringValue) fileNameVal;

        if (!fileTable.isDefined(fileName))
            throw new MyException("File is not opened");

        BufferedReader reader = fileTable.getValue(fileName);

        try {
            String line = reader.readLine();
            int number;

            if (line == null) number = 0;
            else number = Integer.parseInt(line);
            symTable.put(varName, new IntValue(number));
        } catch (Exception e) {
            throw new MyException("Error reading file");
        }

        return null;
    }


    @Override
    public IStmt deepCopy() {
        return new ReadFile(exp.deepCopy(), varName);
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType expType = exp.typecheck(typeEnv);

        if (!expType.equals(new StringType()))
            throw new MyException("ReadFile: expression is not string");

        IType varType = typeEnv.getValue(varName);

        if (!varType.equals(new IntType()))
            throw new MyException("ReadFile: variable is not int");

        return typeEnv;
    }

    @Override
    public String toString() {
        return "ReadFile: ( " + exp.toString() + " )";
    }
}
