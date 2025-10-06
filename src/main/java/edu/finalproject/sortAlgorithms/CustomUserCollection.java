package edu.finalproject.sortAlgorithms;


import java.util.*;
import java.util.stream.Stream;

/* todo: имплементировать от Collection
 *  После этого, заменить все использования CustomUserCollection на Collection*/
public class CustomUserCollection<E> implements Collection<E> {
    private Object[] elements;
    private int size;

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
    @Override
    public int size() {
        return size;
    }
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    @Override
    public boolean contains(Object element) {
        for (int i = 0; i < size; i++) {
            if (element.equals(elements[i])) {
                return true;
            }
        }
        return false;
    }
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            @SuppressWarnings("unchecked")
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (E) elements[currentIndex++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Remove not supported");
            }
        };
    }
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elements, size);
    }
    @Override
    public <E> E[] toArray(E[] a) {
        if (a.length < size) {
            // Создаём новый массив типа a с нужным размером и копируем в него элементы
            return (E[]) Arrays.copyOf(elements, size, a.getClass());
        }
        System.arraycopy(elements, 0, a, 0, size);
        if (a.length > size) {
            a[size] = null; // Согласно контракту, если длина больше, следующему элементу присваивается null
        }
        return a;
    }
    @Override
    public boolean add(E element) {
        // Добавление элемента в коллекцию
        elements = Arrays.copyOf(elements, elements.length + 1);
        elements[size++] = element;
        return true;
    }
    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(o, elements[i])) {
                // Сдвигаем все элементы после i влево на 1 место
                int numMoved = size - i - 1;
                if (numMoved > 0) {
                    System.arraycopy(elements, i + 1, elements, i, numMoved);
                }
                elements[--size] = null; // Очистить последнюю ячейку для GC
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object element : c) {
            if (!this.contains(element)) {
                return false;  // Если хотя бы одного элемента нет, вернуть false
            }
        }
        return true;  // Все элементы из c присутствуют в вашей коллекции
    }
    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E element : c) {
            if (add(element)) { // вызов вашего метода add
                modified = true;
            }
        }
        return modified;
    }
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        Iterator<E> it = this.iterator();
        while (it.hasNext()) {
            E element = it.next();
            if (c.contains(element)) {
                it.remove(); // Удаляем элемент, если он содержится в collection c
                modified = true;
            }
        }
        return modified;
    }
    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        Iterator<E> it = this.iterator();
        while (it.hasNext()) {
            E element = it.next();
            if (!c.contains(element)) {
                it.remove();    // Удалить элемент, если его нет в коллекции c
                modified = true;
            }
        }
        return modified;
    }
    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }
    @SuppressWarnings("unchecked")
    public E get(int index) {
        // Обращение по индексу
        if (index >= size || index < 0) throw new IndexOutOfBoundsException();
        return (E) elements[index];
    }
    public void fillFromStream(Stream<E> stream) {
        // Заполнение коллекции из файла
        stream.forEach(this::add);
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
