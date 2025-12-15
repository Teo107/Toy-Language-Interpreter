package model;

import exceptions.MyException;
import model.adt.*;
import model.statements.IStmt;
import model.adt.MyIDictionary;
import model.values.IValue;
import model.values.StringValue;
import model.adt.MyIHeap;

import java.io.BufferedReader;


public class PrgState {
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, IValue> symTable;
    private MyIList<IValue> out;
    private MyIDictionary<StringValue, BufferedReader> fileTable;
    private MyIHeap heap;
    private int id;
    private static int lastId = 0;

    IStmt originalProgram;

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, IValue> symtbl, MyIList<IValue> ot, MyIDictionary<StringValue, BufferedReader> fileTable, MyIHeap heap, IStmt prg) {
        this.id = getNextId();
        this.exeStack = stk;
        this.symTable = symtbl;
        this.out = ot;
        this.fileTable = fileTable;
        this.heap = heap;
        this.originalProgram = prg.deepCopy();
        stk.push(prg);
    }
    private static synchronized int getNextId() {
        return ++lastId;
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

    public MyIHeap getHeap() {
        return heap;
    }
    public int getId() {
        return id;
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


    public boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() throws MyException {
        if (exeStack.isEmpty())
            throw new MyException("prgstate stack is empty");
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    @Override
    public String toString() {
        return "ID " + this.id + "\n" + "Execution Stack: " + exeStack + "\n" + "Symbol Table: " + symTable + "\n" + "Output: " + out + "\n"
                + "Heap: " + heap + "\n" + "File Table: " + fileTable + "\n";
    }

}
