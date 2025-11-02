package model.adt;

import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {
    private Stack<T> tail;

    public MyStack() {
        this.tail = new Stack<>();
    }

    @Override
    public void push(T value) {
        this.tail.push(value);
    }

    @Override
    public T pop() {
        return this.tail.pop();
    }

    @Override
    public T peek() {
        return this.tail.peek();
    }

    @Override
    public boolean isEmpty() {
        return this.tail.isEmpty();
    }

    @Override
    public String toString() {
        Stack<T> copy = (Stack<T>) this.tail.clone();
        String result = "[\n";
        while (!copy.isEmpty())
            result += copy.pop() + "\n";
        result += "]";
        return result;
    }
}
