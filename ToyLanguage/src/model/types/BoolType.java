package model.types;

public class BoolType implements IType {
    public boolean equals(Object another){
        return another instanceof BoolType;
    }

    public String toString(){
        return "bool";
    }

}
