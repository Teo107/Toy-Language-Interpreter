package repository;

import exceptions.MyException;
import model.PrgState;

public interface IRepository {
    PrgState getCrtPrg();
    void logPrgStateExec() throws MyException;
}
