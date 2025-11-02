package view;

import com.sun.jdi.Value;
import controller.Controller;
import exceptions.MyException;
import model.PrgState;
import model.adt.*;
import model.statements.IStmt;
import model.values.IValue;
import repository.IRepository;
import repository.Repository;

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

        PrgState prg = new PrgState(stk, symTbl, out, program);
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
