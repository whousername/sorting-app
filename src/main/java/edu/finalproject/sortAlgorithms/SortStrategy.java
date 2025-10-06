package edu.finalproject.sortAlgorithms;

import java.util.Collection;
import java.util.Comparator;

import edu.finalproject.model.PersonalData;

public interface SortStrategy<E> {
    void  sort(CustomUserCollection<E> list, Comparator<E> comparator);
}
