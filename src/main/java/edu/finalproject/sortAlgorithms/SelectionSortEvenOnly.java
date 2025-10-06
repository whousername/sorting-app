package edu.finalproject.sortAlgorithms;

import edu.finalproject.model.NumericField;

import java.util.Comparator;

// Сортировка выбором
public class SelectionSortEvenOnly <E extends NumericField> implements SortStrategy<E> {
    public void sort(CustomUserCollection<E> list, Comparator<E> comparator) {
        int n = list.getSize();
        for (int i = 0; i < n - 1; i++) {

            if (isOdd(list.get(i))) {
                continue;
            }

            int minIndex = i;
            for (int j = i + 1; j < n; j++) {

                if (isOdd(list.get(i))) {
                    continue;
                }
                if (comparator.compare(list.get(j), list.get(minIndex)) < 0) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                list.swap(i, minIndex);
            }
        }
    }

    private boolean isOdd(E element) {

        return (element.getNumericValue() & 1) == 1;
    }
}
