package edu.finalproject.sortAlgorithms;

import edu.finalproject.model.NumericField;
import java.util.Comparator;


public class BubbleSortEvenOnly<E extends NumericField> implements SortStrategy<E>{
    public void sort(CustomUserCollection<E> list, Comparator<E> comparator) {
        int n = list.size();
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {

                if (isOdd(list.get(j)) || isOdd(list.get(j + 1))) {
                    continue;
                }

                if (comparator.compare(list.get(j), list.get(j + 1)) > 0) {
                    list.swap(j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    private boolean isOdd(E element) {

        return (element.getNumericValue() & 1) == 1;
    }
}
