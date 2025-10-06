package edu.finalproject.sortAlgorithms;


import java.util.Arrays;
import java.util.stream.Stream;

import edu.finalproject.model.PersonalData;

/* todo: имплементировать от Collection
 *  После этого, заменить все использования CustomUserCollection на Collection*/

 //было рискованно, оставил CustomUserCollection

 
public class CustomUserCollection<E> {
    private Object[] elements;
    private int size;

    public int getSize() {
        return size;
    }

    public CustomUserCollection() {
        // Конструктор без параметров
        this.elements = new Object[0];
        this.size = 0;
    }

    public CustomUserCollection(int size) {
        // Конструктор с параметром
        this.elements = new Object[size];
        this.size = size;
    }

    public void add(PersonalData newUser) {
        // Добавление элемента в коллекцию
        elements = Arrays.copyOf(elements, elements.length + 1);
        elements[size++] = newUser;
    }

    @SuppressWarnings("unchecked")
    public E get(int index) {
        // Обращение по индексу
        if (index >= size || index < 0) throw new IndexOutOfBoundsException();
        return (E) elements[index];
    }

    public void fillFromStream(Stream<E> stream) {
        // Заполнение коллекции из файла
        stream.forEach(e -> {
            elements = Arrays.copyOf(elements, elements.length + 1);
            elements[size++] = e;
        });
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (i != 0) sb.append(", ");
            sb.append(elements[i]); // мб нужно добавить .toString()
        }
        sb.append("]");
        return sb.toString();
    }

    public void swap(int i, int j) {
        if (i < 0 || i >= size || j < 0 || j >= size) {
            throw new IndexOutOfBoundsException("Индексы вне диапазона");
        }
        Object temp = elements[i];
        elements[i] = elements[j];
        elements[j] = temp;
    }

    @SuppressWarnings("unchecked")
    public Stream<E> stream() {
        return (Stream<E>) Arrays.stream(elements, 0, size);
    }

    public void add(String line) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

}
