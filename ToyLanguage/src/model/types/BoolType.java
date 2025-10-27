package model.types;

public class BoolType implements IType {
    public boolean eqauls(Object another){
        return another instanceof BoolType;
    }

    public String toString(){
        return "bool";
    }
}
