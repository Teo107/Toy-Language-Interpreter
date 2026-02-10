package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.adt.MyStack;
import model.expressions.IExp;
import model.expressions.RelationalExp;
import model.expressions.ValueExp;
import model.expressions.VarExp;
import model.types.IType;
import model.types.IntType;

public class ForStmt implements IStmt {
    private String varName;
    private IExp exp1, exp2, exp3;
    private IStmt stmt;

    public ForStmt(String varName, IExp exp1, IExp exp2, IExp exp3, IStmt stmt) {
        this.varName = varName;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getExeStack();
        // int v, v=exp1; (while (v<exp2) stmt; v=exp3)
        IStmt newStmt = new CompStmt(new VarDeclStmt(varName, new IntType()),
                new CompStmt(new AssignStmt(varName, exp1),
                        new WhileStmt(
                                new RelationalExp(1, new VarExp(varName),exp2),
                                new CompStmt(stmt, new AssignStmt(varName, exp3))
                        )));
    stk.push(newStmt);
    return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ForStmt(varName, exp1, exp2, exp3, stmt);
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        MyIDictionary<String, IType> newEnv = typeEnv.deepCopy();
        newEnv.put(varName, new IntType());

        if(!exp1.typecheck(newEnv).equals(new IntType()))
            throw new MyException("Exp1 is not an integer");
        if(!exp2.typecheck(newEnv).equals(new IntType()))
            throw new MyException("Exp2 is not an integer");
        if(!exp3.typecheck(newEnv).equals(new IntType()))
            throw new MyException("Exp3 is not an integer");

        stmt.typecheck(newEnv);
        return typeEnv;
    }

    @Override
    public String toString() {
        return "for("+varName+ " = "+ exp1 + "; " + varName + " < " + exp2 + "; "+varName + " = " + exp3 + ") { " + stmt + " }";
    }
}
