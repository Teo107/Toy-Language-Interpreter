package repository;

import exceptions.MyException;
import model.PrgState;

import java.util.List;

public interface IRepository {
    //PrgState getCrtPrg();

    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> prgList);

    void logPrgStateExec(PrgState state) throws MyException;

}
