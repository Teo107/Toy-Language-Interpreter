package view;

import com.sun.jdi.Value;
import controller.Controller;
import exceptions.MyException;
import model.PrgState;
import model.adt.*;
import model.statements.IStmt;
import model.values.IValue;
import model.values.StringValue;
import repository.IRepository;
import repository.Repository;

import java.io.BufferedReader;
import java.util.Scanner;

public class View {
    private Controller controller;

    public View(Controller controller) {
        this.controller = controller;
    }

    public void run(IStmt program) {
        MyIStack<IStmt> stk = new MyStack<>();
        MyIDictionary<String, IValue> symTbl = new MyDictionary<>();
        MyIList<IValue> out = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();

        PrgState prg = new PrgState(stk, symTbl, out, fileTable,program);
        IRepository repo = new Repository(prg);
        Controller controller = new Controller(repo);

        try {
            System.out.println("Run:");
            controller.allStep();
            System.out.println(out);

        } catch (MyException e) {
            System.out.println(e.getMessage());
        }

    }
}
