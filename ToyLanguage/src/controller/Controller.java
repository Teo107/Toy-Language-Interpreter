package controller;

import exceptions.MyException;
import model.PrgState;
import model.adt.MyIStack;
import model.statements.IStmt;
import model.values.IValue;
import model.values.RefValue;
import repository.IRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Controller {
    private IRepository repo;

    public Controller(IRepository repo) {
        this.repo = repo;
    }

    private List<Integer> getReachableAddresses(List<Integer> roots, Map<Integer, IValue> heap) {
        List<Integer> reachable = new ArrayList<>(roots);

        for (int i = 0; i < reachable.size(); i++) {
            int addr = reachable.get(i);
            IValue value = heap.get(addr);
            if (value instanceof RefValue refVal) {
                int pointed = refVal.getAddress();

                if(!reachable.contains(pointed))
                    reachable.add(pointed);
            }
        }

        return reachable;
    }

    public Map<Integer, IValue> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> { RefValue v1 = (RefValue) v;
                    return v1.getAddress();
                })
                .collect(Collectors.toList());
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
        repo.logPrgStateExec();
        while (!prg.getExeStack().isEmpty()) {
            oneStep(prg);
            repo.logPrgStateExec();
            prg.getHeap().setContent(
                    safeGarbageCollector(getReachableAddresses(getAddrFromSymTable(prg.getSymTable().getContent().values()),
                                    prg.getHeap().getContent()), prg.getHeap().getContent()
                    )
            );
            repo.logPrgStateExec();
        }
    }

}