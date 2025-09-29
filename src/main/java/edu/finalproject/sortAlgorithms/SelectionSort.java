package edu.finalproject.sortAlgorithms;

import java.util.Comparator;

// Сортировка выбором
public class SelectionSort<E> implements SortStrategy<E> {
    public void sort(CustomUserCollection<E> list, Comparator<E> comparator) {
        int n = list.getSize();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (comparator.compare(list.get(j), list.get(minIndex)) < 0) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                list.swap(i, minIndex);
            }
        }
    }
}
