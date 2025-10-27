package model.expressions;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.types.BoolType;
import model.values.IValue;
import model.values.BoolValue;

public class LogicExp implements IExp {
    private IExp e1;
    private IExp e2;
    int op; // 1 - and; 2 - or

    public LogicExp(IExp e1, int op,  IExp e2) {
        this.e1 = e1;
        this.op = op;
        this.e2 = e2;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> dict) throws MyException {
        IValue v1, v2;
        v1 = e1.eval(dict);
        if (v1.getType().equals(new BoolType())){
            v2=e2.eval(dict);

            if (v2.getType().equals(new BoolType())) {
                BoolValue b1 = (BoolValue) v1;
                BoolValue b2 = (BoolValue) v2;
                Boolean n1 = b1.getValue();
                Boolean n2 = b2.getValue();

                if (op == 1) return new BoolValue(n1 && n2);
                else if (op == 2) return new BoolValue(n1 || n2);
                else throw new MyException("Invalid logic operation");

            }else throw new MyException("Second operand is not boolean");

        }else throw new MyException("First operand is not boolean");
    }

    @Override
    public IExp deepCopy() {
        return new LogicExp(e1.deepCopy(), op, e2.deepCopy());
    }

    @Override
    public String toString(){
        String sign = switch (op){
            case 1 -> "&&";
            case 2 -> "||";
            default -> "?";
        };
        return e1.toString() + " " + sign + " " + e2.toString();
    }
}
