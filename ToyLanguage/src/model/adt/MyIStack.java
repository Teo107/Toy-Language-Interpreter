package model.adt;

public interface MyIStack<T> {
    public void push(T value);
    public T pop();
    public T peek();
    public boolean isEmpty();
    public String toString();

}
