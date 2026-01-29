package model.adt;

import java.util.Stack;

public interface MyIStack<T> {
    public void push(T value);
    public T pop();
    public T peek();
    public boolean isEmpty();

    public Stack<T> getStack();

    public String toString();

}
