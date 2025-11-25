package model.expressions;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.IType;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;

public class RelationalExp implements IExp {
    private IExp e1;
    private IExp e2;
    private int op; // 1 - "<", 2 - "<=" , 3 - "==", 4 - "!=", 5 - ">=", 6 - ">"

    public RelationalExp(int op, IExp e1, IExp e2) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> dict, MyIHeap heap) throws MyException {
        IValue v1, v2;
        v1 = e1.eval(dict, heap);
        if (v1.getType().equals((new IntType()))) {
            v2 = e2.eval(dict, heap);
            if (v2.getType().equals((new IntType()))) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = i1.getValue();
                n2 = i2.getValue();
                return switch (op) {
                    case 1 -> new BoolValue(n1 < n2);
                    case 2 -> new BoolValue(n1 <= n2);
                    case 3 -> new BoolValue(n1 == n2);
                    case 4 -> new BoolValue(n1 != n2);
                    case 5 -> new BoolValue(n1 >= n2);
                    case 6 -> new BoolValue(n1 > n2);
                    default -> throw new MyException("Invalid operation");
                };
            } else {
                throw new MyException("Second operand is not an integer");
            }
        } else
            throw new MyException("First operand is not an integer");
    }

    @Override
    public IExp deepCopy() {
        return new RelationalExp(op, e1.deepCopy(), e2.deepCopy());
    }

    @Override
    public String toString() {
        String sign = switch (op) {
            case 1 -> "<";
            case 2 -> "<=";
            case 3 -> "==";
            case 4 -> "!=";
            case 5 -> ">=";
            case 6 -> ">";
            default -> "?";
        };
        return e1.toString() + sign + e2.toString();
    }
}
