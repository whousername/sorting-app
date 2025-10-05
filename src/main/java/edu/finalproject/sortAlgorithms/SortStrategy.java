package edu.finalproject.sortAlgorithms;

import java.util.Comparator;

public interface SortStrategy<E> {
    void  sort(CustomUserCollection<E> list, Comparator<E> comparator);
}
