package model.expressions;


import model.types.IType;
import model.adt.MyIDictionary;
import exceptions.MyException;
import model.adt.MyIHeap;
import model.values.IValue;

public interface IExp {
    IValue eval(MyIDictionary<String, IValue> dict, MyIHeap heap) throws MyException;
    IExp deepCopy();

    IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyException;
}
