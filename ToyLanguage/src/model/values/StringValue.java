package model.values;

import model.types.IType;
import model.types.StringType;

public class StringValue implements IValue {
    private String string;
    public StringValue(String string) {
        this.string = string;
    }

    public String getValue(){
        return this.string;
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public String toString() {
        return "\"" + this.string + "\"";
    }
}
