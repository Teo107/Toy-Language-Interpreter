package model.adt;

import model.values.IValue;

import java.util.Map;

public interface MyIHeap {
    public int add(IValue value);
    void remove(int address);
    public boolean isDefined(int address);

    IValue getAddress(int address);

    int nextFreeAddress();
    int size();

    Map<Integer, IValue> getContent();
    void setContent(Map<Integer, IValue> content);
}



//heap un fel de pointer
// referinta pointeaza la o adresa din memorie nu heap la o valoare
// in heap o sa avem un int pe poz 2 un bool pe 3 un ref catre un ref(int) asta pointeaza catre 1 (intu ala).. daca se scvhimba
// in prima valoare in primu int nu se modifica si la ref(int).
//( C: daca vrem sa schimbam valoarea tre sa dealocam )
//