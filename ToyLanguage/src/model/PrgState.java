package model;

import model.adt.MyIDictionary;
import model.adt.MyIList;
import model.adt.MyIStack;
import model.statements.IStmt;
import model.adt.MyIDictionary;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;


public class PrgState {
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, IValue> symTable;
    private MyIList<IValue> out;
    private MyIDictionary<StringValue, BufferedReader> fileTable;
    IStmt originalProgram;

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, IValue> symtbl, MyIList<IValue> ot, MyIDictionary<StringValue, BufferedReader> fileTable, IStmt prg) {
        this.exeStack = stk;
        this.symTable = symtbl;
        this.out = ot;
        this.fileTable = fileTable;
        this.originalProgram = prg.deepCopy();
        stk.push(prg);
    }

    // getters
    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public MyIDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public MyIList<IValue> getOut() {
        return out;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    // setters
    public void setExeStack(MyIStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public void setSymTable(MyIDictionary<String, IValue> symTable) {
        this.symTable = symTable;
    }

    public void setOut(MyIList<IValue> out) {
        this.out = out;
    }

    public void setOriginalProgram(IStmt originalProgram) {
        this.originalProgram = originalProgram;
    }

    @Override
    public String toString() {
        return "Execution Stack: " + exeStack + "\n" + "Symbol Table: " + symTable + "\n" + "Output: " + out + "\n" + "File Table: " + fileTable + "\n";
    }
}
