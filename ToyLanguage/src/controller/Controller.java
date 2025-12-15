package controller;

import exceptions.MyException;
import model.PrgState;
import model.adt.MyIStack;
import model.statements.IStmt;
import model.values.IValue;
import model.values.RefValue;
import repository.IRepository;
import repository.Repository;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.Objects;


public class Controller {
    private IRepository repo;
    private ExecutorService executor;

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

                if (!reachable.contains(pointed))
                    reachable.add(pointed);
            }
        }

        return reachable;
    }

    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }

    public Map<Integer, IValue> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {
                    RefValue v1 = (RefValue) v;
                    return v1.getAddress();
                })
                .collect(Collectors.toList());
    }

    private void logPrg(List<PrgState> prgList) throws MyException {
        try{
            prgList.stream().forEach(prg->{
                try{
                    repo.logPrgStateExec(prg);
                }catch (MyException e){
                    throw new RuntimeException(e);
                }
            });
        }catch (RuntimeException e){
            throw new MyException(e.getMessage());
        }
    }

    public void oneStepForAllPrg(List<PrgState> prgList) throws MyException {
        logPrg(prgList);

        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>) (() -> p.oneStep()))
                .collect(Collectors.toList());
        try {
            List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            throw new RuntimeException(e);
                        }

                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            prgList.addAll(newPrgList);
        } catch(InterruptedException | RuntimeException exception) {
            throw new MyException(exception.getMessage());
        }
        logPrg(prgList);
        repo.setPrgList(prgList);
    }


        public void allStep () throws MyException {
            executor = Executors.newFixedThreadPool(2);
            List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());

            while (!prgList.isEmpty()) {

                List<Integer> symTableAddresses = new ArrayList<>();

                for (PrgState prg : prgList) {
                    symTableAddresses.addAll(getAddrFromSymTable(prg.getSymTable().getContent().values()));
                }
                Map<Integer, IValue> heap = safeGarbageCollector(getReachableAddresses(symTableAddresses, prgList.get(0).getHeap().getContent()), prgList.get(0).getHeap().getContent());

                for(PrgState prg : prgList){
                    prg.getHeap().setContent(heap);
                }
                oneStepForAllPrg(prgList);
                prgList = removeCompletedPrg(repo.getPrgList());
            }

            executor.shutdownNow();
            repo.setPrgList(prgList);
        }

    }