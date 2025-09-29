package edu.finalproject.sortAlgorithms;


import java.util.Arrays;
import java.util.stream.Stream;

public class CustomUserCollection<E> extends SelectionSort<E> {
    private Object[] elements;
    private int size;

    public int getSize() {
        return size;
    }

    public CustomUserCollection(){
        // Конструктор без параметров
        this.elements = new Object[0];
        this.size = 0;
    }

    public CustomUserCollection(int size){
        // Конструктор с параметром
        this.elements = new Object[size];
        this.size = size;
    }

    public void add(E element) {
        // Добавление элемента в коллекцию
        elements = Arrays.copyOf(elements, elements.length + 1);
        elements[size++] = element;
    }

    @SuppressWarnings("unchecked")
    public E get(int index) {
        // Обращение по индексу
        if (index >= size || index < 0) throw new IndexOutOfBoundsException();
        return (E)elements[index];
    }

    public void fillFromStream(Stream<E> stream) {
        // Заполнение коллекции из файла
        stream.forEach(this::add);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for(int i = 0; i < size; i++) {
            if (i != 0) sb.append(", ");
            sb.append(elements[i]); // мб нужно добавить .toString()
        }
        sb.append("]");
        return sb.toString();
    }

    public void swap(int i, int j){
        if (i < 0 || i >= size || j < 0 || j >= size) {
            throw new IndexOutOfBoundsException("Индексы вне диапазона");
        }
        Object temp = elements[i];
        elements[i] = elements[j];
        elements[j] = temp;
    }


}
