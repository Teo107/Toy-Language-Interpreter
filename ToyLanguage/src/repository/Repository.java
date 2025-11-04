package repository;

import exceptions.MyException;
import model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Repository implements IRepository {
    private List<PrgState> prgStates;
    private String logFilePath;


    public Repository(PrgState prgState) {
        this.prgStates = new ArrayList<>();
        this.prgStates.add(prgState);
        this.logFilePath = "log.txt";
    }

    @Override
    public PrgState getCrtPrg() {
        return prgStates.getFirst();
    }

    @Override
    public void logPrgStateExec() throws MyException {
        PrgState state = prgStates.get(0);
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))) {
            logFile.println("ExeStack:");
            logFile.println(state.getExeStack().toString());
            logFile.println();

            logFile.println("SymTable:");
            logFile.println(state.getSymTable().toString());
            logFile.println();

            logFile.println("Out:");
            logFile.println(state.getOut().toString());
            logFile.println();

            logFile.println("FileTable:");
            logFile.println("empty");
            logFile.println();

            logFile.println("-----------------------------------------------------------------------------------------------------------");
            logFile.println("-----------------------------------------------------------------------------------------------------------");
            logFile.println();

        } catch (Exception e) {
            throw new MyException("Something went wrong");
        }
    }
}
