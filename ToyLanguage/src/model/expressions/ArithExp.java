package model.expressions;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

public class ArithExp implements IExp {
    private IExp e1;
    private IExp e2;
    private int op; // 1-plus, 2-minus, 3-star, 4-division

    public ArithExp(int op, IExp e1, IExp e2) {
        this.e1 = e1;
        this.op = op;
        this.e2 = e2;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> dict)  throws MyException{
        IValue v1,v2;
        v1= e1.eval(dict);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(dict);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue)v1;
                IntValue i2 = (IntValue)v2;
                int n1,n2;
                n1= i1.getValue();
                n2 = i2.getValue();
                if (op==1)  return new IntValue(n1+n2);
                if (op ==2) return new IntValue(n1-n2);
                if (op==3)  return new IntValue(n1*n2);
                if (op==4)
                    if(n2==0) throw new MyException("division by zero");
                    else  return new IntValue(n1/n2);
            } else
                throw new MyException("second operand is not an integer");
        } else
            throw new MyException("first operand is not an integer");
        throw new MyException("Invalid operator");
    }

    @Override
    public IExp deepCopy()
    {
        return new ArithExp(op, e1.deepCopy(), e2.deepCopy());
    }

    @Override
    public String toString() {
        String sign = switch (op){
            case 1 -> "+";
            case 2 -> "-";
            case 3 -> "*";
            case 4 -> "/";
            default -> "?";
        };
        return e1.toString() + sign + e2.toString();
    }
}
