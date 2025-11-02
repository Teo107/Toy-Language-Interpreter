package controller;

import exceptions.MyException;
import model.PrgState;
import model.adt.MyIStack;
import model.statements.IStmt;
import repository.IRepository;


public class Controller {
    private IRepository repo;

    public Controller(IRepository repo) {
        this.repo = repo;
    }

    public PrgState oneStep(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getExeStack();

        if (stk.isEmpty())
            throw new MyException("prgstate stack is empty");
        IStmt crtStmt = stk.pop();
        return crtStmt.execute(state);
    }

    public void allStep() throws MyException {
        PrgState prg = repo.getCrtPrg();
        System.out.println("Initial state: " + prg);
        while (!prg.getExeStack().isEmpty()) {
            oneStep(prg);
            System.out.println("Current state: " + prg);
        }
    }

}