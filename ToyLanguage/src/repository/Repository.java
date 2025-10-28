package repository;

import model.PrgState;

import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private List<PrgState> prgStates;

    public Repository(PrgState prgState) {
        this.prgStates = new ArrayList<>();
        this.prgStates.add(prgState);
    }

    @Override
    public PrgState getCrtPrg() {
        return prgStates.getFirst();
    }
}
